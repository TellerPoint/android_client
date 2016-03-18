package com.tellerpoint.app.fragment;

/**
 * Created by eit on 3/17/16.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tellerpoint.app.R;
import com.tellerpoint.app.adapter.ItemObject;
import com.tellerpoint.app.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class MerchantsFragment extends Fragment {
//    private LinearLayoutManager lLayout;
    private GridLayoutManager lLayout;
    private Context mContext;

    public MerchantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_merchant, container, false);
        this.mContext = getActivity().getApplicationContext();


        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(mContext, 2);

        RecyclerView rView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);

        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(mContext, rowListItem);
        rView.setAdapter(rcAdapter);


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("United States", R.drawable.silverbird));
        allItems.add(new ItemObject("Sweden", R.drawable.maxmart));
        allItems.add(new ItemObject("Canada", R.drawable.hueman));
        allItems.add(new ItemObject("United Kingdom", R.drawable.pinocchio));
        allItems.add(new ItemObject("Germany", R.drawable.eyanaturals));

        return allItems;
    }
}
