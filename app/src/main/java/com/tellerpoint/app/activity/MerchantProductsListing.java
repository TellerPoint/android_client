package com.tellerpoint.app.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tellerpoint.app.R;
import com.tellerpoint.app.adapter.ProductItemAdaptor;
import com.tellerpoint.app.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eit on 3/18/16.
 */
public class MerchantProductsListing extends AppCompatActivity {
    ListView productListView;
    ProductItemAdaptor productItemAdaptor;
    private List<Product> productItemList = new ArrayList<>();
    private ProgressBar pBar;
    private TextView toolbarTitle1;
    private TextView toolbarTitle;

    private Context context;
//    APIService service;
    private boolean isConnected;
    private String merchant_id;
    private String merchant_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_listings);

        this.context = getApplicationContext();

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

//        pBar = (ProgressBar) findViewById(R.id.loadingPanelComment);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        merchant_id = intent.getStringExtra("merchant_id");
        merchant_name = intent.getStringExtra("merchant_name");

//        toolbarTitle1 = (TextView) findViewById(R.id.toolbar);
//        toolbarTitle1.setText(merchant_name);

        toolbarTitle = (TextView) findViewById(R.id.actionbartitle);
        toolbarTitle.setText(merchant_name);

        productListView = (ListView) findViewById(R.id.product_listview);

        productItemAdaptor = new ProductItemAdaptor(getApplicationContext(), R.layout.product_item, productItemList);

        productListView.setAdapter(productItemAdaptor);


        getMerchantProducts();


    }


    private void getMerchantProducts(){
        String URL = "http://tellerpoint.herokuapp.com/index.php/api/merchants/products/mid/" + merchant_id;

        if (isConnected) {
            Ion.with(context)
                    .load("GET", URL)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (e == null && result != null) {
                                Log.e("result", result.toString());

                                JsonArray merchantArray = result.getAsJsonArray("products");
                                int size = merchantArray.size();
                                Log.e("products array", merchantArray.toString());
                                Log.e("result size", size + " ");

                                if (size > 0) {
                                    for (int i = 0; i < size; i++) {
                                        JsonObject jsonObject = merchantArray.get(i).getAsJsonObject();
                                        String  id = jsonObject.get("id").getAsString();
                                        String product_name = jsonObject.get("product_name").getAsString();
                                        String product_desc = jsonObject.get("product_desc").getAsString();;
                                        String product_img = jsonObject.get("product_img").getAsString();
                                        String product_code = jsonObject.get("product_code").getAsString();
                                        String product_amount = jsonObject.get("product_amount").getAsString();
                                        Log.e("product name", product_name);

                                        productItemList.add(new Product(id, product_name, merchant_id, merchant_name, product_desc, product_img, product_code, product_amount));
                                        Log.e("product item list", "" + productItemList.size());
                                    }
                                    productItemAdaptor.notifyDataSetChanged();

                                }

                            } else {
                                if (e != null) {
                                    Log.e("products error", e.toString());
                                }
                            }


                        }
                    });
        } else {
            Toast.makeText(this.context, "No internet Connection, Try again", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
