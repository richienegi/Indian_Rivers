package com.r.indian_rivers;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class Suggestion extends Fragment {
    ProgressDialog progressBar;
AdView mAdView;
TextInputEditText memail,msug;
Button btn;
    AdRequest adRequest;
    public Suggestion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_suggestion, container, false);

        mAdView = view.findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        memail=view.findViewById(R.id.from);
        msug=view.findViewById(R.id.suggestion);
        btn=view.findViewById(R.id.button);
        progressBar = new ProgressDialog(getContext());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String from=memail.getText().toString();

                String suggestion=msug.getText().toString();
                if(from.isEmpty()|| suggestion.isEmpty())
                {
                    Toast.makeText(getContext(), "Please enter your email id and sugeestion", Toast.LENGTH_SHORT).show();
                }
else {
                    sendRequest(from, suggestion);

                    progressBar.setCancelable(false);//you can cancel it by pressing back button
                    progressBar.setMessage("Mail sending...");
                    progressBar.show();
                }
            }
        });
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        mAdView.loadAd(adRequest);
                    }
                });

            }
        };

        Timer t = new Timer();
        t.scheduleAtFixedRate(tt, 0, 1000 * 30);
        return view;
    }

    private void sendRequest(final String from, final String suggestion){


        StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://ritikanegi.com/one.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.dismiss();
                        memail.setText("");
                            msug.setText("");
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.thanku,  (ViewGroup) getActivity().findViewById(R.id.custom_toast_container));
                        Toast toast = new Toast(getContext());
                        toast.setGravity(Gravity.TOP, 0, 40);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        progressBar.dismiss();
                    }

                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("from",from);
                params.put("suggetion", suggestion);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
