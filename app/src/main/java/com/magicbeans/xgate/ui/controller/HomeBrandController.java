package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.brand.Brand;
import com.magicbeans.xgate.bean.brand.BrandHotWrap;
import com.magicbeans.xgate.bean.product.Product;
import com.magicbeans.xgate.bean.product.ProductWrap;
import com.magicbeans.xgate.databinding.LayHomeBrandBinding;
import com.magicbeans.xgate.databinding.LayHomeSingleBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.activity.ProductActivity;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.activity.SectionActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeBrand;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeSingle;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/11.
 */

public class HomeBrandController implements View.OnClickListener {

    private Context context;
    private LayHomeBrandBinding binding;
    private RecycleAdapterHomeBrand adapter;

    public HomeBrandController(LayHomeBrandBinding binding) {
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    public void initCtrl() {
        adapter = new RecycleAdapterHomeBrand(context);
        adapter.setOnItemClickListener(onRecycleItemClickListener);
        binding.recycle.setNestedScrollingEnabled(false);
        binding.recycle.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
//        binding.recycle.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.dp2px(4), GridLayoutManager.VERTICAL, false));
        binding.recycle.setAdapter(adapter);

        binding.btnMore.setOnClickListener(this);
    }

    public void initData() {
        netGetBrand();
    }

    private OnRecycleItemClickListener onRecycleItemClickListener = new OnRecycleItemClickListener() {
        @Override
        public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
            Brand brand = adapter.getResults().get(viewHolder.getLayoutPosition());
            ProductActivity.startBrand(context, brand.getBrandID());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_more:
                SectionActivity.start(context);
                break;
        }
    }

    private void netGetBrand() {
        Map<String, Object> param = new NetParam()
                .build();
        NetApi.NI().netHomeSelectBrand(param).enqueue(new STCallback<BrandHotWrap>(BrandHotWrap.class) {
            @Override
            public void onSuccess(int status, BrandHotWrap bean, String msg) {
                adapter.getResults().clear();
                adapter.getResults().addAll(bean.getBrand());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
