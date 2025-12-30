package com.ysc.bestev.Data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ysc.bestev.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pass, et_name, et_age, et_passck;
    private Button btn_register, btn_validate;
    private AlertDialog dialog;
    boolean validate = false;
    int userAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("회원가입 페이지");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_passck = findViewById(R.id.et_passck);
        btn_register = findViewById(R.id.btn_register);
        btn_validate = findViewById(R.id.btn_validate);

        et_id.setFilters(new InputFilter[] { filter });
        et_pass.setFilters(new InputFilter[] { filter });
        et_pass.setPrivateImeOptions("defaultInputmode=english;");
        et_name.setFilters(new InputFilter[] { filterKor });
        et_passck.setFilters(new InputFilter[] { filter });
        et_passck.setPrivateImeOptions("defaultInputmode=english;");

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = et_id.getText().toString();

                if (validate) {
                    return; //검증 완료
                }

                if (userid.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다. 사용하시겠습니까?").setPositiveButton("확인", null).create();
                                dialog.show();
                                et_id.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            Log.e("Error", e.getMessage());
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final String userID = et_id.getText().toString();
                final String userPass = et_pass.getText().toString();
                final String userPassck = et_passck.getText().toString();
                final String userName = et_name.getText().toString();

                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //한 칸이라도 입력 안했을 경우
                if (userID.equals("") || userPass.equals("") || userPassck.equals("") || userName.equals("")) {
                    try {
                        userAge = Integer.parseInt(et_age.getText().toString());
                        if(userAge == 0 || et_age.length() == 0) {
                            return;
                        }
                    } catch (NumberFormatException e) {
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(userPass.equals(userPassck)) {
                                if (success) {
                                    Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", userName), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //회원가입 실패시
                                } else {
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);*/
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
    protected InputFilter filter= new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9 \\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]*$");
            if (source.equals("") || ps.matcher(source).matches()) {
                return source;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            dialog = builder.setMessage("영문만 입력 가능합니다.").setNegativeButton("확인", null).create();
            dialog.show();
            return "";
        }
    };

    public InputFilter filterKor = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[ㄱ-ㅎ|ㅏ-ㅣ|가-힣 \\u318D\\u119E\\u11A2\\u2022\\u2025a\\u00B7\\uFE55]*$");
            if (source.equals("") || ps.matcher(source).matches()) {
                return source;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            dialog = builder.setMessage("한글만 입력 가능합니다.").setNegativeButton("확인", null).create();
            dialog.show();
            return "";
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:{
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}