package me.pkhope.jianwei.models;

import android.text.TextUtils;

import com.sina.weibo.sdk.openapi.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thinkpad on 2016/6/12.
 */
public class UserList {

    public ArrayList<User> userList;
    public boolean hasvisible;
    public String previous_cursor;
    public String next_cursor;
    public int total_number;
    public Object[] advertises;

    public static UserList parse(String jsonString){
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        UserList users = new UserList();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            users.hasvisible      = jsonObject.optBoolean("hasvisible", false);
            users.previous_cursor = jsonObject.optString("previous_cursor", "0");
            users.next_cursor     = jsonObject.optString("next_cursor", "0");
            users.total_number    = jsonObject.optInt("total_number", 0);

            JSONArray jsonArray = jsonObject.optJSONArray("users");
            int length = jsonArray.length();
            if (jsonArray != null && length > 0){

                users.userList = new ArrayList<>();
                for (int ix = 0; ix < length; ix++){
                    users.userList.add(User.parse(jsonArray.getJSONObject(ix)));
                }
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        return users;
    }
}
