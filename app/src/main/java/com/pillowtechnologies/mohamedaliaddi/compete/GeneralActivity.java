package com.pillowtechnologies.mohamedaliaddi.compete;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GeneralActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    DrawerLayout drawerLayout;
    int x = 500;
    ListView list;
    CustomAdapter adapter;
    GeneralActivity CustomListView = null;

    GoogleApiClient mGoogleApiClient;
    Location location = new Location("curloc");
    List<ParseObject> objectListing;
    List<ParseObject> objectListing1;
    List<ParseObject> objectListing2;
    ArrayList<ParseObject> potentialmatches;
    String matchee ="";
    ArrayList<ParseObject> currentmatches;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_general);
        list = (ListView) findViewById(R.id.list);
        CustomListView = this;
        setListData();
        Resources res = getResources();


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        currentmatches = new ArrayList<ParseObject>();
        potentialmatches = new ArrayList<ParseObject>();

        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        /*locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000L, 500.0f, locationListener);
        location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);*/
        location.setLatitude(52.10013);
        location.setLongitude(5.16141);

            getEvents();
        getCurrentMatches();
        adapter = new CustomAdapter(CustomListView, currentmatches, res);

        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_general, menu);
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

    public Bitmap getPhotoFacebook(final String id) {

        Bitmap bitmap = null;
        final String nomimg = "https://graph.facebook.com/" + id + "/picture?type=large";
        URL imageURL = null;

        try {
            imageURL = new URL(nomimg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {

            e.printStackTrace();
        }
        return bitmap;

    }

    @Override
    public void onConnected(Bundle bundle) {




    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);

        }
    }

    private void selectItem(int pos) {
        if (pos == 0) {
            Intent intent = new Intent(getApplicationContext(), NuSportenActivity.class);
            startActivity(intent);
        }
        if (pos == 1) {
            Intent intent = new Intent(getApplicationContext(), LaterSportenActivity.class);
            startActivity(intent);
        }
        if (pos == 2) {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }
    }


    public void toProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void toPlan(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
        if(x != 500){
        potentialmatches = parseLocation();
        }

    }

    public void toLadder(View view) {
        Intent intent = new Intent(this, LadderActivity.class);
        startActivity(intent);
    }

    public void toCurrent(View view) {
        /*Intent intent = new Intent(this, CurrentActivity.class);
        startActivity(intent);*/

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.getInBackground(potentialmatches.get(0).getObjectId().toString(), new GetCallback<ParseObject>() {
            public void done(ParseObject user2, ParseException e) {
                if (e == null) {
                    matchee = (String) user2.get("User1");
                    user2.put("User2", ParseUser.getCurrentUser().getUsername().toString());
                    user2.saveInBackground();
                }
            }
        });
        Toast.makeText(this,"You have been matched with " + matchee,Toast.LENGTH_SHORT).show();




    }



    public void toUserlist(View view){
        Intent intent = new Intent(this,UserList.class);
        for(int i = 0; i < currentmatches.size(); i++){
            String tempuser1 = (String) currentmatches.get(i).get("User1");
            String tempuser2 = (String) currentmatches.get(i).get("User2");

                intent.putExtra("User" + String.valueOf(i), tempuser1);

                intent.putExtra("User" + String.valueOf(i + 1), tempuser2);

        }
if(currentmatches.size() != 0) {
    startActivity(intent);
} else{Toast.makeText(this,"No matches", Toast.LENGTH_SHORT).show();}
        

    }

    public void setListData()
    {




    }



    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void getAvailableMatches(){

    }

    public ArrayList<ParseObject> parseLocation(){
ArrayList<ParseObject> list = new ArrayList<ParseObject>();
        if(objectListing.size() != 0) {
        for(int i = 0; i < objectListing.size(); i++) {
            Location mlocation = new Location("");

            String latstring = (String) objectListing.get(i).get("Latitude");
            String lonstring = (String) objectListing.get(i).get("Longitude");
            double lat = Double.parseDouble(latstring);
            double lon = Double.parseDouble(lonstring);
            mlocation.setLatitude(lat);
            mlocation.setLongitude(lon);
            float distance = mlocation.distanceTo(location);
            int roundeddistance = Math.round(distance);
            if (roundeddistance <= 5000) {

                list.add(objectListing.get(i));
            }
        }
        }
        return list;
    }


    public void SportNow(View view){
        if(x != 500){
            potentialmatches = parseLocation();
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        if(potentialmatches.size() != 0) {
            query.getInBackground(potentialmatches.get(0).getObjectId().toString(), new GetCallback<ParseObject>() {
                public void done(ParseObject user2, ParseException e) {
                    if (e == null) {
                        matchee = (String) user2.get("User1");
                        user2.put("User2", ParseUser.getCurrentUser().getUsername().toString());
                        user2.saveInBackground();
                        right();
                        getCurrentMatches();
                    }

                }
            });
        }else{Wrong();}
    }

    public void right(){
        Toast.makeText(this,"You have been matched with " + matchee, Toast.LENGTH_SHORT).show();
    }
    public void Wrong(){
        Toast.makeText(this,"Something went wrong, please try later", Toast.LENGTH_SHORT).show();
    }
    public void getEvents(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.whereNotEqualTo("User1", ParseUser.getCurrentUser().getUsername().toString());
        query.whereEqualTo("User2", "empty");
        query.whereNotEqualTo("EventStatus", "Closed");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    x = objectList.size();
                    objectListing = objectList;

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }

    public void getCurrentMatches(){
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Events");
        query1.whereEqualTo("User1", ParseUser.getCurrentUser().getUsername().toString());
        query1.whereNotEqualTo("User2", "empty");
        query1.whereNotEqualTo("EventStatus", "Closed");
        query1.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {

                    objectListing1 = objectList;

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Events");
        query2.whereEqualTo("User2", ParseUser.getCurrentUser().getUsername().toString());
        query2.whereNotEqualTo("User1", "empty");
        query2.whereNotEqualTo("EventStatus", "Closed");
        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {

                    objectListing2 = objectList;

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                createCurrentMatchesList();

            }
        }, 2000);
    }

    public void createCurrentMatchesList(){
        currentmatches.clear();
        if(objectListing1.size() != 0) {
            for (int i = 0; i < objectListing1.size(); i++) {
        if(!(currentmatches.size() == 0)) {
                for (int x = 0; x < currentmatches.size(); x++) {

        if (!(currentmatches.get(x).getObjectId().equals(objectListing1.get(i).getObjectId()))) {
            if(!(currentmatches.contains(objectListing1.get(i)))) {
                currentmatches.add(objectListing1.get(i));
            }
        }
    }
}else{currentmatches.add(objectListing1.get(i));}

            }
        }

        if(objectListing2.size() != 0) {
            for (int i = 0; i < objectListing2.size(); i++) {
                if(!(currentmatches.size() == 0)) {
                    for (int x = 0; x < currentmatches.size(); x++) {

                        if (!(currentmatches.get(x).getObjectId().equals(objectListing2.get(i).getObjectId()))) {
                            if(!(currentmatches.contains(objectListing2.get(i)))) {
                                currentmatches.add(objectListing2.get(i));
                            }
                        }
                    }
                }else{currentmatches.add(objectListing2.get(i));}

            }
        }


        adapter.notifyDataSetChanged();
    }
}
