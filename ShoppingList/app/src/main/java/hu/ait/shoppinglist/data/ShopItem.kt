package hu.ait.shoppinglist.data

import androidx.compose.runtime.mutableStateListOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.ait.shoppinglist.R
import java.io.Serializable

@Entity(tableName = "shopTable")
data class ShopItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "category") var category:ShopCategory,
    @ColumnInfo(name = "name") val title:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "estPrice") val estPrice:Int,
    @ColumnInfo(name = "isBought") var isBought: Boolean
) : Serializable

enum class ShopCategory {
    GROCERY, HOME, OTHER;

    fun getIcon(): Int {
        return if (this == HOME) R.drawable.home
            else if (this == GROCERY) R.drawable.grocery
            else R.drawable.other
    }
}