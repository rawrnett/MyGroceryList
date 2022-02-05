package com.example.ourgrocerylist.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ourgrocerylist.MainActivity;
import com.example.ourgrocerylist.ShoppingListActivity;
import com.example.ourgrocerylist.db.AppDatabase;
import com.example.ourgrocerylist.db.Items;
import java.util.List;

public class AddShoppingViewModel extends AndroidViewModel {
    private MutableLiveData<List<Items>> listOfItems;
    AppDatabase appDatabase;

    public AddShoppingViewModel(Application application){
        super(application);

        listOfItems = new MutableLiveData<>();
        appDatabase = AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Items>> getAddShoppingObserver(){
        return listOfItems;
    }

    public void getAllItemsList(){
        List<Items> itemsList = appDatabase.inventoryDao().getAllItemsList();
        if(itemsList.size() > 0){
            listOfItems.postValue(itemsList);
        }else {
            listOfItems.postValue(null);
        }
    }

    public void updateItem(Items items) {
        appDatabase.inventoryDao().updateItem(items);
        getAllItemsList();
    }
}

