package com.example.user.mdsapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.user.mdsapplication.utils.Constants;

import java.util.List;

    public class ProductListAdapter extends BaseAdapter {

        private Activity mActivity;
        private Context mContext;
        private List<Product> mProducts;

        public ProductListAdapter(Context mContext,Activity mActivity ,List<Product> mProducts) {
            this.mActivity=mActivity;
            this.mContext = mContext;
            this.mProducts = mProducts;
        }

        @Override
        public int getCount() {
            return mProducts.size();
        }

        @Override
        public Object getItem(int position) {
            return mProducts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v=View.inflate(mContext,R.layout.list_product_item,null);
            final ImageView imgProduct=(ImageView)v.findViewById(R.id.imageProduct);
            final int drawableResourceId = mContext.getResources().getIdentifier(mProducts.get(position).getResourceImage(),"drawable", mContext.getPackageName());
            imgProduct.setImageResource(drawableResourceId);
            imgProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog builder = new Dialog(mActivity);
                    builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    builder.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            //nothing;
                        }
                    });
                    ImageView imageView = new ImageView(mActivity);
                    imageView.setImageResource(drawableResourceId);
                    builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                            //Constants.DIALOG_DIMEN,Constants.DIALOG_DIMEN));
                    builder.show();
                    Window window = builder.getWindow();
                    window.setLayout(Constants.DIALOG_DIMEN,Constants.DIALOG_DIMEN);
                    //window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                }
            });
            TextView textProduct=(TextView)v.findViewById(R.id.textProduct);
            textProduct.setText(mProducts.get(position).getName());
            textProduct.setTextColor(mContext.getResources().getColor(R.color.colorProduct));

            final TextView quantityProduct=(TextView) v.findViewById(R.id.quantityProduct);
            quantityProduct.setText(String.valueOf(mProducts.get(position).getQuantity()));
            quantityProduct.setTextColor(mContext.getResources().getColor(R.color.colorProduct));

            final TextView priceProduct=(TextView) v.findViewById(R.id.priceProduct);
            priceProduct.setText(String.valueOf(mProducts.get(position).getPricePerUnit()*mProducts.get(position).getQuantity()) + " lei");
            priceProduct.setTextColor(mContext.getResources().getColor(R.color.colorProduct));

            Button minusProduct=(Button) v.findViewById(R.id.minusProduct);

            minusProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity=mProducts.get(position).getQuantity();
                    if(quantity>0) {
                        mProducts.get(position).setQuantity(quantity - 1);
                        quantityProduct.setText(String.valueOf(mProducts.get(position).getQuantity()));
                        priceProduct.setText(String.valueOf(mProducts.get(position).getPricePerUnit()*mProducts.get(position).getQuantity()) + " lei");
                    }
                }
            });
            Button plusProduct=(Button) v.findViewById(R.id.plusProduct);

            plusProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity=mProducts.get(position).getQuantity();
                    if(quantity<10) {
                        mProducts.get(position).setQuantity(quantity + 1);
                        quantityProduct.setText(String.valueOf(mProducts.get(position).getQuantity()));
                        priceProduct.setText(String.valueOf(mProducts.get(position).getPricePerUnit()*mProducts.get(position).getQuantity()) + " lei");
                    }
                }
            });

            return v;
        }
    }