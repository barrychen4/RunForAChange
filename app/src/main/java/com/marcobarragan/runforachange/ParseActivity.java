package com.marcobarragan.runforachange;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


public class ParseActivity extends Application {

    protected void onCreate(Bundle savedInstanceState) {

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "tDIszEasIJ8ebuKIaV5QMlkcliRCSDDzJT7IoVTk", "IEI8UKOHqvsNvZz61lClYSlYzswnxEpLeTWUPAZZ");

//        final Context c = this.getApplicationContext();
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        Toast.makeText(c, "hi",Toast.LENGTH_SHORT);
//        SaveCallback sc = new SaveCallback() {
//
//            @Override
//            public void done(ParseException arg0) {
//                Toast.makeText(c, "dddd", Toast.LENGTH_SHORT);
//
//            }
//        };
//        testObject.saveInBackground(sc);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

}
