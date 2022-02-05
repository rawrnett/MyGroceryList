package com.example.ourgrocerylist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.ourgrocerylist.InventoryListAdapter;
import com.example.ourgrocerylist.db.AppDatabase;
import com.example.ourgrocerylist.db.Items;

import java.util.List;

public class InventoryListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Items>> listOfInventory;
    AppDatabase appDatabase;

    public InventoryListViewModel(@NonNull Application application) {
        super(application);

        listOfInventory = new MutableLiveData<>();

        appDatabase = AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Items>> getInventoryListObserver(){
        return listOfInventory;
    }

     public void getAllInventoryList(){
        List<Items> inventoryList = appDatabase.inventoryDao().getAllInventoryList();
        if(inventoryList.size() > 0){
            listOfInventory.postValue(inventoryList);
        }else {
            listOfInventory.postValue(null);
        }
     }
    public void saveNewItem(String itemName, int inStock, int safety) {
        Items items = new Items();
        items.itemName = itemName;
        items.inStock = inStock;
        items.safetyStock = safety;
        appDatabase.inventoryDao().insertItem(items);
        getAllInventoryList();
    }


     public void updateItem(Items items){
        appDatabase.inventoryDao().updateItem(items);
        getAllInventoryList();
     }

    public void deleteItem(Items items) {
        appDatabase.inventoryDao().deleteItem(items);
        getAllInventoryList();
    }
}

