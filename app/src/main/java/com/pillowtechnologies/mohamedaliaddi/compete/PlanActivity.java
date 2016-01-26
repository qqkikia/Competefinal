package com.pillowtechnologies.mohamedaliaddi.compete;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class PlanActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Location location = new Location ("curloc");
    int day;
    int month;
    int year;
    int minute;
    int hour;
    String title;
    LocationManager locationManager;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        pref = getApplicationContext().getSharedPreferences("TheData", MODE_PRIVATE);
        editor = pref.edit();
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
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
        /*locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000L,500.0f, locationListener);
        Location location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);*/
        location.setLatitude(52.10013);
        location.setLongitude(5.16141);

        latitude = location.getLatitude();
        longitude = location.getLongitude();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan, menu);
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

    public void setLocation(View view){
        int PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
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






    public void Done(View view){
        DatePicker datePicker = (DatePicker) findViewById(R.id.datepicker);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timepicker);
        EditText titletext = (EditText)findViewById(R.id.titleedittext);
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() + 1;
        year = datePicker.getYear();
        hour = timePicker.getCurrentHour();
        minute = timePicker.getCurrentMinute();
        title = titletext.getText().toString();
        if(!(title.equals(""))){
            String user1 = ParseUser.getCurrentUser().getUsername().toString();
            ParseACL acl = new ParseACL();
            acl.setPublicReadAccess(true);
            acl.setPublicWriteAccess(true);

            ParseObject text = new ParseObject("Events");
            text.put("Day", String.valueOf(day));
            text.put("month",String.valueOf(month));
            text.put("Year",String.valueOf(year));
            text.put("Hour",String.valueOf(hour));
            text.put("Minute",String.valueOf(minute));
            text.put("Title",title);
            text.put("User1", user1);
            text.put("User2","empty");
            text.put("Latitude",String.valueOf(latitude));
            text.put("Longitude",String.valueOf(longitude));
            text.put("EventStatus", "Open");
            text.setACL(acl);
            text.saveInBackground();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), GeneralActivity.class);
                    startActivity(intent);

                }
            }, 1000);
        }

else{Toast.makeText(this,"Please give us a title", Toast.LENGTH_SHORT).show();}



    }



    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


}
