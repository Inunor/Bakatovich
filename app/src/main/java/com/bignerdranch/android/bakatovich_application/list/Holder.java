package com.bignerdranch.android.bakatovich_application.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.bakatovich_application.R;


class Holder {

    static class ListHolder extends RecyclerView.ViewHolder {

        private final View imageView;
        private final TextView title;

        ListHolder(final View view) {
            super(view);

            imageView = view.findViewById(R.id.list_image);
            title = view.findViewById(R.id.list_title);
        }

        View getImageView() {
            return imageView;
        }

        TextView getTitle() {
            return title;
        }
    }
}
