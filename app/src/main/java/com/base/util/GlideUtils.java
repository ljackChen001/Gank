package com.base.util;

import android.content.Context;
import android.widget.ImageView;

import com.base.helper.BlurTransformation;
import com.base.helper.GlideCircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ui.gank.R;

import java.io.File;

/***********************************************************************
 **                            _ooOoo_  
 **                           o8888888o  
 **                           88" . "88  
 **                           (| -_- |)  
 **                            O\ = /O  
 **                        ____/`---'\____  
 **                      .   ' \\| |// `.  
 **                       / \\||| : |||// \  
 **                     / _||||| -:- |||||- \  
 **                       | | \\\ - /// | |  
 **                     | \_| ''\---/'' | |  
 **                      \ .-\__ `-` ___/-. /  
 **                   ___`. .' /--.--\ `. . __  
 **                ."" '< `.___\_<|>_/___.' >'"".  
 **               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
 **                 \ \ `-. \_ __\ /__ _/ .-` / /  
 **         ======`-.____`-.___\_____/___.-`____.-'======  
 **                            `=---='  
 **         .............................................  
 **                  佛祖镇楼                  BUG辟易  
 **    佛曰:  
 **    写字楼里写字间，写字间里程序员；  程序人员写程序，又拿程序换酒钱。  
 **    酒醒只在网上坐，酒醉还来网下眠；  酒醉酒醒日复日，网上网下年复年。  
 **    酒醉酒醒日复日，网上网下年复年。  酒醉酒醒日复日，网上网下年复年。  
 **    酒醉酒醒日复日，网上网下年复年。  但愿老死电脑间，不愿鞠躬老板前；  
 **    奔驰宝马贵者趣，公交自行程序员。  别人笑我忒疯癫，我笑自己命太贱；  
 **                  不见满街漂亮妹，哪个归得程序员？
 ***********************************************************************
 * Created by ChenBaoLin on 2017/4/29.
 *
 */

public class GlideUtils {

    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).crossFade().placeholder(R.drawable.header).error(R.drawable.header).into(iv);
    }

    public static void loadGifImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable
                .header).error(R.drawable.header).into(iv);
    }


    public static void loadCircleImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).placeholder(R.drawable.header).error(R.drawable.header).transform(new
                GlideCircleTransform(context)).into(iv);
    }

    public static void loadRoundCornerImage(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).error(R.drawable.header).transform(new BlurTransformation(context, 5)).into(iv);
    }


    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .into(imageView);


    }

    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, final int resourceId, ImageView iv) {
        Glide.with(context).load(resourceId)
                .transform(new GlideCircleTransform(context)).into(iv);
    }

    public static void loadRoundCornerImage(Context context, final int resourceId, ImageView iv) {
        Glide.with(context).load(resourceId)
                .transform(new BlurTransformation(context, 5)).into(iv);
    }

}
