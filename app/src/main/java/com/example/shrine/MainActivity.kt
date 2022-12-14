package com.example.shrine

import android.content.ClipData.Item
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shrine.data.ItemData
import com.example.shrine.data.SampleItemsData
import com.example.shrine.ui.backdrop.Backdrop
import com.example.shrine.ui.cart.CartBottomSheetState
import com.example.shrine.ui.cart.CartExpandingBottomSheet
import com.example.shrine.ui.theme.ShrineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShrineTheme {
            }
        }
    }
}


@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShrineTheme {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            var sheetState by remember { mutableStateOf(CartBottomSheetState.Collapsed) }
            val cartItems = remember {
                mutableStateListOf(*listOf<ItemData>().toTypedArray())
            }
            Backdrop(
                onBackdropReveal = { revealed ->
                    sheetState =
                        if (revealed) CartBottomSheetState.Hidden else CartBottomSheetState.Collapsed
                },
                onAddCartItem = {
                    cartItems.add(it)
                }
            )
            CartExpandingBottomSheet(
                items = cartItems,
                maxHeight = maxHeight,
                maxWidth = minWidth,
                modifier = Modifier.align(Alignment.BottomEnd),
                sheetState = sheetState,
            ) {
                sheetState = it
            }
        }

    }
}