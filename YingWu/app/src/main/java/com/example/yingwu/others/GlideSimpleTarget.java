package com.example.yingwu.others;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import ch.ielse.view.imagewatcher.ImageWatcher;

public class GlideSimpleTarget extends SimpleTarget<Bitmap> {

    private ImageWatcher.LoadCallback mLoadCallback;

    public GlideSimpleTarget(ImageWatcher.LoadCallback loadCallback) {
        this.mLoadCallback = loadCallback;
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {
        if (mLoadCallback != null) {
            mLoadCallback.onLoadStarted(placeholder);
        }
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        if (mLoadCallback != null) {
            mLoadCallback.onLoadFailed(errorDrawable);
        }
    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        if (mLoadCallback != null) {
            mLoadCallback.onResourceReady(resource);
        }
    }
}

