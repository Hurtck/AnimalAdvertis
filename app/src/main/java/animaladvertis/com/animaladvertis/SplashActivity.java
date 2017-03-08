package animaladvertis.com.animaladvertis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import animaladvertis.com.animaladvertis.beans.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static android.R.attr.tag;
import static android.R.string.yes;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private User user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Bmob.initialize(this,"65749386b1ac27ecde1a176282d5f49b ");
        new FetchDataTask().execute();
    }
    class FetchDataTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(final String... params) {
            try {
                Thread.sleep(3000);
                user = User.getCurrentUser(User.class);
                if(user==null){
                    return "nouser";
                }else{
                    //TODO 在这里加载图片资源 查询位置信息

                }
            }catch (InterruptedException e){
                Log.d("DFG",""+e.getMessage());
            }
            return "user";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if("nouser".equals(s)){
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            if("user".equals(s)){
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getActiveNetworkInfo()!=null){
                    user.setPassword(user.getPwd());
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e==null) {
                                Intent intent = new Intent(SplashActivity.this,UserActivity.class);
                                startActivity(intent);
                            }else{
                                Log.d("AUOIJJD",e.getErrorCode()+e.getMessage()+user.getPwd());
                                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }else{
                    Intent intent = new Intent(SplashActivity.this,UserActivity.class);
                    startActivity(intent);
                }

            }
            finish();
        }
    }
}
