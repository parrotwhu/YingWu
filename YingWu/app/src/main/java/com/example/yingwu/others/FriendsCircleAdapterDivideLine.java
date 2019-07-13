package com.example.yingwu.others;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yingwu.utils.Utils;

public class FriendsCircleAdapterDivideLine extends RecyclerView.ItemDecoration {
    private int mDivideHeight;

    public FriendsCircleAdapterDivideLine() {
        mDivideHeight = Utils.dp2px(0.5f);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0, 0, 0, mDivideHeight);
    }
}


