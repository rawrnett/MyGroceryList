package com.example.ourgrocerylist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ourgrocerylist.db.Items;
import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>{
    private Context context;
    private List<Items> itemList;
    private HandleItemsClick clickListener;
    public ShoppingListAdapter(Context context, ShoppingListAdapter.HandleItemsClick clickListener){
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setShoppingList(List<Items> itemList){
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.shopping_recycler_row, parent, false);
        return new ShoppingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(this.itemList.get(position).itemName);
        holder.shoppingAmount.setText((this.itemList.get(position).shoppingAmount + (this.itemList.get(position).safetyStock - this.itemList.get(position).inStock))+ "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.itemClick(itemList.get(position));
            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.deleteItem(itemList.get(position));
            }
        });

        if(this.itemList.get(position).completed){
            holder.itemName.setPaintFlags(holder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.itemName.setPaintFlags(0);
        }
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
        TextView shoppingAmount;
        ImageView deleteItem;

        public ViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.item_name);
            shoppingAmount = view.findViewById(R.id.shopping_amount);
            deleteItem = view.findViewById(R.id.delete_button);
        }
    }

    public interface HandleItemsClick {
        void deleteItem(Items items);
        void itemClick (Items items);
    }
}

