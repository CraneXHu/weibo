package me.pkhope.jianwei.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.R;

/**
 * Created by pkhope on 2016/6/12.
 */
public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> userList = new ArrayList<>();

    private Context context;

    public UserAdapter(Context context, List<User> list){
        this.context = context;
        userList = list;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nickname.setText(user.name);
        holder.content.setText(user.description);

        Glide.with(context)
                .load(user.profile_image_url)
                .centerCrop()
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView nickname;
        TextView content;

        public UserViewHolder(View itemView) {
            super(itemView);

            avatar = (ImageView)itemView.findViewById(R.id.avatar);
            nickname = (TextView)itemView.findViewById(R.id.nickname);
            content = (TextView)itemView.findViewById(R.id.content);
        }
    }
}
