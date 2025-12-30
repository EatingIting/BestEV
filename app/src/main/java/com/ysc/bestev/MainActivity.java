package com.ysc.bestev;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ysc.bestev.Data.DatabaseOpenHelper;
import com.ysc.bestev.Data.LoginActivity;
import com.ysc.bestev.Data.UserInformation;
import com.ysc.bestev.Data.UserInformationViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//import static com.ysc.bestev.LoginActivity.mContext;

public class MainActivity extends AppCompatActivity {

    private UserInformationViewModel userViewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private long backKeyPressedTime = 0;
    private Toast toast;
    TextView navUserID, navUserNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        navUserID = (TextView)headerView.findViewById(R.id.ID);
        navUserNAME = (TextView)headerView.findViewById(R.id.NAME);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        getData();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)//, R.id.nav_mypage)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.nav_home:
                        setTitle("전기충전소 지도");
                        break;
//                    case R.id.nav_mypage:
//                        setTitle("마이페이지");
//                        break;
                    case R.id.nav_gallery:
                        setTitle("전기차 정보(종류)");
                        break;
                    case R.id.nav_slideshow:
                        setTitle("설정");
                        break;
                    default:
                        setTitle("디폴트");
                        break;
                }
            }
        });
//        getSupportActionBar().setTitle("bestEV");
    }

    private void getData(){
        userViewModel = new ViewModelProvider(this).get(UserInformationViewModel.class);

        String userId = getIntent().getStringExtra("userID");
        String userName = getIntent().getStringExtra("userName");
        UserInformation userInformation = new UserInformation(userId,userName);
        userViewModel.insert(userInformation);
        navUserID.setText(userId);
        navUserNAME.setText(userName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
//                OAuthLogin mOAuthLogin = OAuthLogin.getInstance();
//                boolean isLogoutAndDeleteToken = mOAuthLogin.logoutAndDeleteToken(this);
//                if (!isLogoutAndDeleteToken){
//                    Log.e("isLogoutAndDeleteToken: ", "Client Token Deleted");
//                } 네이버 로그인 토큰 로그아웃!
                Toast.makeText(this, "로그아웃 되고 초기화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                finish();
                SharedPreferences loginInformation = getSharedPreferences("auto",0);
                SharedPreferences.Editor editor = loginInformation.edit();
                editor.remove("loginInformation");
                editor.clear();
                editor.commit();

                startActivity(new Intent(this, LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로가기\'를 한번 더!!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            super.onBackPressed();
            this.finish();
            toast.cancel();
        }
    }
}