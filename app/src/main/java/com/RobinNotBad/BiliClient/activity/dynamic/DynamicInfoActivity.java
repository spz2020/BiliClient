package com.RobinNotBad.BiliClient.activity.dynamic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.RobinNotBad.BiliClient.R;
import com.RobinNotBad.BiliClient.activity.base.BaseActivity;
import com.RobinNotBad.BiliClient.activity.reply.ReplyFragment;
import com.RobinNotBad.BiliClient.adapter.viewpager.ViewPagerFragmentAdapter;
import com.RobinNotBad.BiliClient.api.DynamicApi;
import com.RobinNotBad.BiliClient.api.ReplyApi;
import com.RobinNotBad.BiliClient.event.ReplyEvent;
import com.RobinNotBad.BiliClient.helper.TutorialHelper;
import com.RobinNotBad.BiliClient.model.Dynamic;
import com.RobinNotBad.BiliClient.util.AnimationUtils;
import com.RobinNotBad.BiliClient.util.AsyncLayoutInflaterX;
import com.RobinNotBad.BiliClient.util.CenterThreadPool;
import com.RobinNotBad.BiliClient.util.MsgUtil;
import com.RobinNotBad.BiliClient.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

//动态信息页面
//2023-10-03

public class DynamicInfoActivity extends BaseActivity {

    ReplyFragment rFragment;
    private long seek_reply;

    @SuppressLint({"MissingInflatedId", "InflateParams"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        this.seek_reply = getIntent().getLongExtra("seekReply", -1);

        new AsyncLayoutInflaterX(this).inflate(R.layout.activity_simple_viewpager, null, (layoutView, resId, parent) -> {
            setContentView(layoutView);
            setTopbarExit();
            Intent intent = getIntent();
            long id = intent.getLongExtra("id", 0);

            TextView pageName = findViewById(R.id.pageName);
            pageName.setText("动态详情");

            TutorialHelper.show(R.xml.tutorial_dynamic_info, this, "dynamic_info", 1);

            CenterThreadPool.run(() -> {
                try {
                    Dynamic dynamic = DynamicApi.getDynamic(id);

                    List<Fragment> fragmentList = new ArrayList<>();
                    DynamicInfoFragment diFragment = DynamicInfoFragment.newInstance(dynamic);
                    fragmentList.add(diFragment);
                    rFragment = ReplyFragment.newInstance(dynamic.comment_id, dynamic.comment_type, seek_reply, dynamic.userInfo.mid);
                    rFragment.replyType = ReplyApi.REPLY_TYPE_DYNAMIC;
                    rFragment.setSource(dynamic);
                    fragmentList.add(rFragment);

                    ViewPagerFragmentAdapter vpfAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);

                    runOnUiThread(() -> {
                        ViewPager viewPager = findViewById(R.id.viewPager);
                        viewPager.setAdapter(vpfAdapter);  //没啥好说的，教科书式的ViewPager使用方法
                        View view; if ((view = diFragment.getView()) != null) view.setVisibility(View.GONE);
                        if (seek_reply != -1) viewPager.setCurrentItem(1);

                        if (SharedPreferencesUtil.getBoolean("first_dynamicinfo", true)) {
                            MsgUtil.showMsg("下载完成", this);
                            SharedPreferencesUtil.putBoolean("first_dynamicinfo", false);
                        }

                        diFragment.setOnFinishLoad(() -> AnimationUtils.crossFade(findViewById(R.id.loading), diFragment.getView()));
                    });

                } catch (Exception e) {
                    runOnUiThread(() -> {
                        MsgUtil.err(e, this);
                        ((ImageView) findViewById(R.id.loading)).setImageResource(R.mipmap.loading_2233_error);
                    });
                }
            });
        });
    }

    @Override
    protected boolean eventBusEnabled() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true, priority = 1)
    public void onEvent(ReplyEvent event) {
        rFragment.notifyReplyInserted(event);
    }

}