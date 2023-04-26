package urbantech.presentation.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.urbantech.R;

import java.util.ArrayList;
import java.util.List;

import urbantech.application.Login;
import urbantech.application.Main;
import urbantech.business.AccountManager;
import urbantech.business.CardPayment;
import urbantech.business.Receipt;
import urbantech.business.ValidateDeliveryAddress;
import urbantech.objects.Item;
import urbantech.objects.ItemBuilder;
import urbantech.presentation.cart.CartActivity;
import urbantech.presentation.category.CategoryActivity;
import urbantech.presentation.mainactivity.MainActivity;


public class AccountActivity extends AppCompatActivity {

    CardView cardCard, addressCard, receiptCard;
    AccountManager account;
    TextView card, address, receipt, accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        LinearLayout oldLayout = findViewById(R.id.activity_account);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);

        accountName = super.findViewById(R.id.accountName);
        account = new AccountManager(Main.getDBPathName());
        cardCard = super.findViewById(R.id.cardInfoCard);
        addressCard = super.findViewById(R.id.addressInfoCard);
        receiptCard = super.findViewById(R.id.receiptInfoCard);
        card = cardCard.findViewById(R.id.cardInformation);
        address = addressCard.findViewById(R.id.addressInformation);
        receipt = receiptCard.findViewById(R.id.receiptInformation);

        String placeholder = "WELCOME " + account.getUserName() + "!";
        accountName.setText(placeholder);

        if (!account.getCards().isEmpty())
            this.card.setText(account.getCards().toString());
        else
            this.card.setVisibility(View.INVISIBLE);

        if (account.getAddress() != null)
            this.address.setText(account.getAddress().toString());
        else
            this.address.setVisibility(View.INVISIBLE);

        if (!account.getReceipts().isEmpty())
            this.receipt.setText(account.getReceipts().toString());
        else
            this.receipt.setVisibility(View.INVISIBLE);

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

    //@Override
    public void logoutOnClick(View view) {
        account.logOut();
        Toast.makeText(this.getApplicationContext(), "Logged out!", Toast.LENGTH_LONG).show();//display message
        Intent intent = new Intent(this, MainActivity.class);//go back to main
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
