package com.r.indian_rivers;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class HMainList extends Fragment {
AdView mAdView;
    AdRequest adRequest;
    String lang="";
    RecyclerView recList;
    ProgressDialog progressBar;
    ArrayList<ContactInfo> result;
    private PrefManager prefManager;
    public HMainList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hmain_list, container, false);
        recList = (RecyclerView)view.findViewById(R.id.hcardList);
        recList.setHasFixedSize(true);
        mAdView = view.findViewById(R.id.adView);
         adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        progressBar = new ProgressDialog(getContext());
        demo();
        TimerTask tt = new TimerTask() {

            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            mAdView.loadAd(adRequest);
                        }
                    });
                }
            }
        };

        Timer t = new Timer();
        t.scheduleAtFixedRate(tt, 0, 1000 * 30);
        return view;
    }
    public void demo()
    {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading ...");
        progressBar.show();//displays the progress bar

        result = new ArrayList<ContactInfo>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api1.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        Api1 api = retrofit.create(Api1.class);
        Call<List<rivers>> call  = api.getRivers();
        call.enqueue(new Callback<List<rivers>>() {
            @Override
            public void onResponse(Call<List<rivers>> call, Response<List<rivers>> response) {
                List<rivers> riv= response.body();

                for (int i = 0; i < riv.size(); i++) {
                    ContactInfo ci = new ContactInfo();
                    ci.length =riv.get(i).getLength();
                    ci.source =riv.get(i).getSource();
                    ci.destination =riv.get(i).getDestination();
                    ci.name =riv.get(i).getName();
                    ci.about=riv.get(i).getAbout();
                    ci.left_tributaries=riv.get(i).getLeft_tributaries();
                    ci.right_tributaries=riv.get(i).getRight_tributaries();
                    ci.dam=riv.get(i).getDam();
                    ci.mythology=riv.get(i).getMythology();
                    ci.summary=riv.get(i).getSummary();
                    ci.indian_length=riv.get(i).getIndian_length();
                    ci.major=riv.get(i).getMajor();
                    result.add(ci);

                }
                setAdapter(result);



            }

            @Override
            public void onFailure(Call<List<rivers>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.dismiss();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.option_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.name)
        {
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Data Loading ...");
            progressBar.show();//displays the progress bar

            Collections.sort(result, ContactInfo.RiverName);

            setAdapter(result);
            Toast.makeText(getContext(), "sorted by Name", Toast.LENGTH_SHORT).show();
        }
        else if(item.getItemId()==R.id.length)
        {
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Data Loading ...");
            progressBar.show();//displays the progress bar

            Collections.sort(result, ContactInfo.RiverLegth);

            setAdapter(result);
            Toast.makeText(getContext(), "sorted by Length", Toast.LENGTH_SHORT).show();
        }


        else if (item.getItemId()==R.id.lang)
        {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAdapter(ArrayList<ContactInfo>adapter)
    {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        ContactAdapter ca = new ContactAdapter(adapter,getContext());
        recList.setAdapter(ca);
        progressBar.dismiss();
    }
    private void showAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.halertad, null);
        AdView adView = alertLayout.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        prefManager = new PrefManager(getContext());

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setView(alertLayout);
        alertDialog.setTitle("Default Language :");
        String[] items = {"Hindi","English"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        lang="Hindi";
                        break;
                    case 1:
                        lang="English";
                        break;
                }
            }
        });
        alertDialog.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
prefManager.setLang(lang);
                        Toast.makeText(getContext(),lang +" is set as your default language", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}

