package com.r.indian_rivers.views.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;
import com.r.indian_rivers.R;
import com.r.indian_rivers.model.ContactInfo;
import com.r.indian_rivers.model.Rivers;
import com.r.indian_rivers.network.Api;
import com.r.indian_rivers.network.RetrofitInstance;
import com.r.indian_rivers.views.adapter.ContactAdapter;
import com.r.indian_rivers.views.alertdialog.AlertResponse;
import com.r.indian_rivers.views.alertdialog.ShowAlertDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class EMainList extends Fragment {

    AdView mAdView;
    AdRequest adRequest;
    RecyclerView recList;
    ProgressDialog progressBar;
    ArrayList<ContactInfo> result;

    public EMainList() {
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
        View view = inflater.inflate(R.layout.fragment_emain_list, container, false);
        recList = (RecyclerView) view.findViewById(R.id.ecardList);
        recList.setHasFixedSize(true);
        mAdView = view.findViewById(R.id.adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        progressBar = new ProgressDialog(getContext());
        fetchData();

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

    public void fetchData() {
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Data Loading ...");
        progressBar.show();//displays the progress bar

        result = new ArrayList<ContactInfo>();

        Api api = RetrofitInstance.getApi();

        Call<List<Rivers>> call = api.getEngRiversList();
        call.enqueue(new Callback<List<Rivers>>() {
            @Override
            public void onResponse(Call<List<Rivers>> call, Response<List<Rivers>> response) {
                List<Rivers> riv = response.body();

                for (int i = 0; i < riv.size(); i++) {
                    ContactInfo ci = new ContactInfo();
                    ci.setLength(riv.get(i).getLength());
                    ci.setSource(riv.get(i).getSource());
                    ci.setDestination(riv.get(i).getDestination());
                    ci.setName(riv.get(i).getName());
                    ci.setAbout(riv.get(i).getAbout());
                    ci.setLeft_tributaries(riv.get(i).getLeft_tributaries());
                    ci.setRight_tributaries(riv.get(i).getRight_tributaries());
                    ci.setDam(riv.get(i).getDam());
                    ci.setMythology(riv.get(i).getMythology());
                    ci.setSummary(riv.get(i).getSummary());
                    ci.setIndian_length(riv.get(i).getIndian_length());
                    ci.setMajor(riv.get(i).getMajor());

                    result.add(ci);
                }
                setAdapter(result);


            }

            @Override
            public void onFailure(Call<List<Rivers>> call, Throwable t) {
                Snackbar.make(mAdView, "Something went wrong", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fetchData();
                            }
                        })
                        .show();
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
        if (item.getItemId() == R.id.name) {
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Data Loading ...");
            progressBar.show();//displays the progress bar

            Collections.sort(result, ContactInfo.RiverName);

            setAdapter(result);
            Snackbar.make(mAdView, "Sorted By Name", Snackbar.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.length) {
            progressBar.setCancelable(false);//you can cancel it by pressing back button
            progressBar.setMessage("Data Loading ...");
            progressBar.show();//displays the progress bar

            Collections.sort(result, ContactInfo.RiverLegth);

            for (int i = 0; i < result.size(); i++) {
                Log.d("mydatafor", result.get(i).getLength());
            }
            setAdapter(result);
            Snackbar.make(mAdView, "Sorted By Length", Snackbar.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.lang) {
            showAlertDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAdapter(ArrayList<ContactInfo> adapter) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        ContactAdapter ca = new ContactAdapter(adapter, getContext());
        recList.setAdapter(new ScaleInAnimationAdapter(new AlphaInAnimationAdapter(ca)));
        progressBar.dismiss();
    }

    private void showAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.malertad, null);
        AdView adView = alertLayout.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        AlertResponse response = new AlertResponse() {
            @Override
            public void onPositiveClick(String message) {
                //Action to perform on positive button click
                Snackbar.make(mAdView, getString(R.string.lang_set_toast, message), Snackbar.LENGTH_SHORT).show();
            }
        };

        ShowAlertDialog dialog = new ShowAlertDialog(getContext(), alertLayout, response);
        dialog.showLanguageAlert();
    }
}
