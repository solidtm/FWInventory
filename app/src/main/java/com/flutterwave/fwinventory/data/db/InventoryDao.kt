package com.flutterwave.fwinventory.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.flutterwave.fwinventory.data.model.InventoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: InventoryItem)

    @Update
    suspend fun updateItem(item: InventoryItem)

    @Delete
    suspend fun deleteItem(item: InventoryItem)

    @Query("SELECT * FROM Inventories WHERE userId = :userId")
    fun getItems(userId: String): Flow<List<InventoryItem>>

    @Query("SELECT * FROM Inventories WHERE id = :id AND userId = :userId")
    suspend fun getItem(id: Int, userId: String): InventoryItem?
}