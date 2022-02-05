package com.example.ourgrocerylist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ourgrocerylist.db.Items;
import com.example.ourgrocerylist.viewmodel.AddShoppingViewModel;
import java.util.List;

public class AddShoppingActivity extends AppCompatActivity implements AddShoppingAdapter.HandleItemsClick {

    private AddShoppingViewModel viewModel;
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    private AddShoppingAdapter AddShoppingAdapter;
    private Items addToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping);
        getSupportActionBar().setTitle("Add Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noResultTextView = findViewById(R.id.no_result);
        recyclerView = findViewById(R.id.recyclerView);

        initViewModel();
        initRecyclerView();
        viewModel.getAllItemsList();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AddShoppingViewModel.class);
        viewModel.getAddShoppingObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if (items == null) {
                    noResultTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    AddShoppingAdapter.setItemList(items);
                    recyclerView.setVisibility(View.VISIBLE);
                    noResultTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AddShoppingAdapter = new AddShoppingAdapter(this, this);
        recyclerView.setAdapter(AddShoppingAdapter);
    }

    private void addShoppingQtyDialog(){
        AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        View dialogView = getLayoutInflater().inflate(R.layout.shopping_quantity, null);
        EditText addQty = dialogView.findViewById(R.id.addQty);
        ImageView cancelItemButton = dialogView.findViewById(R.id.cancelItemButton);
        addQty.setText(addToList.shoppingAmount + "");
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

                String quantity = addQty.getText().toString();
                if (!quantity.equals("")) {
                    int num1 = Integer.parseInt(quantity);
                }
                addToList.shoppingAmount = Integer.parseInt(quantity);
                addToList.shoppingList = true;
                viewModel.updateItem(addToList);
                dialogBuilder.dismiss();
                startActivity(new Intent(AddShoppingActivity.this, ShoppingListActivity.class));
            }
        });
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
                initViewModel();
    }

    @Override
    public void editItem(Items items) {
        this.addToList = items;
        addShoppingQtyDialog();
    }
}


