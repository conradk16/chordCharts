package com.realbook.jazz.chord.charts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<ListEntry> {

    private int resourceLayout;
    private Context mContext;


    public CustomListAdapter(Context context, int resource, List<ListEntry> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        ListEntry p = getItem(position);

        if (p != null) {
            TextView tv = v.findViewById(R.id.titleTextView);
            TextView tv2 = v.findViewById(R.id.authorTextView);
            ImageView iv = v.findViewById(R.id.lockImageView);

            if (tv != null) {
                tv.setText(p.t1);
            }

            if (tv2 != null) {
                tv2.setText(p.t2);
            }

            if (iv != null) {
                if (p.image == null) {
                    iv.setImageResource(0);
                } else {
                    iv.setImageResource(p.image);
                }

            }
        }

        return v;
    }

}
