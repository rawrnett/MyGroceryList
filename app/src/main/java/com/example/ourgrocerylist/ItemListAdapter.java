package com.example.ourgrocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ourgrocerylist.db.Items;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private Context context;
    private List<Items> itemList;
    private HandleItemsClick clickListener;

    public ItemListAdapter(Context context, HandleItemsClick clickListener){
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setItemList(List<Items> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.ViewHolder holder, int position) {

        holder.itemName.setText(this.itemList.get(position).itemName);
        holder.safetyStock.setText(this.itemList.get(position).safetyStock + "");
        holder.inStock.setText(this.itemList.get(position).inStock + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            clickListener.editItem(itemList.get(position));
            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.deleteItem(itemList.get(position));
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
        TextView safetyStock;
        TextView inStock;
        ImageView deleteItem;

        public ViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            safetyStock = view.findViewById(R.id.safety_stock);
            inStock = view.findViewById(R.id.in_stock);
            deleteItem = view.findViewById(R.id.delete_button);
        }
    }

    public interface HandleItemsClick {
        void deleteItem(Items items);
        void editItem(Items items);
    }
}

