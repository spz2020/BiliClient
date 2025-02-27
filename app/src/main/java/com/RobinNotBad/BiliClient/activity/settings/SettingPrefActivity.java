package com.RobinNotBad.BiliClient.activity.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.RobinNotBad.BiliClient.R;
import com.RobinNotBad.BiliClient.activity.base.RefreshListActivity;
import com.RobinNotBad.BiliClient.adapter.SettingsAdapter;
import com.RobinNotBad.BiliClient.model.SettingSection;
import com.RobinNotBad.BiliClient.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class SettingPrefActivity extends RefreshListActivity {

    @SuppressLint({"MissingInflatedId", "SetTextI18n", "InflateParams"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPageName("偏好设置");
        Log.e("debug", "进入偏好设置");

        final List<SettingSection> sectionList = new ArrayList<>() {{
            add(new SettingSection("switch", "禁用返回键", "back_disable", getString(R.string.desc_back_disable), "false"));
            add(new SettingSection("switch", "收藏夹单选", "fav_single", getString(R.string.desc_fav_single), "false"));
            add(new SettingSection("switch", "收藏成功提示", "fav_notice", getString(R.string.desc_fav_notice), "false"));
            add(new SettingSection("switch", "显示视频标签", "tags_enable", getString(R.string.desc_tags_enable), "true"));
            add(new SettingSection("switch", "使用新版推荐API", SharedPreferencesUtil.RCMD_API_NEW_PARAM, getString(R.string.desc_new_rcmd_param_enable), "false"));
            add(new SettingSection("switch", "视频相关推荐", "related_enable", getString(R.string.desc_related_enable), "true"));
            add(new SettingSection("switch", "创作中心", "creative_enable", getString(R.string.desc_creative_enable), "true"));
            add(new SettingSection("switch", "长按复制", "copy_enable", getString(R.string.desc_copy_enable), "true"));
            add(new SettingSection("switch", "点击封面播放", "cover_play_enable", getString(R.string.desc_cover_play_enable), "false"));
            add(new SettingSection("switch", "加载渐入渐出动画", SharedPreferencesUtil.LOAD_TRANSITION, getString(R.string.desc_load_transition), "true"));
            add(new SettingSection("switch", "翻动时不加载图片", "image_no_load_onscroll", getString(R.string.desc_img_no_load_onscroll), "true"));
            add(new SettingSection("switch", "识别链接", "link_enable", getString(R.string.desc_link_enable), "true"));
            add(new SettingSection("switch", "更严格的URL链接匹配", SharedPreferencesUtil.STRICT_URL_MATCH, getString(R.string.desc_strict_url_match), "false"));
            add(new SettingSection("switch", "异步加载布局", SharedPreferencesUtil.ASYNC_INFLATE_ENABLE, getString(R.string.desc_async_inflate_enable), "true"));
            add(new SettingSection("switch", "新提示信息显示方式", SharedPreferencesUtil.SNACKBAR_ENABLE, "打开此选项，会启用新提示信息显示方式\n * 开发中，仍有未适配的地方，有问题或没必要请关闭此选项", "false"));
        }};

        recyclerView.setHasFixedSize(true);

        SettingsAdapter adapter = new SettingsAdapter(this, sectionList);
        setAdapter(adapter);

        setRefreshing(false);

    }

}