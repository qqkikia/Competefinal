package com.pillowtechnologies.mohamedaliaddi.compete;

/**
 * Created by Nynke on 13-Jan-16.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.pillowtechnologies.mohamedaliaddi.compete.custom.CustomActivity;
import com.pillowtechnologies.mohamedaliaddi.compete.utils.Utils;

/**
 * The Class Register is the Activity class that shows user registration screen
 * that allows user to register itself on Parse server for this Chat app.
 */
public class Register extends CustomActivity
{

    int n;
    /** The username EditText. */
    private EditText user;

    /** The password EditText. */
    private EditText pwd;

    /** The email EditText. */
    private EditText email;

    /* (non-Javadoc)
     * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        setTouchNClick(R.id.btnReg);

        user = (EditText) findViewById(R.id.user);
        pwd = (EditText) findViewById(R.id.pwd);
        email = (EditText) findViewById(R.id.email);


    }

    /* (non-Javadoc)
     * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
     */
    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        String u = user.getText().toString();
        String p = pwd.getText().toString();
        String e = email.getText().toString();
        if (u.length() == 0 || p.length() == 0 || e.length() == 0)
        {
            Utils.showDialog(this, R.string.err_fields_empty);
            return;
        }
        final ProgressDialog dia = ProgressDialog.show(this, null,
                getString(R.string.alert_wait));
        ParseObject.registerSubclass(ParseUser.class);
        Parse.initialize(this, "8tXOWKPc3gjRIW4MdF0Md7Rc935bDn9Nxa8S1XYb", "vDRj7x984WXSCzGsBpuLBDiCR4IhVJmPKycbFqpg");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

        final ParseUser pu = new ParseUser();
        pu.setEmail(e);
        pu.setPassword(p);
        pu.setUsername(u);
        pu.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e)
            {
                dia.dismiss();
                if (e == null)
                {
                    UserList.user = pu;
                    startActivity(new Intent(Register.this, GeneralActivity.class));
                    setResult(RESULT_OK);
                    finish();
                }
                else
                {
                    Utils.showDialog(
                            Register.this,
                            getString(R.string.err_singup) + " "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }
}
