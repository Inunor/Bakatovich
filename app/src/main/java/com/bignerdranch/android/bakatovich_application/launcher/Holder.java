package com.bignerdranch.android.bakatovich_application.launcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.bakatovich_application.R;


class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {

        private final View imageView;
        private final TextView textView;

        GridHolder(final View view) {
            super(view);
            imageView = view.findViewById(R.id.launcher_image);
            textView = view.findViewById(R.id.launcher_text);
        }

        View getImageView() {
            return imageView;
        }

        TextView getTextView() {
            return textView;
        }
    }
}
