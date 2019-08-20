package com.muhammedsafiulazam.velibstations.utils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Muhammed Safiul Azam on 25/07/2019.
 */

public abstract class RecyclerViewEndlessOnScrollListener extends RecyclerView.OnScrollListener {
    private int mItemsOffset = 5;
    private int mLastItemsCount = 0;
    private boolean isLoading = false;
    RecyclerView.LayoutManager mLayoutManager;

    public RecyclerViewEndlessOnScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int itemsCount = mLayoutManager.getItemCount();
        int lastVisibleItemIndex = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();

        if (itemsCount < mLastItemsCount) {
            this.mLastItemsCount = itemsCount;
            if (itemsCount == 0) {
                isLoading = true;
            }
        }

        if (isLoading && (itemsCount > mLastItemsCount)) {
            isLoading = false;
            mLastItemsCount = itemsCount;
        }

        if (!isLoading && itemsCount > 0 && (lastVisibleItemIndex + mItemsOffset) > itemsCount) {
            onLoadMore();
            isLoading = true;
        }
    }

    public abstract void onLoadMore();
}