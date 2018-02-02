package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.ToastUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.shopcart.ShopCartWrap;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.databinding.ActivityAddressBinding;
import com.magicbeans.xgate.helper.SpringViewHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterAddress;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.List;
import java.util.Map;

public class AddressActivity extends BaseAppCompatActivity {


    private ActivityAddressBinding binding;
    private RecycleAdapterAddress adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()){
            case EventBean.EVENT_REFRESH_ADDRESSLIST:
                adapter.netGetAddressList(true,true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address);
        setToolbar();
        registEventBus();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
    }

    private void initCtrl() {
        adapter = new RecycleAdapterAddress(this);
        adapter.setLoadingLayout(binding.loadingview);
        adapter.setSpringView(binding.spring);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(adapter);
        binding.spring.setHeader(new AliHeader(this, false));
        binding.spring.setFooter(new AliFooter(this, false));
        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                adapter.netGetAddressList(true, false);
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
        binding.loadingview.setOnRefreshListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.netGetAddressList(true, true);
            }
        });
    }

    private void initData() {
        adapter.netGetAddressList(true, true);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                AddressAddActivity.start(this);
                break;
        }
    }
}
