package me.pkhope.jianwei;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import me.pkhope.jianwei.ui.activity.MainActivity;

/**
 * Created by pkhope on 2016/6/24.
 */
public class PostService extends Service implements RequestListener {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String operation = intent.getStringExtra("operation");
        String content = intent.getStringExtra("content");
        if (operation.equals("send")){
            List<String> photos = intent.getStringArrayListExtra("photos");
            if (photos.size() == 0){
                sendText(content);
            } else {
                sendTextWithImage(content,photos.get(0));
            }
        } else {
            Long id = intent.getLongExtra("id",0);
            if (operation.equals("repost")){
                repost(id,content);
            } else {
                comment(id,content);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void sendText(String content){

        MainActivity.getWeiboAPI().update(content, this);
    }

    protected void sendTextWithImage(String content,String name){

        MainActivity.getWeiboAPI().upload(content, getBitmap(name), this);
    }

    protected void repost(Long id,String content){
        MainActivity.getWeiboAPI().repost(id,content,0,this);
    }

    protected void comment(Long id,String content){
        MainActivity.getWeiboAPI().create(content,id,false,this);
    }

    protected void showSuccessNotifiy(){
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Jianwei")
                .setContentText("发送成功")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        ((NotificationManager)getSystemService(Activity.NOTIFICATION_SERVICE)).notify(0,notification);
    }

    protected void showErrorNotifiy(){
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Jianwei")
                .setContentText("发送失败")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        ((NotificationManager)getSystemService(Activity.NOTIFICATION_SERVICE)).notify(1,notification);
    }

    protected Bitmap getBitmap(String name){

        Bitmap bitmap = null;
        try {
            FileInputStream fis = new FileInputStream(name);
            bitmap = BitmapFactory.decodeStream(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    public void onComplete(String s) {
        showSuccessNotifiy();
    }

    @Override
    public void onWeiboException(WeiboException e) {
        e.printStackTrace();
        showErrorNotifiy();
    }
}
