package animaladvertis.com.animaladvertis.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.john.waveview.WaveView;

import animaladvertis.com.animaladvertis.R;

import static com.baidu.location.h.j.t;

/**
 * Created by 47321 on 2017/3/1 0001.
 */

public class LoadImageUtil {
    public static void loadIMage(Context context,final ImageView imageView, String userPhotoAd, int symbol) {
        if (symbol == 0) {//以不缓存的形式加载图片
            Glide.with(context).load(userPhotoAd)
                    .asBitmap()
                    .placeholder(R.drawable.a1)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            imageView.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            imageView.setImageDrawable(errorDrawable);
                        }
                    });
        }
        if (symbol == 1) {//以缓存的形式加载图片
            Glide.with(context).load(userPhotoAd)
                    .asBitmap()
                    .placeholder(R.drawable.a1)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            imageView.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            imageView.setImageDrawable(errorDrawable);
                        }
                    });
        }
    }
    public static void loadIMage(Context context,final ImageView imageView, String userPhotoAd, int symbol,float thumbnail) {
        if (symbol == 0) {//以不缓存的形式加载图片
            Glide.with(context).load(userPhotoAd)
                    .asBitmap().skipMemoryCache(true)
                    .placeholder(R.drawable.a1)
                    .thumbnail(thumbnail)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            imageView.setImageBitmap(resource);
                        }
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            imageView.setImageDrawable(errorDrawable);
                        }
                    });
        }
        if (symbol == 1) {//以缓存的形式加载图片

            Glide.with(context).load(userPhotoAd).asBitmap()
                    .thumbnail(thumbnail)
                    .placeholder(R.drawable.a1)
                    .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    imageView.setImageBitmap(resource);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    imageView.setImageDrawable(errorDrawable);
                }
            });
        }
    }



}
