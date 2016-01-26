package com.pillowtechnologies.mohamedaliaddi.compete;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class NowFragment extends Fragment {

    public NowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_now, container, false);
    }

    public static NowFragment newInstance(String text) {

        NowFragment n = new NowFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        n.setArguments(b);

        return n;
    }
}
