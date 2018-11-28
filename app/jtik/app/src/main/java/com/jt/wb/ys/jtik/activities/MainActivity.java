package com.jt.wb.ys.jtik.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.jt.wb.ys.common.app.Activity;
import com.jt.wb.ys.common.widget.PortraitView;
import com.jt.wb.ys.jtik.R;
import com.jt.wb.ys.jtik.fragments.main.ActiveFragment;
import com.jt.wb.ys.jtik.fragments.main.ContactFragment;
import com.jt.wb.ys.jtik.fragments.main.GroupFragment;
import com.jt.wb.ys.jtik.helper.NavHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {

    @BindView(R.id.appbar)
    View mLayAppBar;

    @BindView(R.id.img_portrait)
    PortraitView mPortrait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

    private NavHelper<Integer> navHelper;

    /**
     * MainActivity 显示的入口
     * @param context 上下文
     */
    public static void show(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        navHelper = new NavHelper<>(this, getSupportFragmentManager(), R.id.lay_container, this);
        navHelper
                .add(R.id.action_home, new NavHelper.Tab<>(ActiveFragment.class, R.string.title_home))
                .add(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.title_group))
                .add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact));

        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .into(new ViewTarget<View, GlideDrawable>(mLayAppBar) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setBackground(resource.getCurrent());
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        // 初始化点击
        Menu menu = mNavigation.getMenu();
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    @Override
    protected void initWindows() {
        super.initWindows();
    }

    @Override
    protected Boolean initArgs(Bundle bundle) {
        return super.initArgs(bundle);
    }

    @OnClick(R.id.img_search)
    public void onSearchMenuClick(){

    }

    @OnClick(R.id.btn_action)
    public void onActionClick(){
        AccountActivity.show(this);
    }

    /**
     * 底部导航栏点击监听
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return navHelper.performClickMenu(item.getItemId());
    }

    /**
     * NavHelper 处理后的回调方法
     * @param newTab
     * @param oldTab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        mTitle.setText(newTab.extra);

        // 对浮动按钮进行隐藏与显示的动画
        float transY = 0;
        float rotation = 0;

        if (Objects.equals(newTab.extra, R.string.title_home)){
            // 主界面时隐藏
            transY = Ui.dipToPx(getResources(), 76);
        }else {
            // TransY 默认为 0，则显示
            if (Objects.equals(newTab.extra, R.string.title_group)){
                // 群
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
            }else {
                // 联系人
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;
            }
        }

        // 开始动画，旋转 -> Y 轴唯一 -> 弹性插值器 -> 时间
        mAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(480)
                .start();
    }
}
