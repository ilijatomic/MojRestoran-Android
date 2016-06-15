package com.ilija.mojrestoran.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ilija.mojrestoran.AppObject;
import com.ilija.mojrestoran.R;
import com.ilija.mojrestoran.util.Constants;

public class BaseActivity extends AppCompatActivity {

    private boolean showHomeButton = false;

    protected void addToolbar(boolean showHomeButton) {
        this.showHomeButton = showHomeButton;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHomeButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.menu_home).setVisible(showHomeButton);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        switch (item.getItemId()) {
            case R.id.menu_profile:
                return true;
            case R.id.profile_log_out:
                sharedPreferences.edit().putString(Constants.PREF_USER_LOGIN, null).commit();
                intent = new Intent(AppObject.getAppInstance().getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;

            case R.id.menu_home:
                String userLogin = AppObject.getAppInstance().getUlogovanKorisnik().getTip();
                switch (userLogin) {
                    case Constants.USER_LOGIN_ADMIN:
                        intent = new Intent(AppObject.getAppInstance().getApplicationContext(), AdminHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        break;
                    case Constants.USER_LOGIN_KONOBAR:
                        intent = new Intent(AppObject.getAppInstance().getApplicationContext(), KonobarHomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        break;
                }
                if (intent != null)
                    startActivity(intent);
                return true;

            case R.id.profile_details:
                intent = new Intent(AppObject.getAppInstance().getApplicationContext(), ProfilDetaljiActivity.class);
                startActivity(intent);
                return true;

            case R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
