package com.pillowtechnologies.mohamedaliaddi.compete;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatFragment extends Fragment {

    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    public static ChatFragment newInstance() {

        ChatFragment c = new ChatFragment();




        return c;
    }


}
