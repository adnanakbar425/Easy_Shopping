package com.android.shoppingzoo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.shoppingzoo.Adapter.SliderAdapter;
import com.android.shoppingzoo.Model.Order;
import com.android.shoppingzoo.Model.Product;
import com.android.shoppingzoo.Model.Utils;
import com.android.shoppingzoo.R;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class ProductDetailsActivity extends AppCompatActivity {

    private CardView addToCartBtn;
    private TextView plusBTn,minusBtn,quantityTV;
    private TextView productName,productDescription,price;
    Product product;

    int quantity=1;

    SliderView imageSlider;
    ArrayList<String> imagesList;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initAll();
        ClickListeners();

        product= (Product) getIntent().getSerializableExtra("product");


        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        price.setText("$"+product.getPrice());

        if(product.getImage1()!=null){
            imagesList.add(product.getImage1());
        }
        if(product.getImage2()!=null){
            imagesList.add(product.getImage2());
        }
        if(product.getImage3()!=null){
            imagesList.add(product.getImage3());
        }

        Set_SliderView(imagesList, imageSlider);

    }

    public void Set_SliderView(ArrayList<String> images, SliderView sliderView) {
        SliderAdapter sliderAdapter = new SliderAdapter();
        sliderAdapter.renewItems(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.startAutoCycle();

    }

    private void ClickListeners() {
        plusBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity+=1;
                quantityTV.setText(String.valueOf(quantity));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity>1){
                    quantity-=1;
                    quantityTV.setText(String.valueOf(quantity));
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInCart=false;
                for(int i=0;i<order.getCartProductList().size();i++){
                    if(product.getProductId().equals(order.getCartProductList().get(i).getProductId())){
                        isInCart=true;
                        break;
                    }
                }
                if(!isInCart){
                    product.setQuantityInCart(quantity);
                    order.addProduct(product);
                    Log.d("testorder",order.getTotalPrice()+ "");
                    Paper.book().delete("order");
                    Paper.book().write("order",order);
                    Toast.makeText(ProductDetailsActivity.this,"Added to cart",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(ProductDetailsActivity.this,"Already in cart",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initAll() {
        imagesList=new ArrayList<>();
        addToCartBtn=findViewById(R.id.add_to_cart_btn);
        imageSlider=findViewById(R.id.slider_view);
        plusBTn=findViewById(R.id.plus_btn);
        minusBtn=findViewById(R.id.minus_btn);
        quantityTV=findViewById(R.id.quantity_tv);
        productName=findViewById(R.id.product_name);
        price=findViewById(R.id.product_price);
        productDescription=findViewById(R.id.product_description);

        product=new Product();

        order=new Order();

        if(Paper.book().read("order")!=null){
            order=Paper.book().read("order");
        }

    }

    public void goBack(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Paper.book().read("order")!=null){
            order=Paper.book().read("order");
        }
    }
}