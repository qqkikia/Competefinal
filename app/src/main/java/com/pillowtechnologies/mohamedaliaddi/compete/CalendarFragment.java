package com.pillowtechnologies.mohamedaliaddi.compete;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class CalendarFragment extends Fragment {

    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    public static CalendarFragment newInstance() {

        CalendarFragment p = new CalendarFragment();




        return p;
    }
}
