package com.example.user.mdsapplication;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

    public class ProductListAdapter extends BaseAdapter {

        private Context mContext;
        private List<Product> mProducts;

        public ProductListAdapter(Context mContext, List<Product> mProducts) {
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
            //ViewGroup.LayoutParams params=v.getLayoutParams();
            //params.height=40;
            //v.setLayoutParams(params);

            ImageView imgProduct=(ImageView)v.findViewById(R.id.imageProduct);
            imgProduct.setImageResource(mProducts.get(position).getIdImage());

            TextView textProduct=(TextView)v.findViewById(R.id.textProduct);
            textProduct.setText(mProducts.get(position).getName());

            final TextView quantityProduct=(TextView) v.findViewById(R.id.quantityProduct);
            quantityProduct.setText(String.valueOf(mProducts.get(position).getQuantity()));

            final TextView priceProduct=(TextView) v.findViewById(R.id.priceProduct);
            priceProduct.setText(String.valueOf(mProducts.get(position).getPrice()*mProducts.get(position).getQuantity()) + " lei");

            Button minusProduct=(Button) v.findViewById(R.id.minusProduct);
            minusProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity=mProducts.get(position).getQuantity();
                    if(quantity>0) {
                        mProducts.get(position).setQuantity(quantity - 1);
                        quantityProduct.setText(String.valueOf(mProducts.get(position).getQuantity()));
                        priceProduct.setText(String.valueOf(mProducts.get(position).getPrice()*mProducts.get(position).getQuantity()) + " lei");
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
                        priceProduct.setText(String.valueOf(mProducts.get(position).getPrice()*mProducts.get(position).getQuantity()) + " lei");
                    }
                }
            });

            return v;
        }
    }