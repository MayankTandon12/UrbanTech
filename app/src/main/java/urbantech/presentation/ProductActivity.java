package urbantech.presentation;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import java.util.List;

import urbantech.application.Login;
import urbantech.business.AccessItems;
import urbantech.business.CartManager;
import urbantech.objects.CartItem;
import urbantech.objects.Item;
import urbantech.presentation.account.AccountActivity;
import urbantech.presentation.account.LoginActivity;
import urbantech.presentation.cart.CartActivity;
import urbantech.presentation.category.CategoryActivity;
import urbantech.presentation.category.CategoryAdapter;
import urbantech.presentation.mainactivity.ItemAdapter;
import urbantech.presentation.mainactivity.MainActivity;

public class ProductActivity extends AppCompatActivity implements ItemAdapter.OnItemListener {
    private static final String TAG = "ItemActivity";
    private Item item;
    List<Item> recommended;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        LinearLayout oldLayout = findViewById(R.id.activity_product);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);

        if (getIntent().hasExtra("selected_item")) {
            item = getIntent().getParcelableExtra("selected_item");

            Resources res = getResources();
            try {
                Drawable dr = ResourcesCompat.getDrawable(res, item.getImage(), null);
                ImageView iv = findViewById(R.id.imageView);
                iv.setImageDrawable(dr);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            TextView tx = findViewById(R.id.productTextView);
            String text = "\n" + item.getBrand() + " " + item.getModelName() + "\n $" + item.getPrice() + "\n Stock: " + item.getStock();
            tx.setText(item.toString());
            //tx.setText(text);

            ImageButton homeButton = newLayout.findViewById(R.id.homeButton);
            homeButton.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });

            ImageButton accountButton = newLayout.findViewById(R.id.accountButton);
            accountButton.setOnClickListener(v -> {
                Login.getLoginID();//get the current ID of login
                Intent intent;
                if (Login.getAccount() == null) //if an account is not logged in
                    intent = new Intent(getApplicationContext(), LoginActivity.class);//go to login page

                else //if one is logged in
                    intent = new Intent(getApplicationContext(), AccountActivity.class);//go to account page
                startActivity(intent);
            });

            ImageButton viewCategoryButton = newLayout.findViewById(R.id.categoryButton);
            viewCategoryButton.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
            });

            ImageButton viewCartButton = newLayout.findViewById(R.id.cartButton);
            viewCartButton.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            });

            recommended= AccessItems.getInstance().getRecommendedItems(item.getItemID());
            for(Item i : recommended){
                System.out.println(i.getModelName());
            }
            RecyclerView recyclerView = findViewById(R.id.recyclerViewRecommended);//setting the view
            RecyclerView.LayoutManager myLayoutManager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(myLayoutManager);
            ItemAdapter adapter = new ItemAdapter(getApplicationContext(), recommended, this);
            recyclerView.setAdapter(adapter);
        }
    }

    public void addToCartOnClick(View v) {
        if (item != null) {
            CartManager.getInstance().addItemToCart(new CartItem(item, 1));

            Log.d(TAG, "Added " + item.getModelName() + " To Cart (Qty in cart: " + CartManager.getInstance().getItemQuantity(item.getItemID()) + ", Qty in stock: " + item.getStock() + ")");
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("selected_item", recommended.get(position));
        startActivity(intent);
    }
}
