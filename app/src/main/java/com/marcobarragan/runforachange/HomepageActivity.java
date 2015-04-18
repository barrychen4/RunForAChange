package com.marcobarragan.runforachange;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;


public class HomepageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Parse.initialize(this, "tDIszEasIJ8ebuKIaV5QMlkcliRCSDDzJT7IoVTk", "IEI8UKOHqvsNvZz61lClYSlYzswnxEpLeTWUPAZZ");

        if(ParseUser.getCurrentUser() != null) {
            Intent in = new Intent(getApplicationContext(), MainActivity.class);
            in.putExtra("objid", ParseUser.getCurrentUser().getObjectId());
            startActivity(in);
            return;
        }
        setContentView(R.layout.activity_homepage);

        final Button btnLogin = (Button) findViewById(R.id.btnLoginHome);
        final Button btnSignup = (Button) findViewById(R.id.btnSignUpHome);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(in);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
