package com.bignerdranch.android.bakatovich_application;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bignerdranch.android.bakatovich_application.R;


public class Holder {

    public static class GridHolder extends RecyclerView.ViewHolder {

        private final View imageView;
        private final TextView textView;

        public GridHolder(final View view) {
            super(view);

            imageView = view.findViewById(R.id.launcher_image);
            textView = view.findViewById(R.id.launcher_text);
        }

        public View getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public static class ListHolder extends RecyclerView.ViewHolder {

        private final View imageView;
        private final TextView title;

        public ListHolder(final View view) {
            super(view);

            imageView = view.findViewById(R.id.list_image);
            title = view.findViewById(R.id.list_title);
        }

        public View getImageView() {
            return imageView;
        }

        public TextView getTitle() {
            return title;
        }
    }

    public static class ApplicationHolder extends RecyclerView.ViewHolder {

        private final View imageView;
        private final TextView textView;

        public ApplicationHolder(final View view) {
            super(view);

            imageView = view.findViewById(R.id.application_image);
            textView = view.findViewById(R.id.application_text);
        }

        public View getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
