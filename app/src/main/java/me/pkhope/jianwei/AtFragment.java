package me.pkhope.jianwei;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.CommentList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkhope on 2016/6/10.
 */
public class AtFragment extends Fragment {

    private int currentPage = 1;
    private List<Comment> commentList = new ArrayList<>();

    private MyCommentsAPI api;

    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                R.color.refresh_progress_2, R.color.refresh_progress_1);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPage(currentPage++);
            }
        });
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycle_view);
        commentAdapter = new CommentAdapter(getContext(),commentList);
        recyclerView.setAdapter(commentAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), RecyclerViewDivider.VERTICAL_LIST));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                boolean isBottom = lastVisibleItem >= commentAdapter.getItemCount() - 4;

                if (isBottom && dy > 0) {

                    loadPage(currentPage++);
                }
            }
        });
        Oauth2AccessToken token = AccessTokenPreference.loadAccessToken(getContext());
        api = new MyCommentsAPI(getContext(),Constants.APP_KEY,token);
        loadPage(currentPage++);
    }

    protected void loadPage(int page){

        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(true);
        }

        api.mentions(page, 10, new RequestListener() {
            @Override
            public void onComplete(String s) {

                CommentList data = CommentList.parse(s);
                if (data.commentList == null){
                    return;
                }
                commentList.addAll(data.commentList);
                commentAdapter.notifyDataSetChanged();

                if (swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                e.printStackTrace();
            }
        });
    }
}

