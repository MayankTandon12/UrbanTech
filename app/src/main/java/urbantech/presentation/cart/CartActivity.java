package urbantech.presentation.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import java.util.List;
import java.util.Locale;

import urbantech.application.Login;
import urbantech.business.CartManager;
import urbantech.objects.CartItem;
import urbantech.presentation.CheckoutActivity;
import urbantech.presentation.account.AccountActivity;
import urbantech.presentation.account.LoginActivity;
import urbantech.presentation.category.CategoryActivity;
import urbantech.presentation.mainactivity.MainActivity;

public class CartActivity extends AppCompatActivity implements CartItemAdapter.OnItemListener {
    List<CartItem> cartItems;
    Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        LinearLayout oldLayout = findViewById(R.id.activity_cart);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);

        RecyclerView recyclerView = findViewById(R.id.recyclerviewCart);
        TextView totalText = findViewById(R.id.totalText);

        checkoutButton = findViewById(R.id.checkoutButton);//set checkoutButton

        //total cost stuff
        cartItems = CartManager.getInstance().getCartList();

        if (cartItems.isEmpty())
            checkoutButton.setVisibility(View.INVISIBLE);//hide checkout button if nothing in cart

        String total = "Nothing in cart!";//if nothing in cart
        if (CartManager.getInstance().getTotalPrice() != 0) {//if there actually is a price and actually are items in cart
            total = "Total Price: $";
            total += String.format(Locale.ENGLISH, "%.2f", CartManager.getInstance().getTotalPrice());
        }
        totalText.setText(total);//display either Nothing in cart or the actual total

        //stuff in cart. add to recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CartItemAdapter(getApplicationContext(), totalText, cartItems, this));

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
    }

    public void checkOutOnClick(View view) {
        if (!CartManager.getInstance().getCartList().isEmpty()) {
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        } else {
            System.out.println("Error: Cart is empty, cannot proceed to checkout.");
        }
    }

    @Override
    public void onItemClick(int position) {
    }
}
