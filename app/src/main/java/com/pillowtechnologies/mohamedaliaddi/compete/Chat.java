        package com.pillowtechnologies.mohamedaliaddi.compete;

/**
 * Created by Nynke on 13-Jan-16.
 */
        import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.pillowtechnologies.mohamedaliaddi.compete.custom.CustomActivity;
import com.pillowtechnologies.mohamedaliaddi.compete.model.Conversation;
import com.pillowtechnologies.mohamedaliaddi.compete.utils.Const;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

        /**
         * The Class Chat is the Activity class that holds main chat screen. It shows
         * all the conversation messages between two users and also allows the user to
         * send and receive messages.
         */
        public class Chat extends CustomActivity {

            /**
             * The Conversation list.
             */
            private ArrayList<Conversation> convList;

            /**
             * The chat adapter.
             */
            private ChatAdapter adp;

            /**
             * The Editext to compose the message.
             */
            private EditText txt;

            /**
             * The user name of buddy.
             */
            private String buddy;

            /**
             * The date of last message in conversation.
             */
            private Date lastMsgDate;

            /**
             * Flag to hold if the activity is running or not.
             */
            private boolean isRunning;

            /**
             * The handler.
             */
            private static Handler handler;
            /**
             * ATTENTION: This was auto-generated to implement the App Indexing API.
             * See https://g.co/AppIndexing/AndroidStudio for more information.
             */
            private GoogleApiClient client;

            /* (non-Javadoc)
             * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
             */
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.chat);

                convList = new ArrayList<Conversation>();
                ListView list = (ListView) findViewById(R.id.list);
                adp = new ChatAdapter();
                list.setAdapter(adp);
                list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                list.setStackFromBottom(true);

                txt = (EditText) findViewById(R.id.txt);
                txt.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

                setTouchNClick(R.id.btnSend);

                buddy = getIntent().getStringExtra(Const.EXTRA_DATA);
               //getActionBar().setTitle(buddy);

                handler = new Handler();
                // ATTENTION: This was auto-generated to implement the App Indexing API.
                // See https://g.co/AppIndexing/AndroidStudio for more information.
                client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
            }

            /* (non-Javadoc)
             * @see android.support.v4.app.FragmentActivity#onResume()
             */
            @Override
            protected void onResume() {
                super.onResume();
                isRunning = true;
                loadConversationList();
            }

            /* (non-Javadoc)
             * @see android.support.v4.app.FragmentActivity#onPause()
             */
            @Override
            protected void onPause() {
                super.onPause();
                isRunning = false;
            }

            /* (non-Javadoc)
             * @see com.socialshare.custom.CustomFragment#onClick(android.view.View)
             */
            @Override
            public void onClick(View v) {
                super.onClick(v);
                if (v.getId() == R.id.btnSend) {
                    sendMessage();
                }

            }

            /**
             * Call this method to Send message to opponent. It does nothing if the text
             * is empty otherwise it creates a Parse object for Chat message and send it
             * to Parse server.
             */
            private void sendMessage() {
                if (txt.length() == 0)
                    return;

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

                String s = txt.getText().toString();
                final Conversation c = new Conversation(s, new Date(), UserList.user.getUsername());
                c.setStatus(Conversation.STATUS_SENDING);
                convList.add(c);
                adp.notifyDataSetChanged();
                txt.setText(null);

                ParseObject po = new ParseObject("Chat");
                ParseACL acl = new ParseACL();
                acl.setPublicReadAccess(true);
                po.setACL(acl);
                po.put("sender", UserList.user.getUsername());
                po.put("receiver", buddy);
                // po.put("createdAt", "");
                po.put("message", s);
                po.saveEventually(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null)
                            c.setStatus(Conversation.STATUS_SENT);
                        else
                            c.setStatus(Conversation.STATUS_FAILED);
                        adp.notifyDataSetChanged();
                    }
                });
                loadConversationList();
            }

            /**
             * Load the conversation list from Parse server and save the date of last
             * message that will be used to load only recent new messages
             */
            private void loadConversationList() {
                ParseQuery<ParseObject> q = ParseQuery.getQuery("Chat");
                if (convList.size() == 0) {
                    // load all messages...
                    ArrayList<String> al = new ArrayList<String>();
                    al.add(buddy);
                    al.add(UserList.user.getUsername());

                    q.whereContainedIn("sender", al);
                    q.whereContainedIn("receiver", al);
                    if (lastMsgDate != null)
                        q.whereGreaterThan("createdAt", lastMsgDate);

                }else {
                    q.whereEqualTo("sender", buddy);
                    q.whereEqualTo("receiver", UserList.user.getUsername());
                }
                    // load only newly received message..


                q.orderByDescending("createdAt");
                q.setLimit(100);

                q.findInBackground(new FindCallback<ParseObject>() {

                    @Override
                    public void done(List<ParseObject> li, ParseException e) {
                        if (li != null && li.size() > 0) {
                            for (int i = li.size() - 1; i >= 0; i--) {
                                ParseObject po = li.get(i);
                                Conversation c = new Conversation(po
                                        .getString("message"), po.getCreatedAt(), po.getString("sender"));
                                convList.add(c);
                                if (lastMsgDate == null
                                       || lastMsgDate.before(c.getDate()))
                                    lastMsgDate = c.getDate();
                                adp.notifyDataSetChanged();
                            }
                        }
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                if (isRunning)
                                    loadConversationList();
                            }
                        }, 3000);
                    }
                });

            }

            @Override
            public void onStart() {
                super.onStart();

                // ATTENTION: This was auto-generated to implement the App Indexing API.
                // See https://g.co/AppIndexing/AndroidStudio for more information.
                client.connect();
                Action viewAction = Action.newAction(
                        Action.TYPE_VIEW, // TODO: choose an action type.
                        "Chat Page", // TODO: Define a title for the content shown.
                        // TODO: If you have web page content that matches this app activity's content,
                        // make sure this auto-generated web page URL is correct.
                        // Otherwise, set the URL to null.
                        Uri.parse("http://host/path"),
                        // TODO: Make sure this auto-generated app deep link URI is correct.
                        Uri.parse("android-app://com.pillowtechnologies.mohamedaliaddi.compete/http/host/path")
                );
                AppIndex.AppIndexApi.start(client, viewAction);
            }

            @Override
            public void onStop() {
                super.onStop();

                // ATTENTION: This was auto-generated to implement the App Indexing API.
                // See https://g.co/AppIndexing/AndroidStudio for more information.
                Action viewAction = Action.newAction(
                        Action.TYPE_VIEW, // TODO: choose an action type.
                        "Chat Page", // TODO: Define a title for the content shown.
                        // TODO: If you have web page content that matches this app activity's content,
                        // make sure this auto-generated web page URL is correct.
                        // Otherwise, set the URL to null.
                        Uri.parse("http://host/path"),
                        // TODO: Make sure this auto-generated app deep link URI is correct.
                        Uri.parse("android-app://com.pillowtechnologies.mohamedaliaddi.compete/http/host/path")
                );
                AppIndex.AppIndexApi.end(client, viewAction);
                client.disconnect();
            }

            /**
             * The Class ChatAdapter is the adapter class for Chat ListView. This
             * adapter shows the Sent or Receieved Chat message in each list item.
             */
            private class ChatAdapter extends BaseAdapter {

                /* (non-Javadoc)
                 * @see android.widget.Adapter#getCount()
                 */
                @Override
                public int getCount() {
                    return convList.size();
                }

                /* (non-Javadoc)
                 * @see android.widget.Adapter#getItem(int)
                 */
                @Override
                public Conversation getItem(int arg0) {
                    return convList.get(arg0);
                }

                /* (non-Javadoc)
                 * @see android.widget.Adapter#getItemId(int)
                 */
                @Override
                public long getItemId(int arg0) {
                    return arg0;
                }

                /* (non-Javadoc)
                 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
                 */
                @Override
                public View getView(int pos, View v, ViewGroup arg2) {
                    Conversation c = getItem(pos);
                    if (c.isSent())
                        v = getLayoutInflater().inflate(R.layout.chat_item_sent, null);
                    else
                        v = getLayoutInflater().inflate(R.layout.chat_item_rcv, null);


                    TextView lbl = (TextView) v.findViewById(R.id.lbl2);
                    lbl.setText(c.getMsg());

                    lbl = (TextView) v.findViewById(R.id.lbl3);
                    if (c.isSent()) {
                        if (c.getStatus() == Conversation.STATUS_SENT)
                            lbl.setText("Delivered");
                        else if (c.getStatus() == Conversation.STATUS_SENDING)
                            lbl.setText("Sending...");
                        else
                            lbl.setText("Failed");
                    } else
                        lbl.setText("");

                    return v;
                }

            }

            /* (non-Javadoc)
             * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
             */
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                if (item.getItemId() == android.R.id.home) {
                    finish();
                }
                return super.onOptionsItemSelected(item);
            }
        }
