package com.tellerpoint.app.activity;

/**
 * Created by eit on 3/17/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;
import com.tellerpoint.app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PaymentFormActivity extends Activity {

    private TextView toolbarTitle;

    private Context context;
    private boolean isConnected;
    private String merchant_id;
    private int transactionId;
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
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);



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
                transactionId = 25;
                submitPayment(merchant_id, product_id, customerPhoneNumber, qty, product_amount, transactionId);
            }

        });


    }

    public void submitPayment(String merchant_id, String product_id, String customerPhoneNumber, String qty, String product_amount, final int transactionId){
        final ProgressDialog progressDialog = new ProgressDialog(PaymentFormActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

//        String URL = "http://tellerpoint.herokuapp.com/index.php/api/purchase";
        String URL = "http://tellerpoint.herokuapp.com/index.php/api/purchase/" + merchant_id +"/" + product_id +"/" + customerPhoneNumber + "/" + qty + "/" + product_amount;


//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("merchant_id", merchant_id);
//        jsonObject.addProperty("product_id", product_id);
//        jsonObject.addProperty("customer_phone", customerPhoneNumber);
//        jsonObject.addProperty("qty", qty);
//        jsonObject.addProperty("amount", product_amount);
        if (isConnected) {
        Ion.with(context)
                .load("GET", URL)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
//                        if (e == null && result != null) {
                            progressDialog.dismiss();
//                            Log.e("purchase sent", result.toString());
//                            payButton.setEnabled(false);

//                            JsonObject jsonObject = result.getAsJsonObject();
//                            transactionId = 23 + 1;
//                            Log.e("transaction id", transactionId);

                            AlertDialog.Builder alert = new AlertDialog.Builder(PaymentFormActivity.this);
                            alert.setTitle("ENTER SMS CODE");
                            final EditText input = new EditText(PaymentFormActivity.this);
                            input.setInputType(InputType.TYPE_CLASS_NUMBER);
                            input.setRawInputType(Configuration.KEYBOARD_12KEY);
                            alert.setView(input);
                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Put actions for OK button here
                                    verifySmsCode(input.getText().toString(), transactionId + 1);
                                }
                            });
                            alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Put actions for CANCEL button here, or leave in blank
                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                    }

                });
        } else {
            Toast.makeText(this.context, "No internet Connection, Try again", Toast.LENGTH_LONG).show();
        }
    }

    private void verifySmsCode(String smsCode, int txnId){
        String URL = "http://tellerpoint.herokuapp.com/index.php/api/purchase/validate";

        final ProgressDialog progressDialog = new ProgressDialog(PaymentFormActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("transaction_id", txnId);
        jsonObject.addProperty("smsCode", smsCode);
        if (isConnected) {
            Ion.with(context)
                    .load("POST", URL)
                    .setJsonObjectBody(jsonObject)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            progressDialog.dismiss();
//                            Log.e("result", result.toString());
                            Intent i = new Intent(PaymentFormActivity.this, PaymentSuccessfulActivity.class);
                            startActivity(i);
                            PaymentFormActivity.this.finish();

                        }
                    });
        } else {
            Toast.makeText(this.context, "No internet Connection, Try again", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}