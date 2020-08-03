package com.r.indian_rivers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactInfo> contactList;
    String length="";
    String source="";
    String destionation="";
    String indialength="";
Context c;
    public ContactAdapter(List<ContactInfo> contactList,Context c) {
        this.c=c;
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        final ContactInfo ci = contactList.get(i);
        if(MainActivity.lang.equalsIgnoreCase("Hindi"))
        {
            length=ContactInfo.HLength;
            source=ContactInfo.HSource;
            destionation=ContactInfo.HDestination;
            indialength=ContactInfo.HindianLength;
        }
        else if(MainActivity.lang.equalsIgnoreCase("English"))
        {

            length=ContactInfo.ELength;
            source=ContactInfo.ESource;
            destionation=ContactInfo.EDestination;
            indialength=ContactInfo.EindianLength;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contactViewHolder.msource.setText(Html.fromHtml("<b>"+source+"</b>"+ci.source,Html.FROM_HTML_MODE_COMPACT));
            contactViewHolder.mlength.setText(Html.fromHtml("<b>"+length+"</b>"+ci.length,Html.FROM_HTML_MODE_COMPACT));
            contactViewHolder.mdrain.setText(Html.fromHtml("<b>"+destionation+"</b>"+ci.destination,Html.FROM_HTML_MODE_COMPACT));
            contactViewHolder.mname.setText(Html.fromHtml( (i+1)+" : "+"<b>"+ci.name+"</b>",Html.FROM_HTML_MODE_COMPACT));
            if (ci.indian_length!=null) {
                contactViewHolder.mIlength.setVisibility(View.VISIBLE);
                contactViewHolder.mIlength.setText(Html.fromHtml("<b>" + indialength + "</b>" + ci.indian_length, Html.FROM_HTML_MODE_COMPACT));

            }
            else
            {
                contactViewHolder.mIlength.setVisibility(View.GONE);
            }
            } else {
            contactViewHolder.msource.setText(Html.fromHtml("<b>"+source+"</b>"+ci.source));
            contactViewHolder.mlength.setText(Html.fromHtml("<b>"+length+"</b>"+ci.length));
            contactViewHolder.mdrain.setText(Html.fromHtml("<b>"+destionation+"</b>"+ci.destination));
            if (ci.indian_length!=null) {
                contactViewHolder.mIlength.setVisibility(View.VISIBLE);
                contactViewHolder.mIlength.setText(Html.fromHtml("<b>"+indialength+"</b>"+ci.indian_length));

            }
            else
            {
                contactViewHolder.mIlength.setVisibility(View.GONE);
            }

            contactViewHolder.mname.setText(Html.fromHtml((i+1)+" : "+"<b>"+ci.name+"</b>"));
        }
        contactViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(c,DetailActivity.class);
                i.putExtra("name",ci.name);
                i.putExtra("source",contactViewHolder.msource.getText().toString());
                i.putExtra("length",contactViewHolder.mlength.getText().toString());
                i.putExtra("drain",contactViewHolder.mdrain.getText().toString());
                i.putExtra("summary",ci.summary);
                i.putExtra("about",ci.about);
                i.putExtra("left_tributaries",ci.left_tributaries);
                i.putExtra("right_tributaries",ci.right_tributaries);
                i.putExtra("mythology",ci.mythology);
                i.putExtra("dam",ci.dam);
                i.putExtra("ilength",contactViewHolder.mIlength.getText().toString());

                c.startActivity(i);

            }
        });

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        protected TextView msource;
        protected TextView mlength;
        protected TextView mdrain;
        protected TextView mname;
        protected TextView mIlength;

        public ContactViewHolder(View v) {
            super(v);
            msource = (TextView) v.findViewById(R.id.source);
            mlength = (TextView) v.findViewById(R.id.length);
            mdrain = (TextView) v.findViewById(R.id.drain);
            mname = (TextView) v.findViewById(R.id.Name);
            mIlength = (TextView) v.findViewById(R.id.indialength);
            cardView=v.findViewById(R.id.card_view);
        }
    }
}
