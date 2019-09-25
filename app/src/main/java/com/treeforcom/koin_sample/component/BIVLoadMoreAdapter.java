package com.treeforcom.koin_sample.component;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ScrollingView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.treeforcom.koin_sample.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BIVLoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final byte TYPE_FOOTER = -2;
    private static final byte TYPE_NO_MORE = -3;
    private static final byte TYPE_LOAD_FAILED = -4;

    private RecyclerView.Adapter mAdapter;
    private View mFooterView;
    private View mNoMoreView;
    private View mLoadFailedView;

    private RecyclerView mRecyclerView;
    private OnLoadMoreListener mOnLoadMoreListener;

    private Enabled mEnabled;
    private boolean mIsLoading;
    private boolean mShouldRemove;
    private boolean mIsLoadFailed;

    BIVLoadMoreAdapter(@NonNull RecyclerView.Adapter adapter) {
        registerAdapter(adapter);
    }

    private void registerAdapter(RecyclerView.Adapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter can not be null!");
        }

        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mObserver);
        mEnabled = new Enabled(mOnEnabledListener);
    }

    RecyclerView.Adapter getOriginalAdapter() {
        return mAdapter;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            int mFooterResId = View.NO_ID;
            if (mFooterResId != View.NO_ID) {
                mFooterView = BIVLoadMoreHelper.inflate(parent, mFooterResId);
            }
            if (mFooterView != null) {
                return new FooterHolder(mFooterView);
            }
            View view = BIVLoadMoreHelper.inflate(parent, R.layout.loadmore_base_footer);
            return new FooterHolder(view);
        } else if (viewType == TYPE_NO_MORE) {
            int mNoMoreResId = View.NO_ID;
            if (mNoMoreResId != View.NO_ID) {
                mNoMoreView = BIVLoadMoreHelper.inflate(parent, mNoMoreResId);
            }
            if (mNoMoreView != null) {
                return new NoMoreHolder(mNoMoreView);
            }
            View view = BIVLoadMoreHelper.inflate(parent, R.layout.loadmore_base_no_more);
            return new NoMoreHolder(view);
        } else if (viewType == TYPE_LOAD_FAILED) {
            int mLoadFailedResId = View.NO_ID;
            if (mLoadFailedResId != View.NO_ID) {
                mLoadFailedView = BIVLoadMoreHelper.inflate(parent, mLoadFailedResId);
            }
            View view = mLoadFailedView;
            if (view == null) {
                view = BIVLoadMoreHelper.inflate(parent, R.layout.loadmore_base_load_failed);
            }
            return new LoadFailedHolder(view, mEnabled, mOnLoadMoreListener);
        }

        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Log.i("onBind", "position");
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position, @NotNull List<Object> payloads) {
        if (holder instanceof FooterHolder) {
            if (!canScroll() && mOnLoadMoreListener != null && !mIsLoading) {
                mIsLoading = true;
                mRecyclerView.post(() -> mOnLoadMoreListener.onLoadMore(mEnabled));
            }
        } else if (holder instanceof NoMoreHolder || holder instanceof LoadFailedHolder) {
            Log.i("Alert", "no handle");
        } else {
            mAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        int count = mAdapter.getItemCount();
        return getLoadMoreEnabled() ? count + 1 : count;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mAdapter.getItemCount() && mIsLoadFailed) {
            return TYPE_LOAD_FAILED;
        }
        if (position == mAdapter.getItemCount() && (getLoadMoreEnabled() || mShouldRemove)) {
            return TYPE_FOOTER;
        } else if (position == mAdapter.getItemCount() && !getLoadMoreEnabled()) {
            return TYPE_NO_MORE;
        }
        return mAdapter.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        final int itemViewType = getItemViewType(position);
        if (mAdapter.hasStableIds() && itemViewType != TYPE_FOOTER &&
                itemViewType != TYPE_LOAD_FAILED && itemViewType != TYPE_NO_MORE) {
            return mAdapter.getItemId(position);
        }
        return super.getItemId(position);
    }

    private boolean canScroll() {
        if (mRecyclerView == null) {
            throw new NullPointerException("mRecyclerView is null, you should setAdapter(recyclerAdapter);");
        }
        return canScrollingViewScrollVertically(mRecyclerView);
    }

    private boolean canScrollingViewScrollVertically(ScrollingView view) {
        final int offset = view.computeVerticalScrollOffset();
        final int range = view.computeVerticalScrollRange() -
                view.computeVerticalScrollExtent();
        if (range == 0) return false;
        if (-1 < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {

        FooterHolder(View itemView) {
            super(itemView);
            BIVLoadMoreHelper.setItemViewFullSpan(itemView);
        }
    }

    public static class NoMoreHolder extends RecyclerView.ViewHolder {

        NoMoreHolder(View itemView) {
            super(itemView);
            BIVLoadMoreHelper.setItemViewFullSpan(itemView);
        }
    }

    public static class LoadFailedHolder extends RecyclerView.ViewHolder {

        LoadFailedHolder(View itemView, final Enabled enabled, final OnLoadMoreListener listener) {
            super(itemView);
            BIVLoadMoreHelper.setItemViewFullSpan(itemView);
            itemView.setOnClickListener(view -> {
                enabled.setLoadFailed(false);
                if (listener != null) {
                    listener.onLoadMore(enabled);
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(mOnScrollListener);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = ((GridLayoutManager) layoutManager);
            final GridLayoutManager.SpanSizeLookup originalSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int itemViewType = getItemViewType(position);
                    if (itemViewType == TYPE_FOOTER || itemViewType == TYPE_NO_MORE ||
                            itemViewType == TYPE_LOAD_FAILED) {
                        return gridLayoutManager.getSpanCount();
                    } else if (originalSizeLookup != null) {
                        return originalSizeLookup.getSpanSize(position);
                    }

                    return 1;
                }
            });
        }
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!getLoadMoreEnabled() || mIsLoading) {
                return;
            }

            if (newState == RecyclerView.SCROLL_STATE_IDLE && mOnLoadMoreListener != null) {
                boolean isBottom;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    isBottom = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition()
                            >= layoutManager.getItemCount() - 1;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager sgLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] into = new int[sgLayoutManager.getSpanCount()];
                    sgLayoutManager.findLastVisibleItemPositions(into);

                    isBottom = last(into) >= layoutManager.getItemCount() - 1;
                } else {
                    isBottom = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition()
                            >= layoutManager.getItemCount() - 1;
                }

                if (isBottom) {
                    mIsLoading = true;
                    mOnLoadMoreListener.onLoadMore(mEnabled);
                }
            }
        }
        private int last(int[] lastPositions) {
            int last = lastPositions[0];
            for (int value : lastPositions) {
                if (value > last) {
                    last = value;
                }
            }
            return last;
        }
    };

    @Override
    public void onDetachedFromRecyclerView(@NotNull RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(mOnScrollListener);
        mAdapter.unregisterAdapterDataObserver(mObserver);
        mRecyclerView = null;
    }

    void setLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore(Enabled enabled);
    }

    void setLoadMoreEnabled(boolean enabled) {
        mEnabled.setLoadMoreEnabled(enabled);
    }

    private boolean getLoadMoreEnabled() {
        return mEnabled.getLoadMoreEnabled() && mAdapter.getItemCount() >= 0;
    }

    private interface OnEnabledListener {
        void notifyChanged();

        void notifyLoadFailed(boolean isLoadFailed);
    }

    private OnEnabledListener mOnEnabledListener = new OnEnabledListener() {
        @Override
        public void notifyChanged() {
            mShouldRemove = true;
        }

        @Override
        public void notifyLoadFailed(boolean isLoadFailed) {
            mIsLoadFailed = isLoadFailed;
            notifyFooterHolderChanged();
        }
    };

    void setShouldRemove() {
        mShouldRemove = true;
    }

    void setLoadFailed(boolean isLoadFailed) {
        mEnabled.setLoadFailed(isLoadFailed);
    }

    public static class Enabled {
        private boolean mLoadMoreEnabled = true;
        private boolean mIsLoadFailed = false;
        private OnEnabledListener mListener;

        Enabled(OnEnabledListener listener) {
            mListener = listener;
        }

        public void setLoadMoreEnabled(boolean enabled) {
            final boolean canNotify = mLoadMoreEnabled;
            mLoadMoreEnabled = enabled;

            if (canNotify && !mLoadMoreEnabled) {
                mListener.notifyChanged();
            }
        }

        void setLoadFailed(boolean isLoadFailed) {
            if (mIsLoadFailed != isLoadFailed) {
                mIsLoadFailed = isLoadFailed;
                mListener.notifyLoadFailed(isLoadFailed);
                setLoadMoreEnabled(!mIsLoadFailed);
            }
        }

        public boolean getLoadMoreEnabled() {
            return mLoadMoreEnabled;
        }
    }

    private RecyclerView.AdapterDataObserver mObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (mShouldRemove) {
                mShouldRemove = false;
            }
            BIVLoadMoreAdapter.this.notifyDataSetChanged();
            mIsLoading = false;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (mShouldRemove && positionStart == mAdapter.getItemCount()) {
                mShouldRemove = false;
            }
            BIVLoadMoreAdapter.this.notifyItemRangeChanged(positionStart, itemCount);
            mIsLoading = false;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            if (mShouldRemove && positionStart == mAdapter.getItemCount()) {
                mShouldRemove = false;
            }
            BIVLoadMoreAdapter.this.notifyItemRangeChanged(positionStart, itemCount, payload);
            mIsLoading = false;
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (mRecyclerView.getChildCount() == 1) {
                BIVLoadMoreAdapter.this.notifyItemRemoved(0);
            }
            BIVLoadMoreAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
            notifyFooterHolderChanged();
            mIsLoading = false;
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (mShouldRemove && positionStart == mAdapter.getItemCount()) {
                mShouldRemove = false;
            }

            boolean shouldSync = false;
            if (mEnabled.getLoadMoreEnabled() && mAdapter.getItemCount() == 0) {
                setLoadMoreEnabled(false);
                shouldSync = true;
                if (getItemCount() == 1) {
                    BIVLoadMoreAdapter.this.notifyItemRemoved(0);
                }
            }
            BIVLoadMoreAdapter.this.notifyItemRangeRemoved(positionStart, itemCount);
            if (shouldSync) {
                setLoadMoreEnabled(true);
            }
            mIsLoading = false;
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (mShouldRemove && (fromPosition == mAdapter.getItemCount() || toPosition == mAdapter.getItemCount())) {
                throw new IllegalArgumentException("can not move last position after setLoadMoreEnabled(false)");
            }
            BIVLoadMoreAdapter.this.notifyItemMoved(fromPosition, toPosition);
            mIsLoading = false;
        }
    };

    private void notifyFooterHolderChanged() {
        if (getLoadMoreEnabled()) {
            BIVLoadMoreAdapter.this.notifyItemChanged(mAdapter.getItemCount());
        } else if (mShouldRemove) {
            mShouldRemove = false;

            int position = mAdapter.getItemCount();
            RecyclerView.ViewHolder viewHolder =
                    mRecyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder instanceof FooterHolder) {
                BIVLoadMoreAdapter.this.notifyItemRemoved(position);
            } else {
                BIVLoadMoreAdapter.this.notifyItemChanged(position);
            }
        }
    }
}