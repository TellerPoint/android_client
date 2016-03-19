package com.tellerpoint.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tellerpoint.app.R;
import com.tellerpoint.app.activity.PaymentFormActivity;
import com.tellerpoint.app.model.Product;

import java.util.List;

//import com.squareup.picasso.Picasso;

/**
 * Created by eit on 8/29/15.
 */
public class ProductItemAdaptor extends ArrayAdapter<Product> {
    private static LayoutInflater inflater = null;
    private List<Product> productItemList;
//    private User mUser;
    Context mContext;

    public ProductItemAdaptor(Context context, int resource) {
        super(context, resource);
    }



    public ProductItemAdaptor(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        mContext = context;

        this.productItemList = objects;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder holder;
        final Product productItem = productItemList.get(position);

        if (convertView == null){
            view = inflater.inflate(R.layout.product_item, null);
            holder = new ViewHolder();
            holder.productImageView = (ImageView) view.findViewById(R.id.productImage);
            holder.productName = (TextView) view.findViewById(R.id.product_name);
            holder.productDesc = (TextView) view.findViewById(R.id.product_desc);
            holder.productAmount = (TextView) view.findViewById(R.id.product_amount);
            holder.payButton = (Button) view.findViewById(R.id.btn_product_pay);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

            holder.productName.setText(productItem.getProduct_name());
            holder.productDesc.setText(productItem.getProduct_desc());
            holder.productAmount.setText("  " + productItem.getProduct_amount() + " GHC");

            Picasso.with(mContext).load(Uri.parse(productItem.getProduct_img()))
                    .noFade()
                    .fit()
                    .placeholder(R.drawable.ic_action_search)
                    .into(holder.productImageView);

            holder.payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String productId = productItem.getId();

                    String merchantId = productItem.getMerchant_id();

                    String productName = productItem.getProduct_name();
                    String productDesc = productItem.getProduct_desc();
                    String productAmount = productItem.getProduct_amount();
                    String productCode = productItem.getProduct_code();
                    String productImg = productItem.getProduct_img();
                    String merchantName = productItem.getMerchant_name();


                    Intent intent = new Intent(v.getContext(), PaymentFormActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("product_id", productId);
                    intent.putExtra("merchant_id", merchantId);
                    intent.putExtra("product_name", productName);
                    intent.putExtra("product_amount", productAmount);
                    intent.putExtra("product_desc", productDesc);
                    intent.putExtra("product_code", productCode);
                    intent.putExtra("product_img", productImg);
                    intent.putExtra("merchant_name", merchantName);

                    intent.putExtra("qty", "1");

                    v.getContext().startActivity(intent);
                }
            });

            return view;
    }

    public static class ViewHolder{
        public ImageView productImageView;
        public TextView productName;
//        public TextView productId;
        public TextView productAmount;
        public TextView productDesc;
        public Button payButton;

    }
}
