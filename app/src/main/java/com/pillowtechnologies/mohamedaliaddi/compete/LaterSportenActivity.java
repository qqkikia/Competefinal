package com.pillowtechnologies.mohamedaliaddi.compete;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class LaterSportenActivity extends AppCompatActivity {
    String time;
    String date;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_later_sporten);
        TextView tx = (TextView)findViewById(R.id.txt);
        Typeface tf = Typeface.createFromAsset(getAssets(),"coolvetica.ttf");
        tx.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_later_sporten, menu);
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

    public void Done(View view){
        Intent intent = new Intent(this,GeneralActivity.class);
        startActivity(intent);
    }

    public void Plan(View view){
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");


        intent.putExtra(CalendarContract.Events.TITLE, "Neel Birthday");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a sample description");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
        startActivity(intent);
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
        date = newFragment.toString();

    }


    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
        time = newFragment.toString();


    }
}