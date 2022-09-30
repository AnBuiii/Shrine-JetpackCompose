package com.example.shrine


import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shrine.data.ItemData
import com.example.shrine.data.SampleItemsData
import com.example.shrine.ui.theme.ShrineTheme
import kotlin.math.min

@Composable
fun ExpandedCart(
    items: List<ItemData> = SampleItemsData,
    onCollapse: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colors.secondary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CartHeader { onCollapse() }
            items.forEach { item ->
                CartItem(item)
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text("This is a button".uppercase())
            }
        }
    }

}

@Composable
private fun CartHeader(
    iconOnTap: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { iconOnTap() }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "down arrow "
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Cart".uppercase())
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "${SampleItemsData.size}".uppercase())
    }
}

@Composable
private fun CartItem(item: ItemData) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { },
            Modifier.padding(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.RemoveCircleOutline,
                contentDescription = "Remove item icon"
            )
        }
        Column(
            Modifier.fillMaxWidth()
        ) {
            Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
            Row(
                Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.photoResId),
                    contentDescription = null
                )
                Spacer(Modifier.width(20.dp))
                Column(
                    Modifier.padding(end = 16.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.vendor}".uppercase(),
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            text = "${item.price}".uppercase(),
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Text(
                        text = item.title.uppercase(),
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExpandedCartPreview() {
    ShrineTheme {
        ExpandedCart()
    }
}

@Composable
private fun CollapsedCart(
    items: List<ItemData> = SampleItemsData.take(6),
    onTap: () -> Unit = {}
) {
    Row(
        Modifier
            .clickable { onTap() }
            .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping cart icon",
            )
        }
        items.take(3).forEach { item ->
            CollapsedCartItem(data = item)
        }
        if (items.size > 3) {
            Box(
                Modifier.size(width = 32.dp, height = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "+${items.size - 3}",
                    style = MaterialTheme.typography.subtitle2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CollapsedCartItem(data: ItemData) {
    Image(
        painter = painterResource(id = data.photoResId),
        contentDescription = data.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

@ExperimentalAnimationApi
@Preview
@Composable
fun CollapsedCartPreview() {
    ShrineTheme {
        Surface(
            color = MaterialTheme.colors.secondary
        ) {
            CollapsedCart(SampleItemsData.take(4))
        }

    }
}

enum class CartBottomSheetState {
    Expanded,
    Collapsed,
    Hidden,
}

@ExperimentalAnimationApi
@Composable
fun CartExpandingBottomSheet(
    modifier: Modifier = Modifier,
    items: List<ItemData> = SampleItemsData.take(14),
    sheetState: CartBottomSheetState = CartBottomSheetState.Collapsed,
    maxHeight: Dp,
    maxWidth: Dp,
    onStateChange: (CartBottomSheetState) -> Unit = {}
) {
    val cartTransition = updateTransition(
        targetState = sheetState,
        label = "Cart Transition"
    )

    val cartXOffset by cartTransition.animateDp(
        label = "cartXOffset",
        transitionSpec = {
            when {
                CartBottomSheetState.Expanded isTransitioningTo CartBottomSheetState.Collapsed ->
                    tween(durationMillis = 433, delayMillis = 67)
                CartBottomSheetState.Collapsed isTransitioningTo CartBottomSheetState.Expanded ->
                    tween(durationMillis = 150)
                else ->
                    tween(durationMillis = 450)
            }
        }
    ) {
        when (it) {
            CartBottomSheetState.Expanded -> maxWidth
            CartBottomSheetState.Hidden -> 0.dp
            else -> {
                val size = min(3, items.size)
                var width = 24 + 40 * (size + 1) + 16 * size + 16
                if (items.size > 3) width += 32 + 16
                width.dp
            }
        }
    }

    val cartHeight by cartTransition.animateDp(
        label = "cartHeight",
        transitionSpec = {
            when {
                CartBottomSheetState.Expanded isTransitioningTo CartBottomSheetState.Collapsed ->
                    tween(durationMillis = 283)
                else ->
                    tween(durationMillis = 500)
            }
        }
    ) {
        if (it == CartBottomSheetState.Expanded) maxHeight else 56.dp
    }

    val cornerSize by cartTransition.animateDp(
        label = "cartCornerSize",
        transitionSpec = {
            when {
                CartBottomSheetState.Expanded isTransitioningTo CartBottomSheetState.Collapsed ->
                    tween(durationMillis = 433, delayMillis = 67)
                else ->
                    tween(durationMillis = 150)
            }
        }
    ) {
        if (it == CartBottomSheetState.Expanded) 0.dp else 24.dp
    }

    Surface(
        modifier = Modifier
            .clip(CutCornerShape(topStart = cornerSize))
            .width(cartXOffset)
            .height(cartHeight)
            .then(modifier),
        color = MaterialTheme.colors.secondary,
        elevation = 8.dp,
    ) {

        cartTransition.AnimatedContent(
            transitionSpec = {
                when {
                    CartBottomSheetState.Expanded isTransitioningTo CartBottomSheetState.Collapsed ->
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 117,
                                delayMillis = 117,
                                easing = LinearEasing
                            )
                        ) with
                                fadeOut(
                                    animationSpec = tween(
                                        durationMillis = 117,
                                        easing = LinearEasing
                                    )
                                )
                    CartBottomSheetState.Collapsed isTransitioningTo CartBottomSheetState.Expanded ->
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 150,
                                delayMillis = 150,
                                easing = LinearEasing
                            )
                        ) with
                                fadeOut(
                                    animationSpec = tween(
                                        durationMillis = 150,
                                        easing = LinearEasing
                                    )
                                )
                    else -> EnterTransition.None with ExitTransition.None
                }.using(SizeTransform(clip = false))
            },
        ) { targetState ->
            if (targetState == CartBottomSheetState.Expanded) {
                ExpandedCart(
                    items = items.take(6),
                    onCollapse = {
                        onStateChange(CartBottomSheetState.Collapsed)
                    }
                )
            } else {
                CollapsedCart(
                    items = items.take(6),
                    onTap = {
                        onStateChange(CartBottomSheetState.Expanded)
                    }
                )
            }

        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun CartExpandingBottomSheetPreview() {
    ShrineTheme {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            var sheetState by remember { mutableStateOf(CartBottomSheetState.Collapsed) }
            CartExpandingBottomSheet(
                items = SampleItemsData,
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