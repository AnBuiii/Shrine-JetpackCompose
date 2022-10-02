package com.example.shrine.ui.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrine.data.ItemData
import com.example.shrine.data.SampleItemsData
import com.example.shrine.ui.theme.ShrineTheme
import com.example.shrine.utils.transformToWeavedList

@Composable
fun Catalog(
    modifier: Modifier = Modifier,
    items: List<ItemData> = SampleItemsData
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    LazyRow(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(32.dp)
    ) {

        itemsIndexed(
            items = transformToWeavedList(items.take(items.size-1)),
            key = { _, item -> item[0].id }
        ) { idx, item ->
            val even = idx % 2 == 0
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(vertical = 48.dp, horizontal = 16.dp)
                .width((screenWidth * 0.66f).dp),
                horizontalAlignment = if (!even) Alignment.CenterHorizontally else Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                if (even) {
                    if (item.getOrNull(1) != null) {
                        CatalogItem(
                            modifier = Modifier
                                .align(Alignment.End)
                                .weight(1f)
                                .fillMaxWidth(0.85f),
                            data = item[1],
                        )
                        Spacer(Modifier.height(40.dp))
                        CatalogItem(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(0.85f),
                            data = item[0],
                        )
                    } else {
                        CatalogItem(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .fillMaxHeight(0.5f),
                            data = item[0],
                        )
                    }
                } else {
                    CatalogItem(
                        modifier = Modifier
                            .padding(top = 240.dp)
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.85f),
                        data = item[0],
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CatalogPreview() {
    ShrineTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Catalog(items = SampleItemsData)
        }
    }
}