package me.pkhope.jianwei.utils;

import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkhope on 2016/6/11.
 */
public class StatusFilter {

    public static List<Status> filter(List<Status> list){

        List<Status> result = new ArrayList<>();
        for (Status s : list ){
            if (s.retweeted_status == null){
                result.add(s);
            }
        }

        return result;
    }
}
