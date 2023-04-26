package urbantech.presentation.mainactivity;

import static urbantech.business.GlobalState.compare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import java.util.List;

import urbantech.objects.Item;
import urbantech.presentation.compare.CompareActivity;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private final OnItemListener mOnItemListener;
    final Context context;
    final List<Item> items;


    public ItemAdapter(Context context, List<Item> items, OnItemListener onItemListener) {
        this.context = context;
        this.items = items;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false), mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getModelName());
        holder.brandView.setText(items.get(position).getBrand());
        holder.imageView.setImageResource(items.get(position).getImage());
        String placeHolder = "$" + items.get(position).getPrice();//just placeholder to set the text as a double (setText only supports ints)
        holder.priceView.setText(placeHolder);

        holder.compareButton.setOnClickListener(v -> {
            //compare object created on startup of mainactivity so is never null
            String compareItems = "Clicked the compare on " + items.get(position).getModelName();
            Toast.makeText(context.getApplicationContext(), compareItems.toUpperCase() + "\n ONLY TWO ITEMS CAN BE COMPARED AT A TIME", Toast.LENGTH_LONG).show();
            if (compare.size() == 0)//if nothing in the object yet
                compare.addItem(items.get(position));//add the item to the compare object

            else if (compare.size() == 1) {//if there is 1 item in the compare
                compare.addItem(items.get(position));
                Intent intent = new Intent(context, CompareActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);//go to the compare activity
            } else {//if there are more than 2 items in compare (this will happen after you successfully compare two objects and then go back to compare another one
                compare.removeFromPos(0);//remove the two items in the compare object
                compare.removeFromPos(0);
                Toast.makeText(context.getApplicationContext(), "ADD ANOTHER ITEM, PREVIOUS ITEMS ARE REMOVED", Toast.LENGTH_LONG).show();
                compare.addItem(items.get(position));//add the item
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(int position) {
        items.remove(position);
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }
}
