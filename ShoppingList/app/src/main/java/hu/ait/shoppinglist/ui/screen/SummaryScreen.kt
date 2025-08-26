package hu.ait.shoppinglist.ui.screen

import android.graphics.PathEffect
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hu.ait.shoppinglist.R
import hu.ait.shoppinglist.data.ShopCategory
import hu.ait.shoppinglist.data.ShopItem
import hu.ait.shoppinglist.ui.navigation.SummaryScreenRoute
import hu.ait.shoppinglist.ui.theme.primaryDark

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    summaryViewModel: SummaryViewModel = viewModel(),
    onClosed: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = context.getString(R.string.sum_title),
            fontSize = 30.sp,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp)
                .fillMaxWidth(),
            color = colorScheme.primary

        )
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier.padding(5.dp).fillMaxWidth()


        ) {
            Text(
                text = context.getString(R.string.sum_items) + summaryViewModel.allShop,
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                textAlign = TextAlign.Center

            )
            Text(
                text = context.getString(R.string.sum_cost) +
                        (summaryViewModel.homeTotal +
                                summaryViewModel.groceryTotal +
                                summaryViewModel.otherTotal),
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        SummaryCard(
            category = ShopCategory.GROCERY,
            categoryNum = summaryViewModel.groceryShop,
            categoryTotal = summaryViewModel.groceryTotal
        )
        SummaryCard(
            category = ShopCategory.HOME,
            categoryNum = summaryViewModel.homeShop,
            categoryTotal = summaryViewModel.homeTotal
        )
        SummaryCard(
            category = ShopCategory.OTHER,
            categoryNum = summaryViewModel.otherShop,
            categoryTotal = summaryViewModel.otherTotal
        )

        Spacer(modifier = Modifier.size(32.dp))
        TextButton(
            onClick = {
                onClosed()
            },
            modifier = modifier.fillMaxWidth(),
            content = { Text(context.getString(R.string.btn_close))},
            colors = ButtonDefaults.buttonColors(
                    colorScheme.primary,
                    colorScheme.onPrimary,
                    colorScheme.primary,
                    colorScheme.primary)

        )
    }
}

@Composable
fun SummaryCard (
    category: ShopCategory,
    categoryNum : Int,
    categoryTotal : Int,
    summaryViewModel: SummaryViewModel = viewModel(),
) {
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
                .padding(10.dp)
                .animateContentSize()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = category.getIcon()),
                    contentDescription = "Category",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp),
                )
                Text(category.toString(), Modifier.fillMaxWidth(0.35f))
                //Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = " Items:$categoryNum"
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "$$categoryTotal",
                )

            }
        }

    }
}


