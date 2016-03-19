package com.tellerpoint.app.fragment;

/**
 * Created by eit on 3/17/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tellerpoint.app.R;
import com.tellerpoint.app.activity.MerchantGenericPayActivity;
import com.tellerpoint.app.activity.MerchantProductsListing;
import com.tellerpoint.app.adapter.MerchantsAdapter;
import com.tellerpoint.app.model.Merchant;
import com.tellerpoint.app.rest.RestClient;
import com.tellerpoint.app.rest.services.APIService;

import java.util.ArrayList;
import java.util.List;


public class MerchantsFragment extends Fragment {
//    private LinearLayoutManager lLayout;
    private GridLayoutManager lLayout;
    private Context mContext;

    FragmentActivity mActivity;
    RecyclerView mRecyclerView;
    MerchantsAdapter rcAdapter;
    private List<Merchant> mMerchantDetails = new ArrayList<Merchant>();

//    private List<FeedItem> feedItemList;
//    private FeedItemAdaptor feedItemAdaptor;
    private boolean isConnected;
    APIService service;


    public MerchantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = RestClient.getClient();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (FragmentActivity) activity;
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_merchant_listings, container, false);
        this.mContext = getActivity().getApplicationContext();



//        List<Merchant> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(mContext, 2);

        RecyclerView rView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);

        rView.setLayoutManager(lLayout);

        rcAdapter = new MerchantsAdapter(mContext, mMerchantDetails);
        rView.setAdapter(rcAdapter);

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnected();

        getMerchantsFromServer();

        rcAdapter.SetOnItemClickListener(new MerchantsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                Log.e("clicked card", "got here: " + position);
                // do something with position
                final Merchant merchantItem = mMerchantDetails.get(position);
                String merchantId = merchantItem.getMerchantId();
                String merchantAccountType = merchantItem.getAccountType();
                String merchantName = merchantItem.getName();

                if (merchantAccountType.equalsIgnoreCase("product")) {
                    Intent i = new Intent(mContext, MerchantProductsListing.class);
                    i.putExtra("merchant_id", merchantId);
                    i.putExtra("merchant_name", merchantName);

                    i.putExtra("merchant_account_type", merchantAccountType);

                    startActivity(i);
                }
                else if (merchantAccountType.equalsIgnoreCase("generic")){
                    Intent i = new Intent(mContext, MerchantGenericPayActivity.class);
                    i.putExtra("merchant_id", merchantId);
                    i.putExtra("merchant_name", merchantName);

                    i.putExtra("merchant_account_type", merchantAccountType);

                    startActivity(i);
                }
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    private void getMerchantsFromServer() {
        String URL = "http://tellerpoint.herokuapp.com/index.php/api/merchants";

        if (isConnected) {
            Ion.with(mContext)
                    .load("GET", URL)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (e == null && result != null) {
                                Log.e("result", result.toString());

                                JsonArray merchantArray = result.getAsJsonArray("merchants");
                                int size = merchantArray.size();
                                Log.e("merchants array", merchantArray.toString());
                                Log.e("result size", size + " ");

                                if (size > 0) {
                                    for (int i = 0; i < size; i++) {
                                        JsonObject jsonObject = merchantArray.get(i).getAsJsonObject();
                                        String  id = jsonObject.get("id").getAsString();
                                        String merchant_name = jsonObject.get("merchant_name").getAsString();
                                        String merchant_logo = jsonObject.get("merchant_logo").getAsString();;
                                        String account_type = jsonObject.get("account_type").getAsString();
                                        Log.e("merchant logo", merchant_logo);

                                        mMerchantDetails.add(new Merchant(merchant_name, merchant_logo.trim(), id, account_type ));
                                        rcAdapter.notifyDataSetChanged();
                                    }
                                }

                            } else {
                                if (e != null) {
                                    Log.e("merchant error", e.toString());
                                }
                            }


                        }
                    });
        } else {
            Toast.makeText(this.mContext, "No internet Connection, Try again", Toast.LENGTH_LONG).show();
        }
    }









}
