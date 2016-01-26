package com.pillowtechnologies.mohamedaliaddi.compete;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class EventActivity extends AppCompatActivity {
String objectID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        objectID = getIntent().getExtras().getString("Objectid");
        String day = getIntent().getExtras().getString("Day");
        String hour = getIntent().getExtras().getString("Hour");
        String minute = getIntent().getExtras().getString("Minute");
        String month = getIntent().getExtras().getString("Month");
        String year = getIntent().getExtras().getString("Year");
        String user1string = getIntent().getExtras().getString("User1");
        String user2string = getIntent().getExtras().getString("User2");
        String title = getIntent().getExtras().getString("Title");
        TextView tv = (TextView)findViewById(R.id.titletextevent);
        TextView tv1 = (TextView)findViewById(R.id.user1text);
        TextView tv2 = (TextView)findViewById(R.id.user2text);
        TextView tv3 = (TextView)findViewById(R.id.datetext);
        tv.setText(title);
        tv1.setText(user1string);
        tv2.setText(user2string);
        tv3.setText(day + "-" + month + "-" + year + "," + hour + ":" + minute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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

    public void CloseEvent(View view){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.getInBackground(objectID, new GetCallback<ParseObject>() {
            public void done(ParseObject status, ParseException e) {
                if (e == null) {
                    status.put("EventStatus", "Closed");
                    status.saveInBackground();
                }
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(getApplicationContext(), GeneralActivity.class);
                startActivity(intent);

            }
        }, 1000);


    }

    public void ToGeneral(View view){
        Intent intent = new Intent(this, GeneralActivity.class);
        startActivity(intent);
    }
}
