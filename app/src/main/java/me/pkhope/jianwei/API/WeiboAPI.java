package me.pkhope.jianwei.api;

import android.content.Context;
import android.graphics.Bitmap;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.FriendshipsAPI;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

/**
 * Created by pkhope on 2016/6/12.
 */
public class WeiboAPI {

    private StatusesAPI statusesAPI;
    private CommentsAPI commentsAPI;
    private FriendshipsAPI friendshipsAPI;
    private UsersAPI usersAPI;
    private Oauth2AccessToken token;

    public WeiboAPI(Context context, String appKey, Oauth2AccessToken token){
        statusesAPI = new StatusesAPI(context,appKey,token);
        commentsAPI = new CommentsAPI(context,appKey,token);
        friendshipsAPI = new FriendshipsAPI(context,appKey,token);
        usersAPI = new UsersAPI(context,appKey,token);

        this.token = token;
    }

    public long getUid(){
        return Long.parseLong(token.getUid());
    }

    public Oauth2AccessToken getToken(){
        return token;
    }

    public void friendsTimeline(int page, int count, int type ,RequestListener listener){
        statusesAPI.friendsTimeline(0, 0, count, page, false, type, false,listener);
    }

    public void userTimeline(int page, int count, RequestListener listener){
        statusesAPI.userTimeline(0,0,count,page,false,0,false,listener);
    }

    public void userTimeline(String name,int page, int count, RequestListener listener){
        statusesAPI.userTimeline(name,0,0,count,page,false,0,false,listener);
    }

    public void toME(int page, int count, RequestListener listener){
        commentsAPI.toME(0,0,count,page,0,0,listener);
    }

    public void byME(int page, int count, RequestListener listener){
        commentsAPI.byME(0,0,count,page,0,listener);
    }

    public void mentions(int page, int count, RequestListener listener){
        statusesAPI.mentions(0,0,count,page,0,0,0,true,listener);
    }

    public void friends(String name,int page,int count,RequestListener listener){
        friendshipsAPI.friends(name,count,page,false,listener);
    }

    public void friends(long id,int page,int count,RequestListener listener){
        friendshipsAPI.friends(id,count,page,false,listener);
    }

    public void follower(String name,int page,int count,RequestListener listener){
        friendshipsAPI.followers(name,count,page,false,listener);
    }

    public void follower(long id,int page,int count,RequestListener listener){
        friendshipsAPI.followers(id,count,page,false,listener);
    }

    public void show(String name, RequestListener listener){
        usersAPI.show(name,listener);
    }

    public void show(long id, RequestListener listener){
        usersAPI.show(id,listener);
    }

    public void show(long id, int page, int count, RequestListener listener){
        commentsAPI.show(id,0,0,count,page,0,listener);
    }

    public void repostTimeline(long id, int count, int page, RequestListener listener){
        statusesAPI.repostTimeline(id,0,0,count,page,0,listener);
    }

    public void update(String content, RequestListener listener){
        statusesAPI.update(content, String.valueOf(0.0), String.valueOf(0.0), listener);
    }

    public void upload(String content, Bitmap bitmap, RequestListener listener){
        statusesAPI.upload(content,bitmap,String.valueOf(0.0),String.valueOf(0.0),listener);
    }

    public void create(String comment, long id, boolean comment_ori, RequestListener listener){
        commentsAPI.create(comment,id,comment_ori,listener);
    }

    public void repost(long id, String status, int commentType, RequestListener listener){
        statusesAPI.repost(id,status,commentType,listener);
    }

    public void showWeiboDetail(long id, RequestListener listener){
        statusesAPI.show(id,listener);
    }
}
