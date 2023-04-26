package urbantech.presentation.compare;

import static urbantech.business.GlobalState.compare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.urbantech.R;

import java.util.Locale;

import urbantech.application.Login;
import urbantech.presentation.account.AccountActivity;
import urbantech.presentation.account.LoginActivity;
import urbantech.presentation.cart.CartActivity;
import urbantech.presentation.category.CategoryActivity;
import urbantech.presentation.mainactivity.MainActivity;


public class CompareActivity extends AppCompatActivity {

    CardView cardView1, cardView2;
    ImageView imageView1, imageView2;
    TextView priceView1, priceView2, priceCompare, stockCompare, priceAfterDiscountCompare;
    TextView specification1, specification2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        cardView1 = super.findViewById(R.id.cardView1);
        cardView2 = super.findViewById(R.id.cardView2);
        imageView1 = cardView1.findViewById(R.id.image1);
        imageView2 = cardView2.findViewById(R.id.image2);
        priceView1 = cardView1.findViewById(R.id.price1);
        priceView2 = cardView2.findViewById(R.id.price2);
        priceCompare = super.findViewById(R.id.priceCompare);
        stockCompare = super.findViewById(R.id.stockCompare);
        priceAfterDiscountCompare = super.findViewById(R.id.priceCompareDiscount);
        specification1 = cardView1.findViewById(R.id.specification1);
        specification2 = cardView2.findViewById(R.id.specification2);

        this.imageView1.setImageResource(compare.getItem(0).getImage());//setting images
        this.imageView2.setImageResource(compare.getItem(1).getImage());

        String price = String.format(Locale.ENGLISH,"Price: %.2f", compare.getItem(0).getPrice());//set first price
        this.priceView1.setText(price);
        String spec1 = String.format(Locale.ENGLISH,"Model Name: %s \nBrand: %s \n Storage: %s \nRAM: %s \nProcessor: %s \nGraphic Card: %s \n" +
                        "Screen Size: %s \nStock: %d",
                compare.getItem(0).getModelName(), compare.getItem(0).getBrand(), compare.getItem(0).getStorage(), compare.getItem(0).getRAM(), compare.getItem(0).getProcessor(), compare.getItem(0).getGraphicCard(), compare.getItem(0).getScreenSize(), compare.getItem(0).getStock());
        this.specification1.setText(spec1);

        price = String.format(Locale.ENGLISH,"Price: %.2f", compare.getItem(1).getPrice());
        this.priceView2.setText(price);
        String spec2 = String.format(Locale.ENGLISH,"Model Name: %s \nBrand: %s \n Storage: %s \nRAM: %s \nProcessor: %s \nGraphic Card: %s \n" +
                        "Screen Size: %s \nStock: %d",
                compare.getItem(1).getModelName(), compare.getItem(1).getBrand(), compare.getItem(1).getStorage(), compare.getItem(1).getRAM(), compare.getItem(1).getProcessor(), compare.getItem(1).getGraphicCard(), compare.getItem(1).getScreenSize(), compare.getItem(1).getStock());
        this.specification2.setText(spec2);

        String lowPrice = String.format("The item " + compare.comparePrice().getModelName() + " has a lower price by: $%.2f", Math.abs(compare.getItem(0).getPrice() - compare.getItem(1).getPrice()));
        priceCompare.setText(lowPrice);

        String lowPriceAfterDis = String.format("The item " + compare.comparePriceAfterDiscount().getModelName() + " has a lower price after discount");
        priceAfterDiscountCompare.setText(lowPriceAfterDis);

        String highStock;
        if (Math.abs(compare.getItem(0).getStock() - compare.getItem(1).getStock()) == 0) {
            highStock = "Both items have same quantity of stock!";
        } else {
            highStock = "The item " + compare.compareStock().getModelName() + " has a higher quantity available in stock by: " + Math.abs(compare.getItem(0).getStock() - compare.getItem(1).getStock()) + " units";
        }
        stockCompare.setText(highStock);

        LinearLayout oldLayout = findViewById(R.id.activity_compare);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        LinearLayout outerLayout = findViewById(R.id.outerLayout);
        outerLayout.addView(newLayout);

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
