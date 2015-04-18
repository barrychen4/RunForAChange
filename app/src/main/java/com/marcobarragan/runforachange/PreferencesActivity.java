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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class PreferencesActivity extends ActionBarActivity {

    public static final int ACTIVITY_LEVEL_HIGH = 10;
    public static final int ACTIVITY_LEVEL_MEDIUM = 7;
    public static final int ACTIVITY_LEVEL_LOW = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        ParseUser currUser = ParseUser.getCurrentUser();
        switch(currUser.getNumber("conversionRate").intValue()) {
            case ACTIVITY_LEVEL_HIGH:
                final RadioButton highButton = (RadioButton) findViewById(R.id.level_high);
                highButton.setChecked(true);
                break;
            case ACTIVITY_LEVEL_MEDIUM:
                final RadioButton medButton = (RadioButton) findViewById(R.id.level_medium);
                medButton.setChecked(true);
                break;
            case ACTIVITY_LEVEL_LOW:
                final RadioButton lowButton = (RadioButton) findViewById(R.id.level_low);
                lowButton.setChecked(true);
                break;
            default:
                final RadioButton customButton = (RadioButton) findViewById(R.id.custom);
                customButton.setChecked(true);
                final EditText customText = (EditText) findViewById(R.id.customRate);
                customText.setText("" + currUser.getNumber("conversionRate").intValue());
                break;
        }

        final Button updateButton = (Button) findViewById(R.id.btnUpdatePrefs);

        updateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ParseUser currUser = ParseUser.getCurrentUser();
                int convRate = 0;
                final RadioGroup activityGroup = (RadioGroup) findViewById(R.id.activity_radio_group);
//                Toast.makeText(getApplicationContext(), "ss" + activityGroup.getCheckedRadioButtonId(), Toast.LENGTH_SHORT).show();
                switch(activityGroup.getCheckedRadioButtonId()) {
                    case R.id.level_high:
                        convRate = PreferencesActivity.ACTIVITY_LEVEL_HIGH;
                        break;
                    case R.id.level_medium:
                        convRate = PreferencesActivity.ACTIVITY_LEVEL_MEDIUM;
                        break;
                    case R.id.level_low:
                        convRate = PreferencesActivity.ACTIVITY_LEVEL_LOW;
                        break;
                    default:
                        try {
                            final EditText customVal = (EditText) findViewById(R.id.customRate);

                            convRate = Integer.parseInt(customVal.getText().toString().replaceAll("\\s+", ""));
                        } catch(NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Customize field is not a number.", Toast.LENGTH_SHORT);
                            return;
                        }
                }
                currUser.put("conversionRate", convRate);
                currUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(getApplicationContext(), MainActivity.class);
                            in.putExtra("objid", ParseUser.getCurrentUser().getObjectId());
                            startActivity(in);
                        } else {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
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
