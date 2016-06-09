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
import android.widget.TextView;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import java.util.ArrayList;
import java.util.List;

import me.pkhope.jianwei.API.MyStatusesAPI;

/**
 * Created by pkhope on 2016/6/7.
 */
public class TimelineFragment extends Fragment {

    private MyStatusesAPI myStatusesAPI;
    private int currentPage = 1;
    private List<Status> statusList = new ArrayList<>();

    private TimelineAdapter timelineAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                R.color.refresh_progress_2, R.color.refresh_progress_1);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPage(currentPage++);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        timelineAdapter = new TimelineAdapter(getContext(),statusList);
        recyclerView.setAdapter(timelineAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new Divider(getActivity(),Divider.VERTICAL_LIST));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                boolean isBottom = lastVisibleItem >= timelineAdapter.getItemCount() - 4;

                if (isBottom && dy > 0) {
                    swipeRefreshLayout.setRefreshing(true);
                    loadPage(currentPage++);
                }
            }
        });

        Oauth2AccessToken token = AccessTokenPreference.loadAccessToken(getContext());
        myStatusesAPI = new MyStatusesAPI(getActivity(),Constants.APP_KEY,token);

        return view;
    }

    protected void loadPage(int page){

        myStatusesAPI.friendsTimeline(page, 10, new RequestListener() {

            @Override
            public void onComplete(String s) {

                StatusList data = StatusList.parse(s);
                statusList.addAll(data.statusList);
                timelineAdapter.notifyDataSetChanged();

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
