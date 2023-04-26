package urbantech.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urbantech.R;

import urbantech.application.Login;
import urbantech.presentation.account.AccountActivity;
import urbantech.presentation.account.LoginActivity;
import urbantech.presentation.cart.CartActivity;
import urbantech.presentation.category.CategoryActivity;
import urbantech.presentation.mainactivity.MainActivity;


public class PlacedOrder extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_placed);
        LinearLayout oldLayout = findViewById(R.id.order_placed);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);

        String displayText = getIntent().getStringExtra("displayText");
        boolean correctInfo = getIntent().getBooleanExtra("info", false);

        TextView textView = findViewById(R.id.customer_info);
        textView.setText(displayText);

        ImageView checkmark = findViewById(R.id.checkmark);
        TextView confirmationText = findViewById(R.id.confirmation_text);
        if (!correctInfo) {//if the correct info was not entered
            checkmark.setVisibility(View.INVISIBLE);//hide the confirmations
            confirmationText.setVisibility(View.INVISIBLE);
        } else {//if correct info
            checkmark.setVisibility(View.VISIBLE);//show the confirmations
            confirmationText.setVisibility(View.VISIBLE);
        }

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


}
