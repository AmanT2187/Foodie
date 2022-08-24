package com.amati.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.amati.foodie.fragments.CartFragment;
import com.amati.foodie.fragments.HomeFragment;
import com.amati.foodie.fragments.ProfileFragment;
import com.amati.foodie.fragments.SearchFragment;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {
    SmoothBottomBar bottomBar;

    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    CartFragment cartFragment = new CartFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.bottomBar);
         getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,homeFragment).commit();

         bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
             @Override
             public boolean onItemSelect(int i) {
                 switch (i){
                     case 0:
                         getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,homeFragment).commit();
                         return true;

                     case 1:
                         getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,searchFragment).commit();
                         return true;

                     case 2:
                         getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,cartFragment).commit();
                         return true;

                     case 3:
                         getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,profileFragment).commit();
                         return true;
                 }
                 return false;
             }
         });


    }
}