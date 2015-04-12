package com.marcobarragan.runforachange;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private static final String ARG_SECTION_NUMBER = "section_number";
    private String objid;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(int sectionNumber) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objid = this.getActivity().getIntent().getStringExtra("objid");
        Log.d("OBJID", objid);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", objid);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    ParseObject po = objects.get(0);
                    ((TextView) getActivity().findViewById(R.id.name)).setText("Name: " + po.get("fName") + " " + po.get("lName"));
                    ((TextView) getActivity().findViewById(R.id.email)).setText("Email: " + po.get("username"));
                    ((TextView) getActivity().findViewById(R.id.totalmiles)).setText("Distance run (feet): " + po.get("points"));
                } else {
                    // Something went wrong.
                }
            }
        });


//        ParseObject po = null;
//        try {
//            po = query.get("3jrIt7WRBh");
//            //The code you are trying to exception handle
//        }catch (Exception e) {
//            //The handling for the code
//            Log.d("FAILURES", "FAILURE");
//        }

//        ((TextView) getActivity().findViewById(R.id.name)).setText("Name: " + po.get("fName") + " " + po.get("lName"));
//        ((TextView) getActivity().findViewById(R.id.email)).setText("Email: " + po.get("username"));
//        ((TextView) getActivity().findViewById(R.id.totalmiles)).setText("Distance run (feet): " + po.get("points"));

//        final
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment@Override
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);

        final Button but = (Button) rootView.findViewById(R.id.btnRefresh);
        but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ParseQuery query = ParseUser.getQuery();
                query.whereEqualTo("objectId", objid);
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            // The query was successful.
                            ParseObject po = objects.get(0);
                            ((TextView) getActivity().findViewById(R.id.name)).setText("Name: " + po.get("fName") + " " + po.get("lName"));
                            ((TextView) getActivity().findViewById(R.id.email)).setText("Email: " + po.get("username"));
                            ((TextView) getActivity().findViewById(R.id.totalmiles)).setText("Distance run (feet): " + po.get("points"));
                        } else {
                            // Something went wrong.
                        }
                    }
                });
            }
        });


        final Button btn_search = (Button) rootView.findViewById(R.id.search_b);
        final EditText query_text = (EditText) rootView.findViewById(R.id.search_q);

        btn_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                List<String> q_str = Arrays.asList(query_text.getText().toString().split("\\s+"));

                ArrayList<ParseQuery<ParseUser>> queries = new ArrayList<ParseQuery<ParseUser>>();
                ParseQuery<ParseUser> query1 = ParseUser.getQuery();
                query1.whereContainedIn("fName", q_str);
                ParseQuery<ParseUser> query2 = ParseUser.getQuery();
                query2.whereContainedIn("lName", q_str);
                ParseQuery<ParseUser> query3 = ParseUser.getQuery();
                query3.whereContainedIn("username", q_str);

                queries.add(query1);
                queries.add(query2);
                queries.add(query3);

                ParseQuery finalQuery = ParseQuery.or(queries);
                final Context c = getActivity().getApplicationContext();

                finalQuery.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null && objects.size() > 0) {
                            // The query was successful.
                            String searched_objid = objects.get(0).getObjectId();

                            //Intent in = new Intent(c, MainActivity.class);
                            //in.putExtra("objid", searched_objid);
                            //startActivity(in);

                            ParseUser po = objects.get(0);
                            ((TextView) getActivity().findViewById(R.id.name)).setText("Name: " + po.get("fName") + " " + po.get("lName"));
                            ((TextView) getActivity().findViewById(R.id.email)).setText("Email: " + po.get("username"));
                            ((TextView) getActivity().findViewById(R.id.totalmiles)).setText("Points: " + po.get("points"));
                        } else {
                            // Something went wrong.
                            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Users");
                            List<String> q_str = Arrays.asList(query_text.getText().toString().split("\\s+"));

                            query1.whereContainedIn("objectId", q_str);
                            query1.findInBackground(new FindCallback<ParseObject>() {
                                public void done(List<ParseObject> objects, ParseException e) {
                                    if (e == null && objects.size() > 0) {
                                        ParseObject po = objects.get(0);

                                        ((TextView) getActivity().findViewById(R.id.name)).setText("UserID: " + po.getObjectId());
                                        ((TextView) getActivity().findViewById(R.id.email)).setText("Distance run (feet): " + po.get("points"));
                                        ((TextView) getActivity().findViewById(R.id.totalmiles)).setText("");
                                    } else {

                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        return rootView;

    }

}
