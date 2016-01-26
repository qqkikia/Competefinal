package com.pillowtechnologies.mohamedaliaddi.compete;

/**
 * Created by mohamedaliaddi on 10/01/16.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    ParseObject tempValues=null;
    int i=0;


    public CustomAdapter(Activity a, ArrayList d,Resources resLocal) {

        activity = a;
        data=d;
        res = resLocal;


        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder{

        public TextView text;



    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){


            vi = inflater.inflate(R.layout.list_item, null);



            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.eventtitle);


            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.text.setText("You don't have any matches at the moment, try later.");

        }
        else
        {

            tempValues=null;
            tempValues = ( ParseObject ) data.get( position );


            holder.text.setText(tempValues.get("Title").toString());





            vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {

    }


    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            GeneralActivity sct = (GeneralActivity)activity;
            Intent i = new Intent(sct,EventActivity.class);
            ParseObject temp = sct.currentmatches.get(mPosition);
            String objectidstring = (String)temp.getObjectId().toString();
            String user1string = (String)temp.get("User1");
            String user2string = (String)temp.get("User2");
            String hour = (String)temp.get("Hour");
            String minute = (String)temp.get("Minute");
            String day = (String)temp.get("Day");
            String month = (String)temp.get("Month");
            String year = (String)temp.get("Year");
            String titlestring = (String)temp.get("Title");
            i.putExtra("Title", titlestring);
            i.putExtra("User1",user1string);
            i.putExtra("User2", user2string);
            i.putExtra("Hour",hour);
            i.putExtra("Minute", minute);
            i.putExtra("Day", day);
            i.putExtra("Month",month);
            i.putExtra("Year",year);
            i.putExtra("Objectid", objectidstring);
            sct.startActivity(i);


        }
    }

}
