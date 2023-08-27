package com.example.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_items")
    fun getMenuItems(): LiveData<List<MenuItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuItems(items: List<MenuItemEntity>)
}

@Entity(tableName = "menu_items")
data class MenuItemEntity(
        @PrimaryKey val id: Int,
        val title: String,
        val description: String,
        val price: String,
        val image: String,
        val category : String
)


@Database(entities = [MenuItemEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun menuItemDao():MenuItemDao
}