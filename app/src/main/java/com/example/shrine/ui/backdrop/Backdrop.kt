package com.example.shrine.ui.backdrop

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrine.data.Category
import com.example.shrine.data.ItemData
import com.example.shrine.data.SampleItemsData
import com.example.shrine.ui.catalog.Catalog
import com.example.shrine.ui.theme.ShrineScrimColor
import com.example.shrine.ui.theme.ShrineTheme
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Backdrop(
    onBackdropReveal: (Boolean) -> Unit = {},
    onAddCartItem: (ItemData) -> Unit = {}
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var backdropRevealed by rememberSaveable { mutableStateOf(scaffoldState.isRevealed) }
    val scope = rememberCoroutineScope()
    var activeCategory by rememberSaveable { mutableStateOf(Category.Feature) }
    val verticalScroll by remember { mutableStateOf(true) }
    BackdropScaffold(
        scaffoldState = scaffoldState,
        gesturesEnabled = false,
        appBar = {
            ShrineTopAppBar(
                backdropRevealed = backdropRevealed,
                onBackdropReveal = {
                    if (!scaffoldState.isAnimationRunning) {
                        backdropRevealed = it
                        onBackdropReveal(it)
                        scope.launch {
                            if (scaffoldState.isConcealed) {
                                scaffoldState.reveal()
                            } else {
                                scaffoldState.conceal()
                            }
                        }
                    }
                }
            )
        },
        frontLayerContent = {
            Box {
                Catalog(
                    items = SampleItemsData.filter {
                        activeCategory == Category.Feature || it.category == activeCategory
                    },
                    onAddCartItem = { onAddCartItem(it) }
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(imageVector = Icons.Default.Tune, contentDescription = "Change grid")
                }
            }
        },
        frontLayerShape = MaterialTheme.shapes.large,
        frontLayerElevation = 16.dp,
        frontLayerScrimColor = ShrineScrimColor.copy(alpha = 0.6f),

        backLayerContent = {
            NavigationMenu(
                modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
                backdropRevealed = backdropRevealed,
                activeCategory = activeCategory,
                onMenuSelect = {
                    backdropRevealed = false
                    onBackdropReveal(false)
                    activeCategory = it
                    scope.launch { scaffoldState.conceal() }
                }
            )
        },
    )
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun BackdropPreview() {
    ShrineTheme {
        Backdrop()
    }
}

