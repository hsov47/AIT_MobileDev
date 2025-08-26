package hu.ait.shoppinglist.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import hu.ait.shoppinglist.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import hu.ait.shoppinglist.data.ShopCategory
import hu.ait.shoppinglist.data.ShopItem
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListScreen (
    viewModel: ShopListViewModel = hiltViewModel(),
    onInfoClicked : (Int, Int, Int, Int, Int, Int, Int) -> Unit,
    modifier :  Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val shopList by viewModel.getAllShopList().collectAsState(emptyList())
    val sortedShopList = shopList.sortedBy { it.isBought }

    var showShopDialog by rememberSaveable { mutableStateOf(false) }
    var shopToEdit: ShopItem? by rememberSaveable {
        mutableStateOf(null)
    }

    val colorScheme = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        context.getString(R.string.app_name),
                        fontSize = 30.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorScheme.primary,
                    titleContentColor = colorScheme.onPrimary,
                    navigationIconContentColor = colorScheme.onPrimary,
                    actionIconContentColor = colorScheme.onPrimary
                ),
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.clearAllShop()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete all"
                        )
                    }
                    IconButton(
                        onClick = {
                            coroutineScope.launch {

                                val allShop = viewModel.getAllShopNum()
                                val allHome = viewModel.getHomeShopNum()
                                val allGrocery = viewModel.getGroceryShopNum()
                                val allOther = viewModel.getOtherShopNum()
                                val allHomeTotal = viewModel.getHomeShopTotal()
                                val allGroceryTotal = viewModel.getGroceryShopTotal()
                                val allOtherTotal = viewModel.getOtherShopTotal()

                                onInfoClicked(
                                    allShop,
                                    allHome,
                                    allGrocery,
                                    allOther,
                                    allHomeTotal,
                                    allGroceryTotal,
                                    allOtherTotal
                                )
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.receipt),
                            contentDescription = "Summary"
                        )
                    }
                }
            )
        },
        floatingActionButton =
            {FloatingActionButton(
                    onClick = {shopToEdit = null
                        showShopDialog = true}
                ) {
                    Icon(Icons.Filled.AddCircle, "Add")
                }
            }


    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            if (shopList.isEmpty()) {
                Text(context.getString(R.string.no_items),
                    modifier = modifier.padding(10.dp))
            } else {
                LazyColumn {
                    items(sortedShopList) { shopItem ->
                        ShopCard(
                            shopItem = shopItem,
                            onCheckedChange = {
                                viewModel.changeShopState(shopItem, it)
                            },
                            onDelete = {
                                viewModel.removeShopItem(shopItem)
                            },
                            onEdit = {
                                    shopItemToEdit ->
                                shopToEdit = shopItemToEdit
                                showShopDialog = true
                            }
                        )

                    }
                }
            }
        }
    }
    if (showShopDialog) {
        ShopDialog(
            viewModel = viewModel,
            shopToEdit = shopToEdit,
            onCancel = {
                showShopDialog = false
            },
            preselected = shopToEdit?.category ?: ShopCategory.GROCERY,
            onSelectionChanged = { selectedCategory: String ->
            },
            list = ShopCategory.entries.toList(),
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShopCard(
    shopItem: ShopItem,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    onEdit: (ShopItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var shopChecked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = shopItem.isBought,
                    onCheckedChange = { onCheckedChange(it) }
                )
                Icon(
                    painter = painterResource(id = shopItem.category.getIcon()),
                    contentDescription = "Category",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp),
                )

                Text(shopItem.title, modifier = Modifier.fillMaxWidth(0.6f))
                //Spacer(modifier = Modifier.fillMaxSize(0.45f))

                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onDelete()
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Build,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        onEdit(shopItem)
                    }
                )
                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp
                        else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Expand or close"
                    )
                }
            }

            if (expanded) {
                Text(
                    context.getString(R.string.exp_desc) + shopItem.description,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
                Text(
                    context.getString(R.string.exp_price) + shopItem.estPrice,
                    style = TextStyle(
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Composable
fun ShopDialog(
    list: List<ShopCategory>,
    preselected: ShopCategory,
    onSelectionChanged: (myData: String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShopListViewModel,
    shopToEdit: ShopItem? = null,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    var shopTitle by remember {
        mutableStateOf(
            shopToEdit?.title ?: ""
        )
    }
    var shopDesc by remember {
        mutableStateOf(
            shopToEdit?.description ?: ""
        )
    }
    var shopPrice by remember {
        mutableIntStateOf(
            shopToEdit?.estPrice ?: 0
        )
    }

    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {
        onCancel()
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Text(
                    if (shopToEdit == null) context.getString(R.string.new_items)
                    else context.getString(R.string.edit_items),
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(context.getString(R.string.item_title)) },
                    value = shopTitle,
                    onValueChange = { shopTitle = it })
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(context.getString(R.string.item_desc)) },
                    value = shopDesc,
                    onValueChange = { shopDesc = it })
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(context.getString(R.string.item_price)) },
                    value = shopPrice.toString(),
                    onValueChange = { newText ->
                        // Only accept digits and empty string
                        if (newText.all { it.isDigit() } || newText.isEmpty()) {
                            shopPrice = newText.toInt()
                        }},
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number))

                OutlinedCard(
                    modifier = modifier.clickable {
                        expanded = !expanded
                    }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top,
                    ) {
                        // Add a clickable Text or IconButton to trigger the dropdown
                        Text (
                            text = selected.toString(), // Display selected category
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expanded = true // Show dropdown menu when clicked
                                }
                                .weight(1f)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        Icon(Icons.Outlined.ArrowDropDown, null, modifier =
                            Modifier.padding(8.dp))

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            list.forEach { listEntry ->
                                DropdownMenuItem(
                                    onClick = {
                                        selected = listEntry
                                        expanded = false
                                        onSelectionChanged(selected.toString())
                                    },
                                    text = {
                                        Text(
                                            text = listEntry.toString(),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .align(Alignment.Start)
                                        )
                                    },
                                )
                            }
                        }
                    }
                }

                    Button(onClick = {
                        if (shopToEdit == null) {
                            viewModel.addShopList(
                                ShopItem(
                                    id = 0,
                                    title = shopTitle,
                                    description = shopDesc,
                                    estPrice = shopPrice,
                                    category = selected,
                                    isBought = false
                                )
                            )
                        } else {
                            val editedShop = shopToEdit.copy(
                                title = shopTitle,
                                description = shopDesc,
                                estPrice = shopPrice,
                                category = selected,
                                isBought = false
                            )
                            viewModel.editShopItem(editedShop)
                        }
                        onCancel()
                    }
                    ) {
                        Text(context.getString(R.string.btn_save))
                    }

                }
            }
        }
    }
