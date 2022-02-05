package com.example.ourgrocerylist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ourgrocerylist.db.Items;
import com.example.ourgrocerylist.viewmodel.ItemListViewModel;
import java.util.List;

public class ItemListActivity extends AppCompatActivity implements ItemListAdapter.HandleItemsClick {

    private ItemListViewModel viewModel;
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    private ItemListAdapter itemListAdapter;
    private Items itemForEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        getSupportActionBar().setTitle("Item List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noResultTextView = findViewById(R.id.no_result);
        recyclerView = findViewById(R.id.recyclerView);
        ImageView addNew = findViewById(R.id.addNewItem);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog(false);
            }
        });

        initViewModel();
        initRecyclerView();
        viewModel.getAllItemsList();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ItemListViewModel.class);
        viewModel.getItemListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if (items == null) {
                    noResultTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    itemListAdapter.setItemList(items);
                    recyclerView.setVisibility(View.VISIBLE);
                    noResultTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemListAdapter = new ItemListAdapter(this, this);
        recyclerView.setAdapter(itemListAdapter);
    }

    private void showAddItemDialog(boolean isForEdit) {
        AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        View dialogView = getLayoutInflater().inflate(R.layout.add_item, null);
        EditText addItemName = dialogView.findViewById(R.id.addItemName);
        EditText addItemSafety = dialogView.findViewById(R.id.addItemSafety);
        EditText addInStock = dialogView.findViewById(R.id.addInStock);
        ImageView cancelItemButton = dialogView.findViewById(R.id.cancelItemButton);

        if(isForEdit){
            addItemName.setText(itemForEdit.itemName + "");
            addItemSafety.setText(itemForEdit.safetyStock + "");
            addInStock.setText(itemForEdit.inStock + "");
        }

        cancelItemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        ImageView addItemButton = dialogView.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name = addItemName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ItemListActivity.this, "Enter Category Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                String safety = addItemSafety.getText().toString();
                if (!safety.equals("")) {
                    int num1 = Integer.parseInt(safety);
                }

                String stock = addInStock.getText().toString();
                if (!stock.equals("")) {
                    int num1 = Integer.parseInt(stock);
                }

                if(isForEdit){
                    itemForEdit.itemName = name;
                    itemForEdit.safetyStock = Integer.parseInt(safety);
                    itemForEdit.inStock = Integer.parseInt(stock);
                    viewModel.updateItem(itemForEdit);
                }else {
                    viewModel.saveNewItem(name, Integer.parseInt(safety), Integer.parseInt(stock));
                }
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        initViewModel();
    }

    @Override
    public void deleteItem(Items items) {
        viewModel.deleteItem(items);
    }

    @Override
    public void editItem(Items items) {
        this.itemForEdit = items;
        showAddItemDialog(true);
    }
}


