package me.pkhope.jianwei.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.openapi.models.Comment;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.R;
import me.pkhope.jianwei.utils.TimeConverter;

/**
 * Created by pkhope on 2016/6/11.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> commentList = new ArrayList<>();

    public CommentAdapter(Context context, List<Comment> list){
        this.context = context;
        commentList = list;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item,parent,false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {

        Comment comment = commentList.get(position);
        holder.content.setText(comment.text);
        holder.nickname.setText(comment.user.name);
        holder.time.setText(TimeConverter.convert(comment.created_at));

        Glide.with(context)
                .load(comment.user.profile_image_url)
                .centerCrop()
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView content;
        private TextView nickname;
        private TextView time;

        public CommentViewHolder(View itemView) {
            super(itemView);

            avatar = (ImageView)itemView.findViewById(R.id.avatar);
            content = (TextView)itemView.findViewById(R.id.content);
            nickname = (TextView)itemView.findViewById(R.id.nickname);
            time = (TextView)itemView.findViewById(R.id.time);
        }
    }
}
