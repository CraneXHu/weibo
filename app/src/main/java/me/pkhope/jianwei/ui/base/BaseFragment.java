package me.pkhope.jianwei.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import me.pkhope.jianwei.R;
import me.pkhope.jianwei.ui.adapter.RecyclerViewDivider;

/**
 * Created by pkhope on 2016/6/12.
 */
public class BaseFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    protected RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_layout, container, false);

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
                refreshData();
            }
        });

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycle_view);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), RecyclerViewDivider.VERTICAL_LIST));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                boolean isBottom = lastVisibleItem >= adapter.getItemCount() - 4;

                if (isBottom && dy > 0) {

                    loadMore();
                }
            }
        });

        refreshData();
    }



    protected void setRefreshing(boolean bRefresh){

//        if (bRefresh == true){
//            if (!swipeRefreshLayout.isRefreshing()){
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        }else{
//            if (swipeRefreshLayout.isRefreshing()){
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }

    }

    protected void refreshData(){

    }

    protected void loadMore(){
//        setRefreshing(true);
    }
}
