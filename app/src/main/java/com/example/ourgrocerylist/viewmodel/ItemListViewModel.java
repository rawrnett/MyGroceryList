package com.example.ourgrocerylist.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.ourgrocerylist.db.AppDatabase;
import com.example.ourgrocerylist.db.Items;
import java.util.List;

public class ItemListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Items>> listOfItems;
    AppDatabase appDatabase;

    public ItemListViewModel(Application application){
        super(application);

        listOfItems = new MutableLiveData<>();

        appDatabase = AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<Items>> getItemListObserver(){
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

    public void saveNewItem(String itemName, int safety, int inStock) {
        Items items = new Items();
        items.itemName = itemName;
        items.safetyStock = safety;
        items.inStock = inStock;
        appDatabase.inventoryDao().insertItem(items);
        getAllItemsList();
    }

    public void updateItem(Items items) {
        appDatabase.inventoryDao().updateItem(items);
        getAllItemsList();
    }

    public void deleteItem(Items items) {
        appDatabase.inventoryDao().deleteItem(items);
        getAllItemsList();
    }

}
