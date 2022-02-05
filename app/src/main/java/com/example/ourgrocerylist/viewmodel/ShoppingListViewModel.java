package com.example.ourgrocerylist.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.ourgrocerylist.db.AppDatabase;
import com.example.ourgrocerylist.db.Items;
import java.util.List;

public class ShoppingListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Items>> listOfShopping;
    AppDatabase appDatabase;

    public ShoppingListViewModel(@NonNull Application application) {
        super(application);
        listOfShopping = new MutableLiveData<>();
        appDatabase = AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Items>> getShoppingListObserver(){
        return listOfShopping;
    }

    public void getAllShoppingList(){
        List<Items> shoppingList = appDatabase.inventoryDao().getAllShoppingList();
        if(shoppingList.size() > 0){
            listOfShopping.postValue(shoppingList);
        }else {
            listOfShopping.postValue(null);
        }
    }

    public void updateItem(Items items) {
        appDatabase.inventoryDao().updateItem(items);
        getAllShoppingList();
    }

    public void deleteItem(Items items) {
        appDatabase.inventoryDao().deleteItem(items);
        getAllShoppingList();
    }
}

