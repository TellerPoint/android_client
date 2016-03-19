package com.tellerpoint.app.activity;

/**
 * Created by eit on 3/17/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.tellerpoint.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PaymentFormActivity extends AppCompatActivity {
    private TextView toolbarTitle;

    private Context context;
    private boolean isConnected;
    private String merchant_id;
    private String transactionId;
    private String smsCode;

    private String merchant_name;
    private String product_id;
    private String product_img;

    private String product_amount;
    private String product_name;
    private String product_desc;
    private String qty;
    private String customerPhoneNumber;


    private static final String TAG = "PaymentFormActivity";

    @InjectView(R.id.btn_pay) Button payButton;
    @InjectView(R.id.productImage) ImageView productImage;
    @InjectView(R.id.product_name) TextView productName;
    @InjectView(R.id.product_desc) TextView productDescription;
    @InjectView(R.id.product_amount) TextView productAmount;
    @InjectView(R.id.input_phone_number) EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_form);
        ButterKnife.inject(this);

        this.context = getApplicationContext();

        Intent intent = getIntent();
        merchant_id = intent.getStringExtra("merchant_id");
        merchant_name = intent.getStringExtra("merchant_name");
        product_id = intent.getStringExtra("product_id");
        product_name = intent.getStringExtra("product_name");
        product_amount = intent.getStringExtra("product_amount");
        product_desc = intent.getStringExtra("product_desc");
        product_img = intent.getStringExtra("product_img");
        qty = intent.getStringExtra("qty");

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

//        pBar = (ProgressBar) findViewById(R.id.loadingPanelComment);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        Log.e("merchant_name", merchant_name);
        Log.e("qty", qty);


        toolbarTitle = (TextView) findViewById(R.id.actionbartitle);
        toolbarTitle.setText(merchant_name);

        Picasso.with(context).load(Uri.parse(product_img))
                .noFade()
                .fit()
                .placeholder(R.drawable.ic_action_search)
                .into(productImage);

        productName.setText(product_name);
        productDescription.setText(product_desc);
        productAmount.setText(product_amount + " GHC");

        payButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                customerPhoneNumber = phoneNumber.getText().toString();
                submitPayment(merchant_id, product_id, customerPhoneNumber, qty, product_amount);
            }

        });


    }

    public void submitPayment(String merchant_id, String product_id, String customerPhoneNumber, String qty, String product_amount){
        final ProgressDialog progressDialog = new ProgressDialog(PaymentFormActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String URL = "http://tellerpoint.herokuapp.com/index.php/api/purchase";


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("merchant_id", merchant_id);
        jsonObject.addProperty("product_id", product_id);
        jsonObject.addProperty("customer_phone", customerPhoneNumber);
        jsonObject.addProperty("qty", qty);
        jsonObject.addProperty("amount", product_amount);

        Ion.with(PaymentFormActivity.this)
                .load("POST", URL)
                .setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.e("api exception", e.toString());
                        Log.e("api result", result.toString());

                        if (e == null && result != null) {
                            progressDialog.dismiss();
                            Log.e("purchase sent", result.toString());
                            payButton.setEnabled(false);

                            JsonObject jsonObject = result.getAsJsonObject();
                            transactionId = jsonObject.get("id").getAsString();
                            Log.e("transaction id", transactionId);

                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setTitle("ENTER SMS CODE");
                            final EditText input = new EditText(context);
                            input.setInputType(InputType.TYPE_CLASS_NUMBER);
                            input.setRawInputType(Configuration.KEYBOARD_12KEY);
                            alert.setView(input);
                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Put actions for OK button here
                                    verifySmsCode(input.getText().toString());
                                }
                            });
                            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Put actions for CANCEL button here, or leave in blank
                                }
                            });
                            alert.show();

                        } else {
                            Log.e("api result", e.toString());
                            payButton.setEnabled(true);

                        }
                    }

                });
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

    private void verifySmsCode(String smsCode){

    }


}