package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.entity.Image;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.view.BannerView;
import com.ins.common.view.BannerView2;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.activity.SearchActivity;
import com.magicbeans.xgate.ui.activity.SectionActivity;
import com.magicbeans.xgate.ui.adapter.PagerAdapterHomeRecommend;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeNew;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterHomeSale;
import com.magicbeans.xgate.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liaoinstan
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener{

    private int position;
    private View rootView;

    private BannerView2 banner;

    private RecyclerView recycle_home_sale;
    private RecycleAdapterHomeSale adapterHomeSale;

    private RecyclerView recycle_home_new;
    private RecycleAdapterHomeNew adapterHomeNew;

    private TabLayout tab_home_recommend;
    private ViewPager pager_home_recommend;
    private PagerAdapterHomeRecommend pagerAdapterHomeRecommend;

    public static Fragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initCtrl();
        initData();

        FocusUtil.focusToTop(banner);
    }

    private void initBase() {
    }

    private void initView() {
        banner = (BannerView2) rootView.findViewById(R.id.banner);
        recycle_home_sale = (RecyclerView) rootView.findViewById(R.id.recycle_home_sale);
        recycle_home_new = (RecyclerView) rootView.findViewById(R.id.recycle_home_new);
        tab_home_recommend = (TabLayout) rootView.findViewById(R.id.tab_home_recommend);
        pager_home_recommend = (ViewPager) rootView.findViewById(R.id.pager_home_recommend);

        rootView.findViewById(R.id.text_search).setOnClickListener(this);
        rootView.findViewById(R.id.btn_new_more).setOnClickListener(this);
        rootView.findViewById(R.id.btn_brand_more).setOnClickListener(this);
    }

    private void initData() {
        //banner初始化假数据
        List<Image> banners = new ArrayList<Image>() {{
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900665723&di=b12643bfd81b25ba211d427533544b61&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0101945774f35c0000018c1b113714.png"));
            add(new Image("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3995594144,3504136179&fm=27&gp=0.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900773698&di=0bcf274f7b9d284e79f90ce2dca79c73&imgtype=0&src=http%3A%2F%2Fwww.289.com%2Fup%2Farticle%2F2015%2F0928%2F141259_64120628.gif"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505900712345&di=c61a3609656fc809917dfcd1c1fda075&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01e00f56d56e336ac7252ce61858a3.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506495445&di=4f316c2a5465fed43af0120773d2f8dc&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F16%2F70%2F07%2F40n58PIC2eb_1024.jpg"));
            add(new Image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506495515&di=1f7990b9071035dbd5751f1d93c029a6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F010c87584941bba801219c7702c875.jpg%40900w_1l_2o_100sh.jpg"));
        }};
        banner.setDatas(banners);

        //限时促销列表假数据
        adapterHomeSale.getResults().clear();
        adapterHomeSale.getResults().add(new TestBean());
        adapterHomeSale.getResults().add(new TestBean());
        adapterHomeSale.getResults().add(new TestBean());
        adapterHomeSale.getResults().add(new TestBean());
        adapterHomeSale.getResults().add(new TestBean());
        adapterHomeSale.getResults().add(new TestBean());
        adapterHomeSale.getResults().add(new TestBean());
        adapterHomeSale.getResults().add(new TestBean());
        adapterHomeSale.notifyDataSetChanged();

        //本周新品列表假数据
        adapterHomeNew.getResults().clear();
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.getResults().add(new TestBean());
        adapterHomeNew.notifyDataSetChanged();
    }

    private void initCtrl() {
        banner.showTitle(false);
        banner.setOnLoadImgListener(new BannerView2.OnLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, R.drawable.default_bk_img, imgurl);
            }
        });

        adapterHomeSale = new RecycleAdapterHomeSale(getContext());
        adapterHomeSale.setOnItemClickListener(onRecycleItemClickListener);
        recycle_home_sale.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycle_home_sale.addItemDecoration(new GridSpacingItemDecoration(1, DensityUtil.dp2px(10), GridLayoutManager.HORIZONTAL, false));
        recycle_home_sale.setAdapter(adapterHomeSale);

        adapterHomeNew = new RecycleAdapterHomeNew(getContext());
        adapterHomeNew.setOnItemClickListener(onRecycleItemClickListener);
        recycle_home_new.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
        recycle_home_new.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(10), GridLayoutManager.HORIZONTAL, true));
        recycle_home_new.setAdapter(adapterHomeNew);

        pagerAdapterHomeRecommend = new PagerAdapterHomeRecommend(getChildFragmentManager(), new String[]{"热门精选", "基础护肤", "时尚彩妆", "品牌香水"});
        tab_home_recommend.setupWithViewPager(pager_home_recommend);
        pager_home_recommend.setAdapter(pagerAdapterHomeRecommend);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_search:
                SearchActivity.start(getActivity());
                break;
            case R.id.btn_new_more:
                SectionActivity.start(getActivity());
                break;
            case R.id.btn_brand_more:
                SectionActivity.start(getActivity());
                break;
        }
    }

    private OnRecycleItemClickListener onRecycleItemClickListener = new OnRecycleItemClickListener() {
        @Override
        public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
            ProductDetailActivity.start(getActivity());
        }
    };
}
