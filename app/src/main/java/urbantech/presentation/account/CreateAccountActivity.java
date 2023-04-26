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


public class CreateAccountActivity extends AppCompatActivity {
    AccountManager account;
    TextView username, password, passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount_layout);
        LinearLayout oldLayout = findViewById(R.id.createaccount_layout);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);

        account = new AccountManager(Main.getDBPathName());
        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.et_confirm_password);

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
    public void setUpAccountOnClick(View view) {
        if (password.getText().toString().equals(passwordConfirm.getText().toString())) {//if passwords match
            account.CreateAcc(username.getText().toString(), passwordConfirm.getText().toString());
            if (account.isCreateSuccess()) {//if the create is successful
                Toast.makeText(this.getApplicationContext(), "Created account!", Toast.LENGTH_LONG).show();//display message
                Intent intent = new Intent(this, LoginActivity.class);//go back to login
                startActivity(intent);
            } else
                Toast.makeText(this.getApplicationContext(), "Account email already exists!", Toast.LENGTH_LONG).show();//display message
        } else//if not
            Toast.makeText(this.getApplicationContext(), "Passwords must match!", Toast.LENGTH_LONG).show();//display message
    }

}
