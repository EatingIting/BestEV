package com.ysc.bestev.Data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ysc.bestev.MainActivity;
import com.ysc.bestev.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button login, register;
    private CheckBox id_save;
    private long backKeyPressedTime = 0;
    private FragmentTabHost mTabHost;

    boolean check_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("로그인 페이지");

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        id_save = findViewById(R.id.id_save);

        SharedPreferences autologin = getSharedPreferences("log", 0);
        String userId = autologin.getString("id", "");
        if (autologin.getBoolean("is",false)) {
            et_id.setText(userId);
            id_save.setChecked(true);
        } else {
            et_id.setText(userId);
            id_save.setChecked(false);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");

                                Toast.makeText( getApplicationContext(), String.format("%s님 환영합니다.", userName), Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                                intent.putExtra("userID", userID);
                                intent.putExtra("userPass", userPass);
                                intent.putExtra("userName", userName);
                                if (id_save.isChecked()) {
                                    SharedPreferences login = getSharedPreferences("log", 0);
                                    SharedPreferences.Editor editor = login.edit();
                                    check_box = true;
                                    editor.putString("id", userID);
                                    editor.putBoolean("is", check_box);
                                    editor.commit();
                                    editor.apply();
                                } else {
                                    SharedPreferences login = getSharedPreferences("log", 0);
                                    SharedPreferences.Editor editor = login.edit();
                                    editor.clear();
                                    editor.apply();
                                    editor.commit();
                                }
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);*/
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 버튼 클릭
                Toast.makeText(LoginActivity.this, "회원가입 화면으로 이동", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로가기\'를 한번 더!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            super.onBackPressed();
            this.finish();
        }
    }
}