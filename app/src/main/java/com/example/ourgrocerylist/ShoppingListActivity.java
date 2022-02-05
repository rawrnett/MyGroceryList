package com.example.ourgrocerylist;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ourgrocerylist.db.Items;
import com.example.ourgrocerylist.viewmodel.ShoppingListViewModel;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity implements  ShoppingListAdapter.HandleItemsClick {

    private ShoppingListViewModel viewModel;
    private TextView noResultTextView;
    private RecyclerView recyclerView;
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        getSupportActionBar().setTitle("Shopping List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noResultTextView = findViewById(R.id.no_result);
        recyclerView = findViewById(R.id.recyclerView);

        ImageView addNew = findViewById(R.id.addNewItem);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShoppingListActivity.this, AddShoppingActivity.class));
            }
        });
        initViewModel();
        initRecyclerView();
        viewModel.getAllShoppingList();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        viewModel.getShoppingListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if (items == null) {
                    noResultTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    shoppingListAdapter.setShoppingList(items);
                    recyclerView.setVisibility(View.VISIBLE);
                    noResultTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListAdapter = new ShoppingListAdapter(this, this);
        recyclerView.setAdapter(shoppingListAdapter);
    }

    @Override
    public void itemClick(Items items) {
        if(items.completed) {
            items.completed = false;
        }else{
            items.completed = true;
        }
        viewModel.updateItem(items);
    }
    @Override
    public void deleteItem(Items items) {
        viewModel.deleteItem(items);
    }
}