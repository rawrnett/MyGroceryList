package com.example.ourgrocerylist.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InventoryDao {

    @Query("Select * from Items where inStock > 0")
    List<Items> getAllInventoryList();

    @Query("Select * from Items where inStock < safetyStock or shoppingList == 1")
    List<Items> getAllShoppingList();

    @Query("Select * from Items")
    List<Items> getAllItemsList();

    @Insert
    void insertItem(Items items);

    @Delete
    void deleteItem(Items items);

    @Update
    void updateItem(Items items);

}
