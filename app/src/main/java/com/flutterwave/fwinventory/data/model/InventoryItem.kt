package com.flutterwave.fwinventory.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventories")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val name: String,
    val totalStock: Int,
    val price: Double,
    val description: String
)