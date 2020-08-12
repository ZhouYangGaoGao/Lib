package com.zhy.photo;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LifeFragment extends Fragment {
   private OnResultListener listener;

    public void setListener(OnResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (listener!=null)listener.onActivityResult(requestCode,resultCode,data);
    }

    interface OnResultListener {
        void onActivityResult(int requestCode, int resultCode, @Nullable Intent data);
    }
}
