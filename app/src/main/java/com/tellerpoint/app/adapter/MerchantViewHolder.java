//package com.tellerpoint.app.adapter;
//
///**
// * Created by eit on 3/18/16.
// */
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.tellerpoint.app.R;
//
//
//public class MerchantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//    public TextView merchantName;
//    public TextView merchantId;
//
//
//    public ImageView merchantLogo;
//
//    public MerchantViewHolder(View itemView) {
//        super(itemView);
//        itemView.setOnClickListener(this);
////        merchantName = (TextView)itemView.findViewById(R.id.merchant_name);
//        merchantLogo = (ImageView)itemView.findViewById(R.id.merchant_logo);
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        mItemClickListener.onItemClick(v, getPosition()); //OnItemClickListener mItemClickListener;
//    }
//
//
//    public interface OnItemClickListener {
//        public void onItemClick(View view , int position);
//    }
//
//
//
//
//}
