package com.example.shrine.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrine.data.ItemData
import com.example.shrine.data.SampleItemsData
import com.example.shrine.utils.getVendorResId

@Composable
fun CatalogItem(
    modifier: Modifier = Modifier,
    data: ItemData
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = data.photoResId),
                contentDescription = "${data.title}'s picture ",
                contentScale = ContentScale.Crop
            )
            Icon(
                modifier = Modifier.padding(12.dp),
                imageVector = Icons.Outlined.AddShoppingCart,
                contentDescription = "Add to cart",
            )
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 12.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = getVendorResId(data.vendor)),
                contentDescription = "${data.title}'s vendor'}",
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = data.title, style = MaterialTheme.typography.subtitle2)
        Text(text = "$${data.price}", style = MaterialTheme.typography.body2, modifier = Modifier.padding(vertical = 12.dp))

    }
}

@Preview()
@Composable
fun CatalogItemPreview() {
    MaterialTheme {
        CatalogItem(
            modifier = Modifier.height(300.dp),
            data = SampleItemsData[0]
        )
    }
}