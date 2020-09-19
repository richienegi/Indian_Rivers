package com.r.indian_rivers.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.r.indian_rivers.R;
import com.r.indian_rivers.model.ContactInfo;
import com.r.indian_rivers.views.activity.DetailActivity;
import com.r.indian_rivers.views.activity.MainActivity;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactInfo> contactList;
    String length = "";
    String source = "";
    String destionation = "";
    String indialength = "";
    Context c;

    public ContactAdapter(List<ContactInfo> contactList, Context c) {
        this.c = c;
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        final ContactInfo ci = contactList.get(i);
        if (MainActivity.lang.equalsIgnoreCase("Hindi")) {
            length = ci.getHLength();
            source = ci.getHSource();
            destionation = ci.getHDestination();
            indialength = ci.getHIndianLength();
        } else if (MainActivity.lang.equalsIgnoreCase("English")) {

            length = ci.getELength();
            source = ci.getESource();
            destionation = ci.getEDestination();
            indialength = ci.getEIndianLength();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contactViewHolder.msource.setText(Html.fromHtml("<b>" + source + "</b>" + ci.getSource(), Html.FROM_HTML_MODE_COMPACT));
            contactViewHolder.mlength.setText(Html.fromHtml("<b>" + length + "</b>" + ci.getLength(), Html.FROM_HTML_MODE_COMPACT));
            contactViewHolder.mdrain.setText(Html.fromHtml("<b>" + destionation + "</b>" + ci.getDestination(), Html.FROM_HTML_MODE_COMPACT));
            contactViewHolder.mname.setText(Html.fromHtml("<b>" + (i + 1) + ": " + ci.getName() + "</b>", Html.FROM_HTML_MODE_COMPACT));
            if (ci.getIndian_length() != null) {
                contactViewHolder.mIlength.setVisibility(View.VISIBLE);
                contactViewHolder.mIlength.setText(Html.fromHtml("<b>" + indialength + "</b>" + ci.getIndian_length(), Html.FROM_HTML_MODE_COMPACT));

            } else {
                contactViewHolder.mIlength.setVisibility(View.GONE);
            }
        } else {
            contactViewHolder.msource.setText(Html.fromHtml("<b>" + source + "</b>" + ci.getSource()));
            contactViewHolder.mlength.setText(Html.fromHtml("<b>" + length + "</b>" + ci.getLength()));
            contactViewHolder.mdrain.setText(Html.fromHtml("<b>" + destionation + "</b>" + ci.getDestination()));
            contactViewHolder.mname.setText(Html.fromHtml("<b>" + (i + 1) + ": " + ci.getName() + "</b>"));
            if (ci.getIndian_length() != null) {
                contactViewHolder.mIlength.setVisibility(View.VISIBLE);
                contactViewHolder.mIlength.setText(Html.fromHtml("<b>" + indialength + "</b>" + ci.getIndian_length()));

            } else {
                contactViewHolder.mIlength.setVisibility(View.GONE);
            }

        }
        contactViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, DetailActivity.class);
                i.putExtra("name", ci.getName());
                i.putExtra("source", contactViewHolder.msource.getText().toString());
                i.putExtra("length", contactViewHolder.mlength.getText().toString());
                i.putExtra("drain", contactViewHolder.mdrain.getText().toString());
                i.putExtra("summary", ci.getSummary());
                i.putExtra("about", ci.getAbout());
                i.putExtra("left_tributaries", ci.getLeft_tributaries());
                i.putExtra("right_tributaries", ci.getRight_tributaries());
                i.putExtra("mythology", ci.getMythology());
                i.putExtra("dam", ci.getDam());
                i.putExtra("ilength", contactViewHolder.mIlength.getText().toString());

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
            cardView = v.findViewById(R.id.card_view);
        }
    }
}
