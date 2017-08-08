package com.offers_rn.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.offers_rn.R;
import com.offers_rn.uielement.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

/**
 * Created by user on 7/8/2016.
 */


    public class PicassoImageLoader implements MediaLoader {

        private String url;

        public PicassoImageLoader(String url) {
            this.url = url;
        }

        @Override
        public boolean isImage() {
            return true;
        }



    @Override
        public void loadMedia(Context context, final ImageView imageView, final MediaLoader.SuccessCallback callback) {

            imageView.setBackgroundColor(Color.parseColor("#87CEEB"));
       /*     imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(imageView.getContext(),AlertDialog.THEME_HOLO_LIGHT);
                    Log.d("test click","click");
                    builder.setMessage("Sure");
                    builder.create().show();
                }
            }); */

            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView, new ImageCallback(callback));


        }

        @Override
        public void loadThumbnail(Context context, ImageView thumbnailView, MediaLoader.SuccessCallback callback) {
            Picasso.with(context)
                    .load(url)
                    .resize(100, 100)
                    .placeholder(R.drawable.placeholder_image)
                    .centerInside()
                    .into(thumbnailView, new ImageCallback(callback));
        }

        public String getURL(){
            return this.url;
        }

        private static class ImageCallback implements Callback {

            private final MediaLoader.SuccessCallback callback;

            public ImageCallback(SuccessCallback callback) {
                this.callback = callback;
            }

            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onError() {

            }
        }
    }
