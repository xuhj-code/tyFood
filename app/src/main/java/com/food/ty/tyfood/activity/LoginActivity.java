package com.food.ty.tyfood.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.food.ty.tyfood.FoodClient;
import com.food.ty.tyfood.R;
import com.food.ty.tyfood.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;


/**
 * Created by ty on 2017/2/25.
 */
//@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    FoodClient foodClient = FoodClient.getInstance();
    private EditText ename;
    private EditText epassword;
    private static LoginActivity singleton;
    protected static User user;
    String name;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ename = (EditText)findViewById(R.id.et_name);
        epassword = (EditText)findViewById(R.id.et_password);

        singleton = this;
        user = User.getInstance(this);

        x.view().inject(this);
        if (!(user.getUserName().equals("")))
        {
            ename.setText(user.getUserName());
            epassword.setText(user.getUserPassword());
        }

    }
    public void login(View v)
    {
        name = ename.getText().toString().trim();
        password = epassword.getText().toString().trim();

        if (name.equals("")||password.equals(""))
        {
            Toast.makeText(LoginActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
        }
        else
        {
            RequestParams params = new RequestParams(foodClient.API+"/LoginServlet");
            params.addQueryStringParameter("name",name);
            params.addQueryStringParameter("password",password);
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (result.equals("true"))
                    {
                        Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
                        user.setUserName(name);
                        user.setUserPassword(password);
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }
                    else
                    {
                        ename.setText("");
                        epassword.setText("");
                        user.setUserName("");
                        user.setUserPassword("");
                        Toast.makeText(LoginActivity.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();

                    }

                }
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(LoginActivity.this,"连接网络失败", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(CancelledException cex) {
                }
                @Override
                public void onFinished() {
                }
            });


        }

    }

}

