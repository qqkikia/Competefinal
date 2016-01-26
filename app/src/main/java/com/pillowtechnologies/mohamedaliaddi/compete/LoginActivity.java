package com.pillowtechnologies.mohamedaliaddi.compete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.CallbackManager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {

    int n;
    CallbackManager callbackManager;
    ArrayList<String> perms = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        perms.add("public_profile");

        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        CirclePageIndicator cp = (CirclePageIndicator)findViewById(R.id.indicator);
        cp.setViewPager(pager);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return FragmentOne.newInstance("FragmentOne, Instance 1");
                case 1: return FragmentTwo.newInstance("FragmentTwo, Instance 2");
                case 2: return FragmentThree.newInstance("FragmentThree, Instance 3");
                case 3: return FragmentFour.newInstance("FragmentFour, Instance 4");

                default: return FragmentOne.newInstance("FragmentOne, default");
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    public void toSignUp(View view){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }


    public void Login(View view){

        Intent intent = new Intent (this,Login.class);
        startActivity(intent);    }

    public void Register(View view){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }




}


