package urbantech.presentation.category;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import java.util.ArrayList;
import java.util.List;

import urbantech.application.Login;
import urbantech.application.Services;
import urbantech.objects.Category;
import urbantech.persistence.ItemPersistence;
import urbantech.presentation.account.AccountActivity;
import urbantech.presentation.account.LoginActivity;
import urbantech.presentation.cart.CartActivity;
import urbantech.presentation.mainactivity.MainActivity;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryListener {

    List<Category> categories = new ArrayList<>();
    ItemPersistence itemPersistence;
    final String all = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        LinearLayout oldLayout = findViewById(R.id.activity_category);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);
        itemPersistence = Services.getItemPersistence();

        //get the list of categories
        categories = itemPersistence.getCategories();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCategory);//setting the view
        RecyclerView.LayoutManager myLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(myLayoutManager);
        CategoryAdapter adapter = new CategoryAdapter(getApplicationContext(), categories, this);
        recyclerView.setAdapter(adapter);

        ImageButton homeButton = newLayout.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            itemPersistence.setCategory(all);
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

    @Override
    public void onCategoryClick(int position) {
        //select the category, go to that category
        Intent intent = new Intent(this, MainActivity.class);
        itemPersistence.setCategory(categories.get(position).getName());
        intent.putParcelableArrayListExtra("selected_category", (ArrayList<? extends Parcelable>) categories.get(position).getItems());//send the selected category to main
        System.out.println("passing intent of " + categories.get(position).getName());//debugging
        startActivity(intent);//start mainactivity
    }

}
