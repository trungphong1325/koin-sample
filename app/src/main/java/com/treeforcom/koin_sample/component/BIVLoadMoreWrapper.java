package com.treeforcom.koin_sample.component;

import androidx.recyclerview.widget.RecyclerView;

public class BIVLoadMoreWrapper {
    private final BIVLoadMoreAdapter mLoadMoreAdapter;
    private BIVLoadMoreWrapper(BIVLoadMoreAdapter loadMoreAdapter) {
        mLoadMoreAdapter = loadMoreAdapter;
    }

    public static BIVLoadMoreWrapper with(RecyclerView.Adapter adapter) {
        BIVLoadMoreAdapter loadMoreAdapter = new BIVLoadMoreAdapter(adapter);
        return new BIVLoadMoreWrapper(loadMoreAdapter);
    }

    public BIVLoadMoreWrapper setListener(BIVLoadMoreAdapter.OnLoadMoreListener listener) {
        mLoadMoreAdapter.setLoadMoreListener(listener);
        return this;
    }

    public BIVLoadMoreWrapper setLoadMoreEnabled(boolean enabled) {
        mLoadMoreAdapter.setLoadMoreEnabled(enabled);
        if (!enabled) {
            mLoadMoreAdapter.setShouldRemove();
        }
        return this;
    }

    public void setLoadFailed(boolean isLoadFailed) {
        mLoadMoreAdapter.setLoadFailed(isLoadFailed);
    }

    public BIVLoadMoreAdapter into(RecyclerView recyclerView) {
        mLoadMoreAdapter.setHasStableIds(mLoadMoreAdapter.getOriginalAdapter().hasStableIds());
        recyclerView.setAdapter(mLoadMoreAdapter);
        return mLoadMoreAdapter;
    }
}