package com.pillowtechnologies.mohamedaliaddi.compete;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class LaterFragment extends Fragment {

    public LaterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_later, container, false);
    }

    public static LaterFragment newInstance(String text) {

        LaterFragment l = new LaterFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        l.setArguments(b);

        return l;
    }
}
