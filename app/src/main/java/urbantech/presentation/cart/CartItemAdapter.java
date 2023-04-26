package urbantech.presentation.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import urbantech.business.CartManager;
import urbantech.objects.CartItem;
import urbantech.objects.Item;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemViewHolder> {

    private final OnItemListener mOnItemListener;
    final Context context;
    DecimalFormat df = new DecimalFormat("#.##");
    final List<CartItem> cartItems;
    //CartManager cart;
    final TextView totalPriceView;

    public CartItemAdapter(Context context, TextView totalPriceView, List<CartItem> cartItems, OnItemListener onItemListener) {
        this.context = context;
        this.cartItems = cartItems;
        this.mOnItemListener = onItemListener;
        this.totalPriceView = totalPriceView;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item_view, parent, false), mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        Item item = cartItem.getItem();

        holder.nameView.setText(item.getModelName());
        holder.brandView.setText(item.getBrand());
        holder.imageView.setImageResource(item.getImage());
        String placeHolder = "$" + item.getPrice();//just placeholder to set the text as a double (setText only supports ints)
        holder.priceView.setText(placeHolder);
        placeHolder = "Qty: " + CartManager.getInstance().getItemQuantity(item.getItemID());
        holder.qtyView.setText(placeHolder);

        holder.increaseButton.setOnClickListener(v -> {
            CartManager.getInstance().incrementItemQuantity(item.getItemID());
            holder.qtyView.setText("Qty: " + CartManager.getInstance().getItemQuantity(item.getItemID()));
            updateTotalPrice();
        });

        holder.decreaseButton.setOnClickListener(v -> {
            CartManager.getInstance().decrementItemQuantity(item.getItemID());
            holder.qtyView.setText("Qty: " + CartManager.getInstance().getItemQuantity(item.getItemID()));
            updateTotalPrice();
        });

        holder.deleteItemButton.setOnClickListener(v -> {
            CartManager.getInstance().removeItemFromCart(item.getItemID());
            this.notifyItemRemoved(position);
            updateTotalPrice();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void updateTotalPrice() {
        totalPriceView.setText(String.format(Locale.ENGLISH, "Total Price: $%.2f", CartManager.getInstance().getTotalPrice()));
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
