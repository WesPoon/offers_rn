package com.offers_rn.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.offers_rn.R;
import com.offers_rn.SplashScreen;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.loader.DefaultImageLoader;
import com.veinhorn.scrollgalleryview.loader.DefaultVideoLoader;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 4/8/2016.
 */
public class ScollGallery extends ActionBarActivity {
    private static final ArrayList<String> images = new ArrayList<>(Arrays.asList(
            "http://cdn.bulbagarden.net/upload/thumb/b/b1/151Mew.png/250px-151Mew.png",
            "http://pldh.net/media/dreamworld/054.png",
            "http://cdn.bulbagarden.net/upload/thumb/7/70/079Slowpoke.png/250px-079Slowpoke.png",
            "https://media.eventhubs.com/images/characters/ssb4/ryu.png",
            "https://media.eventhubs.com/images/characters/ssb4/sheik.png",
            "https://media.eventhubs.com/images/characters/ssb4/sonic.png",
            "https://media.eventhubs.com/images/characters/ssb4/toon_link.png",
            "https://media.eventhubs.com/images/characters/ssb4/villager.png",
            "https://media.eventhubs.com/images/characters/ssb4/pikachu.png",
            "http://www.socialtalent.co/wp-content/uploads/blog-content/so-logo.png"
            ));

    private NewScrollGalleryView newScrollGalleryView;
    private HorizontalScrollView thumbNailScroll;
    private FloatingActionButton floatActionButton;
    private final List<MediaInfo> infos = new ArrayList<>(images.size());

    ParseUser user;
    String username="";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_gallery);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pick one as Icon");

        for (String url : images) infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url){

        }));

        newScrollGalleryView = (NewScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        thumbNailScroll = (HorizontalScrollView) findViewById(R.id.thumbnails_scroll_view);
        thumbNailScroll.setBackgroundResource(R.drawable.material_scrollgallery_gradient);
        thumbNailScroll.setMinimumHeight(150);

        floatActionButton = (FloatingActionButton) findViewById(R.id.scroll_gallery_sure);
        floatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadProfilePic();
   /*             String profile_pic_url = ((PicassoImageLoader)infos.get(newScrollGalleryView.getViewPager().getCurrentItem()).getLoader()).getURL();
                if(profile_pic_url==null) {
                    Toast.makeText(v.getContext(), "test null", Toast.LENGTH_LONG).show();
                }
                else{

                    SharedPreferences settings = getSharedPreferences(getApplicationContext().getString(R.string.app_name), 0);
                    SharedPreferences.Editor PE = settings.edit();
                    PE.putString("profile_pic_url", profile_pic_url);
                    PE.commit();
                    SQLiteDatabase mydatabase = openOrCreateDatabase("UserDB",MODE_PRIVATE,null);
                    Intent intent = new Intent(v.getContext(), SplashScreen.class);
                    startActivity(intent);
                } */
            }

            public void gotoNext(View v){

            }
        });

        newScrollGalleryView
                .setThumbnailSize(150)
                .setZoom(false)

                .setFragmentManager(getSupportFragmentManager())


    /*            .addMedia(MediaInfo.mediaLoader(new MediaLoader() {
                    @Override public boolean isImage() {
                        return true;
                    }

                    @Override public void loadMedia(Context context, ImageView imageView,
                                                    MediaLoader.SuccessCallback callback) {
                        imageView.setImageBitmap(toBitmap(R.drawable.cuhk));
                        callback.onSuccess();
                    }

                    @Override public void loadThumbnail(Context context, ImageView thumbnailView,
                                                        MediaLoader.SuccessCallback callback) {
                        thumbnailView.setImageBitmap(toBitmap(R.drawable.hku));
                        callback.onSuccess();
                    }
                }))
                .addMedia(MediaInfo.mediaLoader(new DefaultVideoLoader(movieUrl, R.mipmap.default_video))) */
                .addMedia(infos);

   //     NewScrollGalleryView.getViewPager();

        //        .setBackgroundColor(Color.parseColor("#87CEEB"));
    }

    private Bitmap toBitmap(int image) {
        return ((BitmapDrawable) getResources().getDrawable(image)).getBitmap();
    }

    private void uploadProfilePic(){

        if(ParseUser.getCurrentUser()==null){
            user = new ParseUser();
            user.setUsername(username);
            user.setPassword("565656");
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Let them use the app now.


                        Log.d("success","");


                    } else {
                        Log.d(e.getMessage(),"fail");
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                    }
                }
            });
        }
        else{

            final String profile_pic_url = ((PicassoImageLoader)infos.get(newScrollGalleryView.getViewPager().getCurrentItem()).getLoader()).getURL();
            user = ParseUser.getCurrentUser();
            user.setUsername(username);
            user.put("profile_pic",profile_pic_url);
            user.saveInBackground(new SaveCallback(){

                @Override
                public void done(ParseException arg0) {
                    // TODO Auto-generated method stub
                    if(arg0==null){


                        if(profile_pic_url==null) {

                        }
                        else{

                            SharedPreferences settings = getSharedPreferences(getApplicationContext().getString(R.string.app_name), 0);
                            SharedPreferences.Editor PE = settings.edit();
                            PE.putString("profile_pic_url", profile_pic_url);
                            PE.commit();
                            SQLiteDatabase mydatabase = openOrCreateDatabase("UserDB",MODE_PRIVATE,null);
                            Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                            startActivity(intent);
                        }

                    }
                    else{

                    }
                }


            });
        }





    }
}

