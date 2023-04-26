package urbantech.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import urbantech.application.Login;
import urbantech.application.Main;
import urbantech.business.AccountManager;
import urbantech.business.CardPayment;
import urbantech.business.CartManager;
import urbantech.business.Receipt;
import urbantech.business.ReceiptBuilder;
import urbantech.business.ValidateDeliveryAddress;
import urbantech.objects.CartItem;
import urbantech.objects.Item;
import urbantech.presentation.account.AccountActivity;
import urbantech.presentation.account.LoginActivity;
import urbantech.presentation.cart.CartActivity;
import urbantech.presentation.category.CategoryActivity;
import urbantech.presentation.mainactivity.ItemAdapter;
import urbantech.presentation.mainactivity.MainActivity;

public class CheckoutActivity extends AppCompatActivity implements ItemAdapter.OnItemListener {
    List<Item> items;
    String total;
    ValidateDeliveryAddress delivery;
    CardPayment payment;
    Receipt receipt;
    AccountManager account;
    boolean validPaymentMethod = false;
    boolean validShippingAddress = false;
    String streetName, city, postalCode, province, country, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        LinearLayout oldLayout = findViewById(R.id.activity_checkout);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);

        RecyclerView recyclerView = findViewById(R.id.cart_items);
        items = new ArrayList<>();
        List<CartItem> cartItems;
        cartItems = CartManager.getInstance().getCartList(); //temp
        for (CartItem c : cartItems)
            items.add(c.getItem());

        TextView totalText = findViewById(R.id.totalCheckOut);
        account = new AccountManager(Main.getDBPathName());

        if (CartManager.getInstance().getTotalPrice() != 0) {//if there actually is a price and actually are items in cart
            total = "Total Price: $";
            total += String.format(Locale.ENGLISH, "%.2f", CartManager.getInstance().getTotalPrice());
        }
        totalText.setText(total);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemAdapter(getApplicationContext(), items, this));

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

    public void onCheckoutClick(View view) {
        EditText shippingStreetName = findViewById(R.id.shipping_street_name);
        EditText shippingCity = findViewById(R.id.shipping_city);
        EditText shippingPostalCode = findViewById(R.id.shipping_postal_code);
        EditText shippingProvince = findViewById(R.id.shipping_province);
        EditText shippingCountry = findViewById(R.id.shipping_country);
        EditText emailAddress = findViewById(R.id.email_address);

        EditText cardNumber = findViewById(R.id.card_number);
        EditText expiryDate = findViewById(R.id.expiry_date);
        EditText cvv = findViewById(R.id.cvv);

        streetName = shippingStreetName.getText().toString();
        city = shippingCity.getText().toString();
        postalCode = shippingPostalCode.getText().toString();
        province = shippingProvince.getText().toString();
        country = shippingCountry.getText().toString();
        email = emailAddress.getText().toString();

        delivery = new ValidateDeliveryAddress(streetName, city, province, country, postalCode);
        validShippingAddress = delivery.checkValidAddress(streetName, city, province, country, postalCode);

        String cardNumberStr = cardNumber.getText().toString();
        String expiryDateStr = expiryDate.getText().toString();
        String cvvStr = cvv.getText().toString();
        payment = new CardPayment(cardNumberStr, cvvStr, expiryDateStr);
        validPaymentMethod = (payment.validCardNum() && payment.validExpireDate() && payment.validCVV());
        checkout(delivery, payment, email);
    }

    @Override
    public void onItemClick(int position) {

    }

    public void onOneClickCheckout(View view) {
        Login.getLoginID();
        if (Login.getAccount() == null)
            Toast.makeText(this.getApplicationContext(), "You must be logged in to do that!", Toast.LENGTH_LONG).show();//display message
        else if (account.getCards() == null || account.getCards().isEmpty())
            Toast.makeText(this.getApplicationContext(), "You need a saved card to use this!", Toast.LENGTH_LONG).show();//display message
        else if (account.getAddress() == null)
            Toast.makeText(this.getApplicationContext(), "You need a saved address to do that!", Toast.LENGTH_LONG).show();//display message
        else {
            streetName = account.getAddress().getAddressLine();
            city = account.getAddress().getCity();
            postalCode = account.getAddress().getPostalCode();
            province = account.getAddress().getProvince();
            country = account.getAddress().getCountry();
            email = account.getUserName();

            delivery = account.getAddress();
            validShippingAddress = delivery.checkValidAddress(delivery.getAddressLine(), delivery.getCity(),
                    delivery.getProvince(), delivery.getCountry(), delivery.getPostalCode());

            payment = account.getCards().get(0);
            validPaymentMethod = (payment.validCardNum() && payment.validExpireDate() && payment.validCVV());
            checkout(delivery, payment, email);
        }
    }

    private void checkout(ValidateDeliveryAddress delivery, CardPayment payment, String email) {
        //placeholder orderNumber
        int orderNumber;
        orderNumber = (int) (Math.random() * 1000000);

        boolean correctInfo = false;
        String displayText = "";//initialise displayString
        if (validShippingAddress && validPaymentMethod) {
            displayText = "\n" + "Shipping Address: " + delivery.getAddressLine() + " " + delivery.getCity() + " "
                    + delivery.getPostalCode() + " " + delivery.getProvince() + " " + delivery.getCountry() + " " + "\n" +
                    "Payment Information:" + payment.getCardNum() + " " +
                    payment.getExpireDate() + " " + payment.getCvv() + " " + "\n" + total;
            ReceiptBuilder builder = new ReceiptBuilder();
            receipt = builder.itemsString(items).name(email).orderNumberString(orderNumber).total(CartManager
                            .getInstance().getTotalPrice()).amountItemsString(CartManager.getInstance().getCartList().size())
                    .items(items).orderNumber(orderNumber).amountItems(items.size()).build();

            Login.getLoginID();
            if (Login.getAccount() != null)//if an account is logged in
                account.addReceipts(receipt);//add receipt to the account

            displayText += "\n" + receipt.toString();
            CartManager.getInstance().checkout(); //clears cart, updates stock
            correctInfo = true;
        } else {
            displayText += "\n" + "Wrong Information Entered, Enter correct information again!";
        }

        Intent intent = new Intent(this, PlacedOrder.class);
        intent.putExtra("displayText", displayText);
        intent.putExtra("info", correctInfo);
        startActivity(intent);
    }

    public void onSaveClick(View view) {
        EditText shippingStreetName = findViewById(R.id.shipping_street_name);
        EditText shippingCity = findViewById(R.id.shipping_city);
        EditText shippingPostalCode = findViewById(R.id.shipping_postal_code);
        EditText shippingProvince = findViewById(R.id.shipping_province);
        EditText shippingCountry = findViewById(R.id.shipping_country);

        EditText cardNumber = findViewById(R.id.card_number);
        EditText expiryDate = findViewById(R.id.expiry_date);
        EditText cvv = findViewById(R.id.cvv);

        String streetName = shippingStreetName.getText().toString();
        String city = shippingCity.getText().toString();
        String postalCode = shippingPostalCode.getText().toString();
        String province = shippingProvince.getText().toString();
        String country = shippingCountry.getText().toString();
        delivery = new ValidateDeliveryAddress(streetName, city, province, country, postalCode);
        validShippingAddress = delivery.checkValidAddress(streetName, city, province, country, postalCode);

        String cardNumberStr = cardNumber.getText().toString();
        String expiryDateStr = expiryDate.getText().toString();
        String cvvStr = cvv.getText().toString();
        payment = new CardPayment(cardNumberStr, cvvStr, expiryDateStr);
        validPaymentMethod = (payment.validCardNum() && payment.validExpireDate() && payment.validCVV());

        //add card and address to account that's logged in
        Login.getLoginID();//get the current ID of login
        if (Login.getAccount() != null) {//if logged into an account
            account.addCard(payment);
            account.addAddress(delivery);
        }
        else
            Toast.makeText(this.getApplicationContext(), "You must be logged in to do that!", Toast.LENGTH_LONG).show();//display message
    }
}
