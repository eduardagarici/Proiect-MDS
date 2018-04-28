package com.example.user.mdsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class SpecialMentions extends AppCompatActivity {

    private Animation fade_in,fade_out;
    private ViewFlipper viewFlipper;
    private ListView lvProduct;
    private ProductListAdapter adapter;
    private List<Product> mProductList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_mentions);
        setSlideShow();
        setListView();
    }

    public void setSlideShow(){
       viewFlipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper);
        fade_in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        viewFlipper.setInAnimation(fade_in);
        viewFlipper.setOutAnimation(fade_out);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();
    }

    public void setListView(){
        lvProduct=findViewById(R.id.ListView);
        mProductList=new ArrayList<>();
        mProductList.add(new Product(1,R.drawable.burger,"burgerChicken",2,10));
        mProductList.add(new Product(2,R.drawable.drink,"drinkCocaCola",2,10));
        mProductList.add(new Product(1,R.drawable.burger,"burgerChicken",2,10));
        mProductList.add(new Product(2,R.drawable.drink,"drinkCocaCola",2,10));
        mProductList.add(new Product(1,R.drawable.burger,"burgerChicken",2,10));
        mProductList.add(new Product(2,R.drawable.drink,"drinkCocaCola",2,10));
        mProductList.add(new Product(1,R.drawable.burger,"burgerChicken",2,10));
        mProductList.add(new Product(2,R.drawable.drink,"drinkCocaCola",2,10));

        adapter=new ProductListAdapter(getApplicationContext(),mProductList);
        lvProduct.setAdapter(adapter);

        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"it works",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
