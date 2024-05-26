package com.RobinNotBad.BiliClient.model;

import com.RobinNotBad.BiliClient.api.ReplyApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Reply {
    public long rpid;
    public String ofBvid = "";
    public String pubTime;
    public UserInfo sender;
    public String message;
    public ArrayList<Emote> emotes;
    public Map<String, Long> atNameToMid;
    public ArrayList<String> pictureList;
    public int likeCount;
    public boolean upLiked;
    public boolean upReplied;
    public boolean liked;
    public int childCount;
    public boolean isDynamic;
    public ArrayList<String> childMsgList;

    public Reply(){}
}
