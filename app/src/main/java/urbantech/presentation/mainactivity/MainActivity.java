package urbantech.presentation.mainactivity;

import static urbantech.business.GlobalState.compare;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import urbantech.application.Login;
import urbantech.application.Main;
import urbantech.application.Services;
import urbantech.business.AccessItems;
import urbantech.business.CompareItems;
import urbantech.business.SearchItems;
import urbantech.objects.Item;
import urbantech.persistence.ItemPersistence;
import urbantech.presentation.ProductActivity;
import urbantech.presentation.account.AccountActivity;
import urbantech.presentation.account.LoginActivity;
import urbantech.presentation.cart.CartActivity;
import urbantech.presentation.category.CategoryActivity;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemListener {
    private final boolean[] checkboxStates = new boolean[16];
    List<Item> items;
    ItemPersistence itemPersistence;
    final String all = "";
    private ArrayList<Item> search_items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout oldLayout = findViewById(R.id.activity_main);
        View newLayout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.button_layout, oldLayout, false);
        oldLayout.addView(newLayout);
        copyDatabaseToDevice();

        if (compare == null)//the object containing the items to compare
            compare = new CompareItems();//instantiate compare

        RecyclerView recyclerView = findViewById(R.id.recyclerviewMain);

        //items to display in the recycler view
        if (getIntent() != null && getIntent().hasExtra("selected_category")) {//if there has been a category passed
            items = getIntent().getParcelableArrayListExtra("selected_category");//get the items in that category
            Toast.makeText(getApplicationContext(), "CATEGORY SELECTED, PRESS HOME AND" + "\n SEARCH TO SEE THE FULL LIST VIEW", Toast.LENGTH_LONG).show();
        } else {
            itemPersistence = Services.getItemPersistence();
            itemPersistence.setCategory(all);
            items = AccessItems.getInstance().getItems();//get all items
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemAdapter(this, items, this));

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

        ImageButton viewCartButton = newLayout.findViewById(R.id.cartButton);
        viewCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(intent);
        });

        ImageButton viewCategoryButton = newLayout.findViewById(R.id.categoryButton);
        viewCategoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
            startActivity(intent);
        });
        initiateSearchWidget();
    }

    private void initiateSearchWidget() {
        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //clear search list
                search_items.clear();
                //search using business layer class SearchItems
                SearchItems search = new SearchItems(Services.getItemPersistence());
                search.setSearchName(newText);
                search_items = (ArrayList<Item>) search.getSearchResult();
                //adapters
                ItemAdapter adapter = new ItemAdapter(getApplicationContext(), search_items, position -> {
                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                    intent.putExtra("selected_item", search_items.get(position));
                    startActivity(intent);
                });
                RecyclerView recyclerView = findViewById(R.id.recyclerviewMain);
                recyclerView.setAdapter(adapter);
                return true;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("selected_item", items.get(position));
        startActivity(intent);
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            if (Main.getDBPathName().equals("SC"))
                Main.setDBPathName(dataDirectory.toString() + "/" + Main.getDBPathName());

        } catch (final IOException ioe) {
            //Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}
