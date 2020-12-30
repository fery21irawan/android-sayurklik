package com.sayurklik.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.sayurklik.app.helpers.ProgressBarHelper;
import com.sayurklik.app.helpers.SayurKlikHelper;
import com.sayurklik.app.models.CartModel;
import com.sayurklik.app.models.NavigationModel;
import com.sayurklik.app.models.OrderModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {
    FragmentManager mManager = getSupportFragmentManager();
    BottomNavigationView navView;
    static DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private ProgressBarHelper mPbh;

    MaterialSearchBar searchBar;

    public static BadgeDrawable badgeCart, badgeTagihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        navView.getOrCreateBadge(R.id.navigation_cart);
        //navView.getOrCreateBadge(R.id.navigation_store);
        badgeCart = navView.getBadge(R.id.navigation_cart);
        /*final BadgeDrawable badgeStore = navView.getBadge(R.id.navigation_store);
        badgeStore.setNumber(1100);
        badgeStore.setMaxCharacterCount(3);*/
        //badgeStore.setBadgeGravity(BadgeDrawable.TOP_END);
        navView.getOrCreateBadge(R.id.navigation_profil);
        badgeTagihan = navView.getBadge(R.id.navigation_profil);
        new CartModel(this).setCartBadge(badgeCart);
        new OrderModel(this).setTagihanBadge(badgeTagihan);
        //badgeHome.setNumber(cartBadge);

        mManager.beginTransaction().replace(
                R.id.view,
                new HomeFragment()).commit();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mRecyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mPbh = findViewById(R.id.pbh);

        loadMenuNavigation();
        chatToWhatsapp();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mManager.beginTransaction().replace(
                            R.id.view,
                            new HomeFragment()).commit();
                    return true;
                case R.id.navigation_store:
                    navView.removeBadge(R.id.navigation_store);
                    mManager.beginTransaction().replace(
                            R.id.view,
                            new ProductFragment()).commit();
                    return true;
                case R.id.navigation_cart:
                    startActivity(new Intent(MainActivity.this, CartActivity.class));
                    return true;
                case R.id.navigation_profil:
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                case R.id.navigation_more:
                    startActivity(new Intent(MainActivity.this, MoreActivity.class));
                    return true;
            }
            return false;
        }
    };
    private void loadMenuNavigation() {
        new NavigationModel.Get(this)
                .setProgressBar(mPbh)
                .showInto(mRecyclerView);
    }
    //for close drawer from adapter
    public static void closeDrawer(){
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    private void chatToWhatsapp(){
        ExtendedFloatingActionButton fab_wa = findViewById(R.id.fab_whatsapp);
        fab_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SayurKlikHelper(MainActivity.this).callToWhatsApp("+6282243200046");
            }
        });

    }
    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Bundle bundle = new Bundle();
        bundle.putString("keyword", text.toString());
        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);
        mManager.beginTransaction().replace(
                R.id.view,
                productFragment).commit();
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                mDrawerLayout.openDrawer(GravityCompat.START);
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "in_ID");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Ucapkan kata kunci !");
                try {
                    startActivityForResult(intent, 321);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case MaterialSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 321) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Bundle bundle = new Bundle();
                bundle.putString("keyword", result.get(0).toString());
                ProductFragment productFragment = new ProductFragment();
                productFragment.setArguments(bundle);
                mManager.beginTransaction().replace(
                        R.id.view,
                        productFragment).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
