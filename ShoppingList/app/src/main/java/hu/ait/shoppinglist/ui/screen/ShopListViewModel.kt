package hu.ait.shoppinglist.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import hu.ait.shoppinglist.data.ShopDAO
import hu.ait.shoppinglist.data.ShopItem
import hu.ait.shoppinglist.data.ShopCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ShopListViewModel @Inject constructor(val shopDAO: ShopDAO) : ViewModel(){

    fun getAllShopList(): Flow<List<ShopItem>> {
        return shopDAO.getAllShop()
    }

    suspend fun getAllShopNum(): Int {
        return shopDAO.getShopNum()
    }

    suspend fun getHomeShopNum(): Int {
        return shopDAO.getShopCategoryNum(ShopCategory.HOME)
    }

    suspend fun getGroceryShopNum(): Int {
        return shopDAO.getShopCategoryNum(ShopCategory.GROCERY)
    }

    suspend fun getOtherShopNum(): Int {
        return shopDAO.getShopCategoryNum(ShopCategory.OTHER)
    }
    suspend fun getHomeShopTotal(): Int {
        return shopDAO.getShopCategoryTotal(ShopCategory.HOME)
    }

    suspend fun getGroceryShopTotal(): Int {
        return shopDAO.getShopCategoryTotal(ShopCategory.GROCERY)
    }

    suspend fun getOtherShopTotal(): Int {
        return shopDAO.getShopCategoryTotal(ShopCategory.OTHER)
    }

    fun addShopList(shopItem: ShopItem) {
        // launch coroutine when viewModel is in focus
        viewModelScope.launch(Dispatchers.IO) {
            shopDAO.insert(shopItem)
        }
    }

    fun removeShopItem(shopItem: ShopItem) {
        viewModelScope.launch{
            shopDAO.delete(shopItem)
        }
    }

    fun editShopItem(editedShop: ShopItem) {
        viewModelScope.launch {
            shopDAO.update(editedShop)
        }
    }

    fun changeShopState(shopItem: ShopItem, value: Boolean) {
        // make new instance triggering state change in table
        val updatedToDo = shopItem.copy()
        updatedToDo.isBought = value
        viewModelScope.launch {
            shopDAO.update(updatedToDo)
        }
    }

    fun clearAllShop() {
        viewModelScope.launch{
            shopDAO.deleteAllShop()
        }
    }

}
