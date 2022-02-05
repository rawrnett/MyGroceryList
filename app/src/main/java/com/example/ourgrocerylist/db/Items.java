package com.example.ourgrocerylist.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Items {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "itemName")
    public String itemName;

    @ColumnInfo(name = "safetyStock")
    public int safetyStock;

    @ColumnInfo(name = "inStock")
    public int inStock;

    @ColumnInfo(name = "shoppingList")
    public Boolean shoppingList;

    @ColumnInfo(name = "shoppingAmount")
    public int shoppingAmount;

    @ColumnInfo(name = "completed")
    public boolean completed;
}

