package com.tellerpoint.app.adapter;

/**
 * Created by eit on 3/18/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tellerpoint.app.R;
import com.tellerpoint.app.model.Merchants;

import java.util.List;

public class MerchantsAdapter extends RecyclerView.Adapter<MerchantsAdapter.MerchantViewHolder> {

    private List<Merchants> mMerchantsList;
    private Context context;
    public OnItemClickListener mItemClickListener;

//    private final FragmentActivity mActivity;

//    private List<Merchants> mMerchantDetails = new ArrayList<Merchants>();

    public MerchantsAdapter(Context context, List<Merchants> mMerchantsList) {
        this.mMerchantsList = mMerchantsList;
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
        holder.merchantLogo.setImageResource(mMerchantsList.get(position).getLogo());
    }


    @Override
    public int getItemCount() {
        return this.mMerchantsList.size();
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
//                final Merchants merchantItem = mMerchantDetails.get(getLayoutPosition());
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


//    private List<Merchants> getAllItemList(){
//
////        List<Merchants> allItems = new ArrayList<Merchants>();
//        mMerchantDetails.add(new Merchants("Silverbird", R.drawable.silverbird, 1, "products"));
//        mMerchantDetails.add(new Merchants("MaxMart", R.drawable.maxmart, 2, "generic"));
//        mMerchantDetails.add(new Merchants("Hueman Socks", R.drawable.hueman, 3, "products"));
//        mMerchantDetails.add(new Merchants("Pinocchio", R.drawable.pinocchio, 4, "generic"));
//        mMerchantDetails.add(new Merchants("Eya Naturals", R.drawable.eyanaturals, 5, "products"));
//
//        return mMerchantDetails;
//    }
}
