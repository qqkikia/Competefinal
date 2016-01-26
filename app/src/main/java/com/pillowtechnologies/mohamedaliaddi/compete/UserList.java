package com.pillowtechnologies.mohamedaliaddi.compete;

/**
 * Created by Nynke on 13-Jan-16.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pillowtechnologies.mohamedaliaddi.compete.custom.CustomActivity;
import com.pillowtechnologies.mohamedaliaddi.compete.utils.Const;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class UserList is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class UserList extends CustomActivity
{

    int n;
    /** The Chat list. */
    private ArrayList<ParseUser> uList;

    /** The user. */
    public static ParseUser user;

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);

        updateUserStatus(true);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        updateUserStatus(false);
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        ArrayList<String> UserNames = new ArrayList<String>();


            for (int i = 0; i < getIntent().getExtras().size(); i++) {
                String tempuser = (String) getIntent().getExtras().get("User"+String.valueOf(i));
                if(!(tempuser.equals(ParseUser.getCurrentUser().getUsername().toString()))){
                    UserNames.add(tempuser);
                }

        }
        getFriends(UserNames);

    }

    /**
     * Update user status.
     *
     * @param online
     *            true if user is online
     */
    private void updateUserStatus(boolean online)
    {
        user.put("online", online);
        user.saveEventually();
    }

    /**
     * Load list of users.
     */

    int x = 5;




    public void getFriends(List<String> UserNames) {

        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_loading));
        List<ParseQuery<ParseUser>> queries = new ArrayList<ParseQuery<ParseUser>>();
        for (String UserName : UserNames) {
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            parseQuery.whereEqualTo("username", UserName);
            queries.add(parseQuery);
        }

        ParseQuery<ParseUser> userQuery = ParseQuery.or(queries);

        userQuery.findInBackground(new FindCallback<ParseUser>() {

            @Override
            public void done(List<ParseUser> userList, ParseException e) {
                dia.dismiss();
                if (e == null) {
                    for (int i = 0; i < userList.size(); i++) {
                        if (userList != null) {

                            if (userList.size() == 0)
                                Toast.makeText(UserList.this,
                                        R.string.msg_no_user_found,
                                        Toast.LENGTH_SHORT).show();

                            uList = new ArrayList<ParseUser>(userList);
                            ListView list = (ListView) findViewById(R.id.list);
                            list.setAdapter(new UserAdapter());
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> arg0,
                                                        View arg1, int pos, long arg3) {
                                    startActivity(new Intent(UserList.this, Chat.class).putExtra(
                                            Const.EXTRA_DATA, uList.get(pos)
                                                    .getUsername()));
                                }
                            });
                        }

                    }
                }
            }
        });
    }

    /**
     * The Class UserAdapter is the adapter class for User ListView. This
     * adapter shows the user name and it's only online status for each item.
     */
    private class UserAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount()
        {
            return uList.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public ParseUser getItem(int arg0)
        {
            return uList.get(arg0);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int pos, View v, ViewGroup arg2)
        {
            if (v == null)
                v = getLayoutInflater().inflate(R.layout.chat_item, null);

            ParseUser c = getItem(pos);
            TextView lbl = (TextView) v;
            lbl.setText(c.getUsername());
            lbl.setCompoundDrawablesWithIntrinsicBounds(
                    c.getBoolean("online") ? R.drawable.ic_online
                            : R.drawable.ic_offline, 0, R.drawable.arrow, 0);

            return v;
        }

    }
}

