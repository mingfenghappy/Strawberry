package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.LayHomeRecommendBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.SaleActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterRecomment;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeRecommendController implements View.OnClickListener{

    private Context context;
    private LayHomeRecommendBinding binding;
    private RecycleAdapterRecomment adapter;

    public HomeRecommendController(LayHomeRecommendBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        adapter = new RecycleAdapterRecomment(context);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        binding.recycle.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(4), GridLayoutManager.VERTICAL, false));
        binding.recycle.setAdapter(adapter);
        binding.btnMore.setOnClickListener(this);
    }

    public void initData() {
        netHomeRecommendList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_more:
                SaleActivity.start(context, SaleActivity.TYPE_RECOMMED);
                break;
        }
    }

    public void netHomeRecommendList() {
        Map<String, Object> param = new NetParam()
                .build();
        NetApi.NI().netHomeRecommendList(param).enqueue(new STCallback<ProductWrap>(ProductWrap.class) {
            @Override
            public void onSuccess(int status, ProductWrap bean, String msg) {
                adapter.getResults().clear();
                adapter.getResults().addAll(ListUtil.getFirst(bean.getProductList(), 4));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
