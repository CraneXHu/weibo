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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.R;
import me.pkhope.jianwei.utils.TimeConverter;

/**
 * Created by pkhope on 2016/6/8.
 */
public class FriendsTimelineAdapter extends RecyclerView.Adapter<FriendsTimelineAdapter.TimelineViewHolder>{

    private List<Status> statusList = new ArrayList<>();

    private Context context;

    public FriendsTimelineAdapter(Context context, List<Status> list){
        this.context = context;
        statusList = list;
    }


    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_item,parent,false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimelineViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        Status status = statusList.get(position);
        holder.content.setText(status.text);
        holder.nickname.setText(status.user.name);
        holder.time.setText(TimeConverter.convert(status.created_at));

        Picasso.with(context)
                .load(status.user.profile_image_url)
                .into(holder.avatar);

        if (status.pic_urls != null){

            holder.image.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(status.pic_urls.get(0))
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public class TimelineViewHolder extends RecyclerView.ViewHolder{

        ImageView avatar;
        TextView nickname;
        TextView time;
        TextView content;
        ImageView image;

        public TimelineViewHolder(View itemView) {
            super(itemView);

            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            nickname = (TextView) itemView.findViewById(R.id.nickname);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
