package com.marcobarragan.runforachange;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.ParseException;

import org.json.JSONArray;


public class SignupActivity extends Activity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private static final int REQUEST_OAUTH = 1;

    /**
     *  Track whether an authorization activity is stacking over the current activity, i.e. when
     *  a known auth error is being resolved, such as showing the account chooser or presenting a
     *  consent dialog. This avoids common duplications as might happen on screen rotations, etc.
     */
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;

    private GoogleApiClient mClient = null;
    private String TAG = "LOG";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.initialize(this, "tDIszEasIJ8ebuKIaV5QMlkcliRCSDDzJT7IoVTk", "IEI8UKOHqvsNvZz61lClYSlYzswnxEpLeTWUPAZZ");

        mContext = this.getApplicationContext();

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        setContentView(R.layout.activity_signup);

        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
        Account account = null;

        final Button button = (Button) findViewById(R.id.btnSignUp);
        final EditText username   = (EditText)findViewById(R.id.etUserName);
        final EditText password   = (EditText)findViewById(R.id.etPass);
        final EditText fname   = (EditText)findViewById(R.id.etFName);
        final EditText lname   = (EditText)findViewById(R.id.etLName);
        final EditText passwordConfirm   = (EditText)findViewById(R.id.etPassConfirm);

        if (accounts.length > 0) {
            account = accounts[0];
            Log.d("EMAIL: ", account.name);
            username.setText(account.name);
        }

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                if(username.getText().toString().replaceAll("\\s+","").length() == 0
                        || password.getText().toString().replaceAll("\\s+","").length() == 0
                        || fname.getText().toString().replaceAll("\\s+","").length() == 0
                        || lname.getText().toString().replaceAll("\\s+","").length() == 0) {

                    Toast.makeText(mContext, "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
                    return;

                }

                if(!passwordConfirm.getText().toString().equals(password.getText().toString())) {
                    Toast.makeText(mContext, "The passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseUser user = new ParseUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.put("fName", fname.getText().toString());
                user.put("lName", lname.getText().toString());
                user.put("points",0);
                user.put("blockMode",false);
                ArrayList<Object> blockedList = new ArrayList<Object>();
                user.put("blocked", new JSONArray(blockedList));

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {

                            Log.d("SUCCESS","SUCCESS");
                            Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT);
                            Intent in = new Intent(getApplicationContext(), MainActivity.class);
                            in.putExtra("objid", ParseUser.getCurrentUser().getObjectId());
                            startActivity(in);
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            System.out.println("ERROR MESSAGE: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Email already taken.", Toast.LENGTH_SHORT);

                        }
                    }
                });
            }
        });
    }


}
