package urbantech.presentation.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbantech.R;

import java.util.List;

import urbantech.objects.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private final OnCategoryListener mOnCategoryListener;
    final Context context;
    final List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories, OnCategoryListener onCategoryListener) {
        this.context = context;
        this.categories = categories;
        this.mOnCategoryListener = onCategoryListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view, parent, false), mOnCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if (categories != null && categories.size() > position) {
            holder.nameView.setText(categories.get(position).getName());
            holder.imageView.setImageResource(categories.get(position).getImage());
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface OnCategoryListener {
        void onCategoryClick(int position);
    }
}
