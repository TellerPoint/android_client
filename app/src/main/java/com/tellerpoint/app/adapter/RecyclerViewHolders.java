package com.tellerpoint.app.adapter;

/**
 * Created by eit on 3/18/16.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tellerpoint.app.R;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView merchantName;
    public ImageView merchantLogo;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
//        merchantName = (TextView)itemView.findViewById(R.id.merchant_name);
        merchantLogo = (ImageView)itemView.findViewById(R.id.merchant_logo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Merchant Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
