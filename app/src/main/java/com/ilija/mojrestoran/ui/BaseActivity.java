package com.ilija.mojrestoran.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.SplashActivity;
import com.ilija.mojrestoran.util.Constants;

public class BaseActivity extends AppCompatActivity {

    protected void addToolbar(boolean showHomeButton) {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        findViewById(R.id.home).setVisibility(showHomeButton ? View.VISIBLE : View.GONE);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(!showHomeButton);

        ImageView home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = null;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String userLogin = sharedPreferences.getString(Constants.PREF_USER_LOGIN, "");
                switch (userLogin) {
                    case Constants.USER_LOGIN_ADMIN:
                        intent = new Intent(AppObject.getAppInstance().getApplicationContext(), AdminHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        break;
                    case Constants.USER_LOGIN_WAITER:
                        intent = new Intent(AppObject.getAppInstance().getApplicationContext(), KonobarHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        break;
                }

                if (intent != null)
                    startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                return true;
            case R.id.profile_log_out:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                sharedPreferences.edit().putString(Constants.PREF_USER_LOGIN, null).commit();
                Intent intent = new Intent(AppObject.getAppInstance().getApplicationContext(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
