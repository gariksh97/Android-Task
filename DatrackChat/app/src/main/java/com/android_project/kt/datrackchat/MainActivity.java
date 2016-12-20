package com.android_project.kt.datrackchat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android_project.kt.datrackchat.addfriend.AddFriendActivity;
import com.android_project.kt.datrackchat.chat.dialogs.DialogListFragment;
import com.android_project.kt.datrackchat.chat.messages.DialogFragment;
import com.android_project.kt.datrackchat.dictionary.DictionaryFragment;
import com.android_project.kt.datrackchat.game.GameFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    public Map<String, Fragment> fragmentMap;
    private Fragment selectedFragment;
    public boolean isDialog = false;

    protected MainActivity(Parcel in) {
    }

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Chat"));
        tabLayout.addTab(tabLayout.newTab().setText("Dictionary"));
        tabLayout.addTab(tabLayout.newTab().setText("Game"));
        tabLayout.addOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        changeByPos(tab.getPosition());
                    }


                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        changeByPos(tab.getPosition());
                    }

                    private void changeByPos(int position) {
                        switch (position) {
                            case 0:
                                if (isDialog)
                                    changeFragment("Dialog");
                                else
                                    changeFragment("DialogList");
                                break;
                            case 1:
                                changeFragment("Dictionary");
                                break;
                            case 2:
                                changeFragment("Game");
                                break;
                        }
                    }
                }
        );

        if (savedInstanceState == null) {
            fragmentMap = new HashMap<>();
            fragmentMap.put("DialogList", DialogListFragment.newInstance());
            fragmentMap.put("Dictionary", DictionaryFragment.newInstance());
            fragmentMap.put("Game", GameFragment.newInstance());
            fragmentMap.put("Dialog", DialogFragment.newInstance());
            tabLayout.getTabAt(0).select();
        } else {
            ArgumentsBundle argumentsBundle = (ArgumentsBundle) savedInstanceState.getSerializable("arguments");
            fragmentMap = (Map<String, Fragment>) argumentsBundle.get("fragmentMap");
            selectedFragment = (Fragment) argumentsBundle.get("selectedFragment");
            isDialog = (boolean) argumentsBundle.get("isDialog");
            tabLayout.getTabAt(
                    (Integer) argumentsBundle.get("position")
            ).select();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArgumentsBundle argumentsBundle = new ArgumentsBundle();
        argumentsBundle.put("selectedFragment", selectedFragment);
        argumentsBundle.put("fragmentMap", fragmentMap);
        argumentsBundle.put("position", tabLayout.getSelectedTabPosition());
        argumentsBundle.put("isDialog", isDialog);
        outState.putSerializable("arguments", argumentsBundle);
    }

    public void changeFragment(String fragmentString) {
        Fragment fragment = fragmentMap.get(fragmentString);
        if (selectedFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(selectedFragment);
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }
        selectedFragment = fragment;
    }


    @Override
    public void onBackPressed() {
        if (isDialog && tabLayout.getSelectedTabPosition() == 0) {
            isDialog = false;
            changeFragment("DialogList");
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            menu.findItem(R.id.action_log_out).setTitle("Log in");
            menu.findItem(R.id.action_add_friend).setVisible(false);
        } else {
            menu.findItem(R.id.action_add_friend).setVisible(true);
            menu.findItem(R.id.action_log_out).setTitle("Log out");
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
            //TODO: Добавить настройки
        } else if (id == R.id.action_log_out) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                FirebaseAuth.getInstance().signOut();

                AuthorizationActivity.googleApiClient.clearDefaultAccountAndReconnect();
                Intent loginActivity = new Intent(this, AuthorizationActivity.class);
                startActivity(loginActivity);

            }
            startActivity(new Intent(this, AuthorizationActivity.class));
            finish();
            return true;
        } else if (id == R.id.action_add_friend) {
            startActivity(new Intent(MainActivity.this, AddFriendActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
