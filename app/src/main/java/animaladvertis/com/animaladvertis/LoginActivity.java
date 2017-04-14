package animaladvertis.com.animaladvertis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.input.InputManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingle.widget.LoadingView;

import animaladvertis.com.animaladvertis.beans.User;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.update.BmobUpdateAgent.setDefault;
import static com.baidu.location.h.j.u;

public class LoginActivity extends AppCompatActivity {
    private EditText et_userName;
    private EditText et_pwd;
    private Button bt_login;
    private TextView rigest;
    private LoadingView loadind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this,"65749386b1ac27ecde1a176282d5f49b ");
        et_userName = (EditText) findViewById(R.id.et_userName);
        et_pwd = (EditText) findViewById(R.id.et_userPassword);
        bt_login = (Button) findViewById(R.id.bt_login);
        rigest = (TextView) findViewById(R.id.tv_register);
        loadind = (LoadingView) findViewById(R.id.loading);

        setUser();
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadind.setVisibility(View.VISIBLE);
                bt_login.setClickable(false);

                final String userName= et_userName.getText().toString().trim();
                final String pwd = et_pwd.getText().toString().trim();

                if(userName==null){
                    Toast.makeText(getApplicationContext(),"请输入用户名",Toast.LENGTH_SHORT).show();
                }else if(pwd==null){
                    Toast.makeText(getApplicationContext(),"请输入密码",Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    user.setUsername(userName);
                    user.setPassword(pwd);
                    user.setPwd(pwd);
                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User o, BmobException e) {
                            if(o!=null){
                                Intent intent = new Intent(LoginActivity.this,UserActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                if(e.getErrorCode()==101){
                                    Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"其他错误"+e.getErrorCode(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            bt_login.setClickable(true);
                            loadind.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });

        rigest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setUser() {
        String userName;
        User user =  BmobUser.getCurrentUser(User.class);
        if(user!=null){
            userName = user.getUsername();
            et_userName.setText(userName);
        }

    }


}
