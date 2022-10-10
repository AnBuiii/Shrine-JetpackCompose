package com.example.shrine.ui.cart

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shrine.cart.CollapsedCart
import com.example.shrine.cart.ExpandedCart
import com.example.shrine.data.ItemData
import com.example.shrine.data.SampleItemsData
import com.example.shrine.ui.theme.ShrineTheme
import kotlin.math.min


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
    val collapsedCartItems = items.distinct()
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
                val size = min(3, collapsedCartItems.size)
                var width = 24 + 40 * (size + 1) + 16 * size + 16
                if (collapsedCartItems.size > 3) width += 32 + 16
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
                    items = items,
                    onCollapse = {
                        onStateChange(CartBottomSheetState.Collapsed)
                    }
                )
            } else {
                CollapsedCart(
                    items = collapsedCartItems,
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