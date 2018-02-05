package com.bignerdranch.android.bakatovich_application;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OffsetItemDecoration extends RecyclerView.ItemDecoration {

    private final int offset;

    public OffsetItemDecoration(final int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent, final RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(offset, offset, offset, offset);
    }
}