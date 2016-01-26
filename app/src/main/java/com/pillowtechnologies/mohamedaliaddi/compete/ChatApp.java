package com.pillowtechnologies.mohamedaliaddi.compete;

/**
 * Created by Nynke on 13-Jan-16.
 */
import android.app.Application;

import com.parse.Parse;

public class ChatApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "8tXOWKPc3gjRIW4MdF0Md7Rc935bDn9Nxa8S1XYb", "vDRj7x984WXSCzGsBpuLBDiCR4IhVJmPKycbFqpg");
    }
}