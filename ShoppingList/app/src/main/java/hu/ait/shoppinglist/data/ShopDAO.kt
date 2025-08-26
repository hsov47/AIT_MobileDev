package hu.ait.shoppinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDAO {
    @Query("SELECT * FROM shopTable")
    fun getAllShop() : Flow<List<ShopItem>>

    @Query("SELECT * from shopTable WHERE id = :id")
    fun getShop(id: Int): Flow<ShopItem>

    @Query("SELECT COUNT(*) from shopTable")
    suspend fun getShopNum(): Int

    @Query("SELECT COUNT(*) from shopTable WHERE category = :shopCategory")
    suspend fun getShopCategoryNum(shopCategory : ShopCategory): Int

    @Query("SELECT SUM(estPrice) from shopTable WHERE category = :shopCategory")
    suspend fun getShopCategoryTotal(shopCategory : ShopCategory): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shop: ShopItem)

    @Update
    suspend fun update(shop: ShopItem)

    @Delete
    suspend fun delete(shop: ShopItem)

    @Query("DELETE from shoptable")
    suspend fun deleteAllShop()

}