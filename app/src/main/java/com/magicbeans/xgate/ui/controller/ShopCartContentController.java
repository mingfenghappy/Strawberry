package com.magicbeans.xgate.ui.controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.common.ItemDecorationDivider;
import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.data.db.AppDatabaseManager;
import com.magicbeans.xgate.databinding.FragmentShopbagBinding;
import com.magicbeans.xgate.databinding.LayRecommendBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.sharesdk.ShareDialog;
import com.magicbeans.xgate.ui.activity.OrderAddActivity;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.adapter.DiffCallBack;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeShopbag;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterRecomment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ShopCartContentController extends BaseController<FragmentShopbagBinding> implements OnRecycleItemClickListener, View.OnClickListener {

    private RecycleAdapterHomeShopbag adapter;

    public ShopCartContentController(FragmentShopbagBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    private void initCtrl() {
        binding.includeBottombar.btnGo.setOnClickListener(this);
        binding.includeBottombar.textShopbagCheckall.setOnClickListener(this);
        binding.includeBottombar.btnShare.setOnClickListener(this);
        binding.includeBottombar.btnFavo.setOnClickListener(this);
        binding.includeBottombar.btnDel.setOnClickListener(this);
        adapter = new RecycleAdapterHomeShopbag(context);
        adapter.setLoadingLayout(binding.loadingLayout);
        adapter.setOnItemClickListener(this);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        binding.recycle.addItemDecoration(new ItemDecorationDivider(context, LinearLayoutManager.VERTICAL));
        binding.recycle.setAdapter(adapter);
        binding.spring.setHeader(new AliHeader(context, false));
        binding.spring.setFooter(new AliFooter(context, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.spring.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        adapter.setOnSelectChangeListenner(new RecycleAdapterHomeShopbag.OnSelectChangeListenner() {
            @Override
            public void onSelectChange() {
                calcuPrice();
            }
        });
    }

    private void initData() {
        LiveData<List<Product2>> product2sLiveData = AppDatabaseManager.getInstance().queryShopCartTables();
        product2sLiveData.observeForever(new Observer<List<Product2>>() {
            @Override
            public void onChanged(@Nullable List<Product2> product2s) {
                adapter.notifyDataSetChanged(product2s);
                binding.spring.onFinishFreshAndLoad();
                //计算价格
                calcuPrice();
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        Product2 product2 = adapter.getResults().get(viewHolder.getLayoutPosition());
        ProductDetailActivity.start(context, product2.getProdID());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_shopbag_checkall:
                if (adapter.isSelectAll()) {
                    binding.includeBottombar.textShopbagCheckall.setSelected(false);
                    adapter.selectAll(false);
                } else {
                    binding.includeBottombar.textShopbagCheckall.setSelected(true);
                    adapter.selectAll(true);
                }
                break;
            case R.id.btn_go:
                DialogSure.showDialog(context, "确定要下单？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        OrderAddActivity.start(context);
                    }
                });
                break;
            case R.id.btn_share:
                new ShareDialog(context).show();
                break;
            case R.id.btn_favo:
                ToastUtil.showToastShort("开发中");
                break;
            case R.id.btn_del:
                DialogSure.showDialog(context, "确定要删除这些商品？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        List<Product2> selectBeans = adapter.getSelectBeans();
//                        AppDatabaseManager.getInstance().deleteShopCartTable(selectBeans.toArray(new Product2[]{}));
//                        adapter.getResults().removeAll(selectBeans);
//                        adapter.freshDataSet();

                        AppDatabaseManager.getInstance().deleteShopCartTable(selectBeans.toArray(new Product2[]{}));
                        EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_SHOPCART));
//                        LiveData<List<Product2>> newResults = AppDatabaseManager.getInstance().queryShopCartTables();
//                        newResults.observeForever(new Observer<List<Product2>>() {
//                            @Override
//                            public void onChanged(@Nullable List<Product2> product2s) {
//                                adapter.notifyDataSetChanged(product2s);
//                            }
//                        });
                    }
                });
                break;
        }
    }

    //#################  对外方法 ##################

    public void refreshData() {
        initData();
    }

    public void setEditModel(boolean isEdit) {
        if (isEdit) {
            binding.includeBottombar.layEdit.setVisibility(View.VISIBLE);
            binding.includeBottombar.layUnedit.setVisibility(View.GONE);
        } else {
            binding.includeBottombar.layEdit.setVisibility(View.GONE);
            binding.includeBottombar.layUnedit.setVisibility(View.VISIBLE);
        }
    }

    public void calcuPrice() {
        List<Product2> selectProduct2s = adapter.getSelectBeans();
        binding.includeBottombar.btnGo.setText("去结算(" + selectProduct2s.size() + ")");
        float totalPrice = 0;
        for (Product2 product2 : selectProduct2s) {
            totalPrice += Float.parseFloat(product2.getShopPrice()) * product2.getCount();
        }
        binding.includeBottombar.textShopbagPriceall.setText("合计：￥" + totalPrice);
    }

    public boolean isEdit() {
        return binding.includeBottombar.layEdit.getVisibility() == View.VISIBLE;
    }
}
