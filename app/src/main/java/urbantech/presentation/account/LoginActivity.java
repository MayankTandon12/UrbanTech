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

import com.example.urbantech.R;

import urbantech.application.Login;
import urbantech.application.Main;
import urbantech.business.AccountManager;
import urbantech.presentation.cart.CartActivity;
import urbantech.presentation.category.CategoryActivity;
import urbantech.presentation.mainactivity.MainActivity;


public class LoginActivity extends AppCompatActivity {

    AccountManager account;
    TextView username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);
        LinearLayout oldLayout = findViewById(R.id.loginlayout);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);

        account = new AccountManager(Main.getDBPathName());
        username = findViewById(R.id.email);
        password = findViewById(R.id.password);

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

    public void loginOnClick(View view) {
        account.loginAcc(Main.getDBPathName(), username.getText().toString(), password.getText().toString());
        if (account.isLoginSuccess()) {
            Toast.makeText(this.getApplicationContext(), "Logged in!", Toast.LENGTH_LONG).show();//display message
            Intent intent = new Intent(this, MainActivity.class);//go back to main
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else
            Toast.makeText(this.getApplicationContext(), "Incorrect email/password!", Toast.LENGTH_LONG).show();//display message

    }

    public void createAccountOnClick(View view) {//go to the create account screen
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}
