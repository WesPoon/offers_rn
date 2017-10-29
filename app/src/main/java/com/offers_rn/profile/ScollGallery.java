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

            "https://media.eventhubs.com/images/characters/ssb4/ryu.png",
            "https://media.eventhubs.com/images/characters/ssb4/sheik.png",
            "https://media.eventhubs.com/images/characters/ssb4/sonic.png",
            "https://media.eventhubs.com/images/characters/ssb4/toon_link.png",
            "https://media.eventhubs.com/images/characters/ssb4/villager.png",
            "https://media.eventhubs.com/images/characters/ssb4/pikachu.png",
            "https://twt-thumbs.washtimes.com/media/image/2015/08/08/58888ce53f4a63237e0f6a7067004816_c0-0-3964-2311_s885x516.jpg?77ac8e0073a3ccb248a507de545528ea1652665f",
            "https://b-i.forbesimg.com/naazneenkarmali/files/2013/09/400x31.jpg",
            "https://vignette.wikia.nocookie.net/evchk/images/3/33/7915650_980x1200_0.jpg/revision/latest?cb=20151213093609",
            "https://fortunedotcom.files.wordpress.com/2017/05/gettyimages-683157516.jpg?w=720&quality=85",
            "http://ntdimg.com/pic/2014/10-8/p5440711a776821217.jpg"
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

                Toast.makeText(v.getContext(),"TEST-1",Toast.LENGTH_SHORT).show();
                uploadProfilePic();

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
            
            final String profile_pic_url;
            profile_pic_url = ((PicassoImageLoader)infos.get(newScrollGalleryView.getViewPager().getCurrentItem()).getLoader()).getURL();
            user = ParseUser.getCurrentUser();
            user.put("profile_pic",profile_pic_url);
            user.saveInBackground(new SaveCallback(){

                @Override
                public void done(ParseException e) {
                    // TODO Auto-generated method stub
                    if(e==null){

                        Toast.makeText(getApplicationContext(),"TEST-3",Toast.LENGTH_SHORT).show();
                        if(profile_pic_url==null) {
                            Toast.makeText(getApplicationContext(),"TEST-4",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"TEST-5",Toast.LENGTH_SHORT).show();
                            SharedPreferences settings = getSharedPreferences(getApplicationContext().getString(R.string.app_name), 0);
                            SharedPreferences.Editor PE = settings.edit();
                            PE.putString("profile_pic_url", profile_pic_url);
                            PE.commit();
                            SQLiteDatabase mydatabase = openOrCreateDatabase("UserDB",MODE_PRIVATE,null);
                            finish();
                            System.gc();
                        }

                    }
                    else{

                    }
                }


            });
        }




    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}

