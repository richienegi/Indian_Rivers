package com.r.indian_rivers.views.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.r.indian_rivers.R;
import com.r.indian_rivers.model.Quiz;

import java.util.ArrayList;

public class AnswerList extends BaseAdapter {
    /*check*/
    Context c;
    ArrayList<Quiz> data;
    ArrayList<String> ans;

    public AnswerList(Context c, ArrayList<Quiz> data, ArrayList<String> ans) {
        this.c = c;
        this.data = data;
        this.ans = ans;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater in = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = in.inflate(R.layout.answerlist, parent, false);

        TextView t1 = (TextView) view.findViewById(R.id.question);
        TextView t2 = (TextView) view.findViewById(R.id.correctans);
        TextView t3 = (TextView) view.findViewById(R.id.yourans);
        ImageView img = (ImageView) view.findViewById(R.id.answercheck);
        try {
            ans.get(position);
        } catch (IndexOutOfBoundsException e) {
            ans.add("Nothing");
        }
        t1.setText(position + 1 + ") " + data.get(position).getQuestion());
        if (!ans.get(position).trim().equalsIgnoreCase(data.get(position).getAnswer().trim())) {
            img.setImageResource(R.drawable.wrong);
        } else {
            img.setImageResource(R.drawable.correct);
        }
        t2.setText("Correct Answer: " + data.get(position).getAnswer());
        t3.setText(ans.get(position));
        return view;
    }
}
