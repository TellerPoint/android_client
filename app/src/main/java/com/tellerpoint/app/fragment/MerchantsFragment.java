package com.tellerpoint.app.fragment;

/**
 * Created by eit on 3/17/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tellerpoint.app.R;
import com.tellerpoint.app.activity.MerchantGenericPayActivity;
import com.tellerpoint.app.activity.MerchantProductsListing;
import com.tellerpoint.app.adapter.MerchantsAdapter;
import com.tellerpoint.app.model.Merchants;

import java.util.ArrayList;
import java.util.List;


public class MerchantsFragment extends Fragment {
//    private LinearLayoutManager lLayout;
    private GridLayoutManager lLayout;
    private Context mContext;

    FragmentActivity mActivity;
    RecyclerView mRecyclerView;
    MerchantsAdapter adapter;
    private List<Merchants> mMerchantDetails = new ArrayList<Merchants>();


    public MerchantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (FragmentActivity) activity;
        setRetainInstance(true);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_merchant_listings, container, false);
        this.mContext = getActivity().getApplicationContext();


        List<Merchants> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(mContext, 2);

        RecyclerView rView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);

        rView.setLayoutManager(lLayout);

        MerchantsAdapter rcAdapter = new MerchantsAdapter(mContext, rowListItem);
        rView.setAdapter(rcAdapter);

        rcAdapter.SetOnItemClickListener(new MerchantsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                Log.e("clicked card", "got here: " + position);
                // do something with position
                final Merchants merchantItem = mMerchantDetails.get(position);
                int merchantId = merchantItem.getMerchantId();
                String merchantAccountType = merchantItem.getAccountType();

                if (merchantAccountType.equalsIgnoreCase("product")) {
                    Intent i = new Intent(mContext, MerchantProductsListing.class);
                    i.putExtra("merchant_id", merchantId);
                    i.putExtra("merchant_account_type", merchantAccountType);

                    startActivity(i);
                }
                else if (merchantAccountType.equalsIgnoreCase("generic")){
                    Intent i = new Intent(mContext, MerchantGenericPayActivity.class);
                    i.putExtra("merchant_id", merchantId);
                    i.putExtra("merchant_account_type", merchantAccountType);

                    startActivity(i);
                }
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_merchant_listings, container, false);
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//        adapter = new MerchantsAdapter(mActivity);
//        return rootView;
//    }



//    @Override
//    public void onViewCreated(View view , Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mRecyclerView.setAdapter(adapter);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        adapter.SetOnItemClickListener(new MerchantsAdapter.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(View v, int position) {
//                Log.e("clicked card", "got here: " + position);
//                // do something with position
////                final Merchants merchantItem = mMerchantDetails.get(position);
////                int merchantId = merchantItem.getMerchant_id();
////                String merchantAccountType = merchantItem.getAccountType();
////
////                Intent i = new Intent(mContext, MerchantProductsListing.class);
////                i.putExtra("merchant_id", merchantId);
////                i.putExtra("merchant_account_type", merchantAccountType);
////
////                startActivity(i);
//            }
//        });
//    }


    private List<Merchants> getAllItemList(){

//        List<Merchants> allItems = new ArrayList<Merchants>();
        mMerchantDetails.add(new Merchants("Silverbird", R.drawable.silverbird, 1, "product"));
        mMerchantDetails.add(new Merchants("MaxMart", R.drawable.maxmart, 2, "generic"));
        mMerchantDetails.add(new Merchants("Hueman Socks", R.drawable.hueman, 3, "product"));
        mMerchantDetails.add(new Merchants("Pinocchio", R.drawable.pinocchio, 4, "generic"));
        mMerchantDetails.add(new Merchants("Eya Naturals", R.drawable.eyanaturals, 5, "product"));

        return mMerchantDetails;
    }






}
