package com.pillowtechnologies.mohamedaliaddi.compete;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.Profile;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public static ProfileFragment newInstance() {

        ProfileFragment p = new ProfileFragment();




        return p;
    }
}
