package com.magicbeans.xgate.net.nethelper;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.shopcart.ShopCartInfo;
import com.magicbeans.xgate.bean.shopcart.ShopCartWrap;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.data.db.manager.ShopCartTableManager;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by Marie on 2018/1/22.
 * 管理购物车的网络请求
 */

public class NetShopCartHelper {
    private static NetShopCartHelper instance;

    private NetShopCartHelper() {
    }

    public static synchronized NetShopCartHelper getInstance() {
        if (instance == null) instance = new NetShopCartHelper();
        return instance;
    }

    /////////////////////////////////////

    //添加购物车
    public void netAddShopCart(final String ProdId, int count) {
        Map<String, Object> param = new NetParam()
                .put("ProdId", ProdId)
                .put("qty", count)
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netAddShopCart(param).enqueue(new STFormatCallback<ShopCartWrap>(ShopCartWrap.class) {
            @Override
            public void onSuccess(int status, ShopCartWrap shopCartWrap, String msg) {
                String errorMsg = shopCartWrap.getErrorMsg(ProdId);
                ToastUtil.showToastShort(!TextUtils.isEmpty(errorMsg) ? errorMsg : "添加成功");
                List<ShopCart> shopCarts = shopCartWrap.getProdList();
                updateDataBaseSendFreshMsg(shopCarts);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    //修改购物车商品
    //conetxt只用于显示进度弹窗，如果不需要显示进度可以传null
    public void netUpdateShopCart(final Context context, final String ProdId, int count) {
        dismissLoadingDialog(context);
        Map<String, Object> param = new NetParam()
                .put("ProdId", ProdId)
                .put("qty", count)
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netUpdateShopCart(param).enqueue(new STFormatCallback<ShopCartWrap>(ShopCartWrap.class) {
            @Override
            public void onSuccess(int status, ShopCartWrap shopCartWrap, String msg) {
                String errorMsg = shopCartWrap.getErrorMsg(ProdId);
                if (!TextUtils.isEmpty(errorMsg)) ToastUtil.showToastShort(errorMsg);
                List<ShopCart> shopCarts = shopCartWrap.getProdList();
                updateDataBaseSendFreshMsg(shopCarts);
                dismissLoadingDialog(context);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog(context);
            }
        });
    }

    //批量修改购物车商品（可用于删除）
    //批量删除的接口很恶心，不是传递需要删除的商品id集合，而是传不需要删除的商品id，其余没传的都会被删除
    //conetxt只用于显示进度弹窗，如果不需要显示进度可以传null
    public void netBatchUpdateShopCart(final Context context, List<ShopCart> shopCarts) {
        showLoadingDialog(context);
        Map<String, Object> param = new NetParam()
                .put("AppProds", ShopCart.getBatchUpdateIds(shopCarts))
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netBatchUpdateShopCart(param).enqueue(new STFormatCallback<ShopCartWrap>(ShopCartWrap.class) {
            @Override
            public void onSuccess(int status, ShopCartWrap shopCartWrap, String msg) {
                List<ShopCart> shopCarts = shopCartWrap.getProdList();
                updateDataBaseSendFreshMsg(shopCarts);
                dismissLoadingDialog(context);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog(context);
            }
        });
    }

    //批量添加商品
    //conetxt只用于显示进度弹窗，如果不需要显示进度可以传null
    public void netBatchAddShopCart(final Context context, List<ShopCart> shopCarts) {
        showLoadingDialog(context);
        Map<String, Object> param = new NetParam()
                .put("AppProds", ShopCart.getBatchUpdateIds(shopCarts))
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netBatchAddShopCart(param).enqueue(new STFormatCallback<ShopCartWrap>(ShopCartWrap.class) {
            @Override
            public void onSuccess(int status, ShopCartWrap shopCartWrap, String msg) {
                List<ShopCart> shopCarts = shopCartWrap.getProdList();
                updateDataBaseSendFreshMsg(shopCarts);
                dismissLoadingDialog(context);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog(context);
            }
        });
    }

    //删除购物车商品
    //conetxt只用于显示进度弹窗，如果不需要显示进度可以传null
    public void netRemoveShopCart(final Context context, String ProdId) {
        showLoadingDialog(context);
        Map<String, Object> param = new NetParam()
                .put("ProdId", ProdId)
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netRemoveShopCart(param).enqueue(new STFormatCallback<ShopCartWrap>(ShopCartWrap.class) {
            @Override
            public void onSuccess(int status, ShopCartWrap shopCartWrap, String msg) {
                List<ShopCart> shopCarts = shopCartWrap.getProdList();
                updateDataBaseSendFreshMsg(shopCarts);
                dismissLoadingDialog(context);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog(context);
            }
        });
    }

    //获取购物车列表
    public LiveData<List<ShopCart>> netGetShopCartList() {
        final MutableLiveData<List<ShopCart>> liveData = new MediatorLiveData<>();
        Map<String, Object> param = new NetParam()
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netGetShopCartList(param).enqueue(new STFormatCallback<ShopCartWrap>(ShopCartWrap.class) {
            @Override
            public void onSuccess(int status, ShopCartWrap shopCartWrap, String msg) {
                final List<ShopCart> shopCarts = shopCartWrap.getProdList();
                LiveData<Integer> retLiveData = ShopCartTableManager.getInstance().deleteAndUpdate(shopCarts.toArray(new ShopCart[]{}));
                retLiveData.observeForever(new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        liveData.setValue(shopCarts);
                    }
                });
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
        return liveData;
    }

    //获取商品信息（总价，折扣，运费等）
    public LiveData<ShopCartInfo> netGetShopCartInfo(List<ShopCart> shopCarts) {
        final MutableLiveData<ShopCartInfo> liveData = new MediatorLiveData<>();
        Map<String, Object> param = new NetParam()
                .put("AppProds", ShopCart.getBatchUpdateIds(shopCarts))
                .build();
        NetApi.NI().netGetShopCartInfo(param).enqueue(new STFormatCallback<ShopCartWrap>(ShopCartWrap.class) {
            @Override
            public void onSuccess(int status, ShopCartWrap shopCartWrap, String msg) {
                ShopCartInfo shopCartInfo = new ShopCartInfo(shopCartWrap);
                liveData.setValue(shopCartInfo);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
        return liveData;
    }

    private void dismissLoadingDialog(Context context) {
        if (context != null && context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).dismissLoadingDialog();
        }
    }

    private void showLoadingDialog(Context context) {
        if (context != null && context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).showLoadingDialog();
        }
    }

    public void netUpdateShopCart(String ProdId, int count) {
        netUpdateShopCart(null, ProdId, count);
    }

    public void netRemoveShopCart(String ProdId) {
        netRemoveShopCart(null, ProdId);
    }

    public void netBatchUpdateShopCart(List<ShopCart> shopCarts) {
        netBatchUpdateShopCart(null, shopCarts);
    }

    public void netBatchAddShopCart(List<ShopCart> shopCarts) {
        netBatchAddShopCart(null, shopCarts);
    }

    //################ 业务逻辑 ######################

    private void updateDataBaseSendFreshMsg(List<ShopCart> shopCarts) {
        //把更新同步到数据库
        LiveData<Integer> liveData = ShopCartTableManager.getInstance().deleteAndUpdate(shopCarts.toArray(new ShopCart[]{}));
        //同步到数据库后发送更新购物车的消息
        liveData.observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer shopCarts) {
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_SHOPCART));
            }
        });
    }
}
