package com.example.ourgrocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ourgrocerylist.db.Items;
import java.util.List;

public class AddShoppingAdapter extends RecyclerView.Adapter<AddShoppingAdapter.ViewHolder> {

    private Context context;
    private List<Items> itemList;
    private HandleItemsClick clickListener;

    public AddShoppingAdapter(Context context, HandleItemsClick clickListener){
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setItemList(List<Items> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.add_shopping_recycler_row, parent, false);
        return new AddShoppingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddShoppingAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(this.itemList.get(position).itemName);
        holder.safetyStock.setText(this.itemList.get(position).safetyStock + "");
        holder.inStock.setText(this.itemList.get(position).inStock + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.editItem(itemList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (itemList == null || itemList.size() == 0)
            return 0;
        else
            return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView inStock;
        TextView safetyStock;

        public ViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            inStock = view.findViewById(R.id.in_stock);
            safetyStock = view.findViewById(R.id.safety_stock);
        }
    }

    public interface HandleItemsClick {
        void editItem (Items items);
    }
}
