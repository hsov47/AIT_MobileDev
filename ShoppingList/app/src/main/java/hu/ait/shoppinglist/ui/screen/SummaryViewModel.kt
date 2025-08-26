package hu.ait.shoppinglist.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import hu.ait.shoppinglist.ui.navigation.SummaryScreenRoute

class SummaryViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    var allShop by mutableStateOf(0)
    var homeShop by mutableStateOf(0)
    var groceryShop by mutableStateOf(0)
    var otherShop by mutableStateOf(0)
    var homeTotal by mutableStateOf(0)
    var groceryTotal by mutableStateOf(0)
    var otherTotal by mutableStateOf(0)

    init {
        allShop = savedStateHandle.toRoute<SummaryScreenRoute>().allShop
        homeShop = savedStateHandle.toRoute<SummaryScreenRoute>().homeShop
        groceryShop = savedStateHandle.toRoute<SummaryScreenRoute>().groceryShop
        otherShop = savedStateHandle.toRoute<SummaryScreenRoute>().otherShop
        homeTotal = savedStateHandle.toRoute<SummaryScreenRoute>().homeTotal
        groceryTotal = savedStateHandle.toRoute<SummaryScreenRoute>().groceryTotal
        otherTotal = savedStateHandle.toRoute<SummaryScreenRoute>().otherTotal

    }
}