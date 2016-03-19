package com.tellerpoint.app.adapter;

/**
 * Created by eit on 3/18/16.
 */

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tellerpoint.app.R;
import com.tellerpoint.app.model.Merchant;

import java.util.List;

public class MerchantsAdapter extends RecyclerView.Adapter<MerchantsAdapter.MerchantViewHolder> {

    private List<Merchant> mMerchantList;
    private Context context;
    public OnItemClickListener mItemClickListener;

//    private final FragmentActivity mActivity;

//    private List<Merchant> mMerchantDetails = new ArrayList<Merchant>();

    public MerchantsAdapter(Context context, List<Merchant> mMerchantList) {
        this.mMerchantList = mMerchantList;
        this.context = context;
    }

//    public MerchantsAdapter(FragmentActivity mActivity) {
//        this.mActivity = mActivity;
//        getAllItemList();
//    }

    @Override
    public MerchantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        MerchantViewHolder rcv = new MerchantViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MerchantViewHolder holder, int position) {
//        holder.merchantLogo.setImageResource(mMerchantList.get(position).getLogo());

        Picasso.with(context).load(Uri.parse(mMerchantList.get(position).getLogo()))
                .noFade()
                .fit()
                .placeholder(R.drawable.ic_action_search)
                .into(holder.merchantLogo);
    }


    @Override
    public int getItemCount() {
        return this.mMerchantList.size();
    }




    public class MerchantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView merchantName;
        public TextView merchantId;


        public ImageView merchantLogo;

        public MerchantViewHolder(View itemView) {
            super(itemView);
            merchantLogo = (ImageView)itemView.findViewById(R.id.merchant_logo);
//        merchantName = (TextView)itemView.findViewById(R.id.merchant_name);


            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
//                final Merchant merchantItem = mMerchantDetails.get(getLayoutPosition());
//                int merchantId = merchantItem.getMerchant_id();
//                String merchantAccountType = merchantItem.getAccountType();

                Log.e("card clicked position: ", getLayoutPosition() + " card clicked");

            }
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


//    private List<Merchant> getAllItemList(){
//
////        List<Merchant> allItems = new ArrayList<Merchant>();
//        mMerchantDetails.add(new Merchant("Silverbird", R.drawable.silverbird, 1, "products"));
//        mMerchantDetails.add(new Merchant("MaxMart", R.drawable.maxmart, 2, "generic"));
//        mMerchantDetails.add(new Merchant("Hueman Socks", R.drawable.hueman, 3, "products"));
//        mMerchantDetails.add(new Merchant("Pinocchio", R.drawable.pinocchio, 4, "generic"));
//        mMerchantDetails.add(new Merchant("Eya Naturals", R.drawable.eyanaturals, 5, "products"));
//
//        return mMerchantDetails;
//    }
}
