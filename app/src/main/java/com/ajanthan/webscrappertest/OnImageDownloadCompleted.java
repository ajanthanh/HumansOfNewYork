package com.ajanthan.webscrappertest;

import android.graphics.Bitmap;

/**
 * Created by ajanthan on 16-02-05.
 */
public interface OnImageDownloadCompleted {
    void onTaskCompleted(Bitmap img, int position);
}