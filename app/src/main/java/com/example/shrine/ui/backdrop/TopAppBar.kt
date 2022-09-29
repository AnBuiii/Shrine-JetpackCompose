package com.example.shrine.ui.backdrop

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shrine.R
import com.example.shrine.ui.theme.ShrineTheme

@Composable
private fun TopAppBarText(
    modifier: Modifier = Modifier, text: String
) {
    Text(
        modifier = modifier,
        text = text.uppercase(),
        style = MaterialTheme.typography.subtitle1,
        fontSize = 17.sp
    )
}

@Composable
private fun MenuSearchField() {
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .height(56.dp)
            .padding(end = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(value = searchText,
            onValueChange = { searchText = it },
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(end = 36.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                }
            })
        if (searchText.isEmpty()) {
            TopAppBarText(
                modifier = Modifier.alpha(ContentAlpha.disabled), text = "Search"
            )
        }
        Divider(
            modifier = Modifier.align(Alignment.BottomCenter),
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
        )
    }
}

@ExperimentalAnimationApi
@Composable
internal fun ShrineTopAppBar(
    backdropRevealed: Boolean, onBackdropReveal: (Boolean) -> Unit = {}
) {
    TopAppBar(title = {
        val density = LocalDensity.current

        Box(
            Modifier
                .width(46.dp)
                .fillMaxHeight()
                .toggleable(value = backdropRevealed,
                    onValueChange = { onBackdropReveal(it) },
                    indication = rememberRipple(bounded = false, radius = 56.dp),
                    interactionSource = remember { MutableInteractionSource() }),
            contentAlignment = Alignment.CenterStart) {
            AnimatedVisibility(
                visible = !backdropRevealed, enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 180, delayMillis = 90, easing = LinearEasing
                    )
                ) + slideInHorizontally(
                    initialOffsetX = { with(density) { (-20).dp.roundToPx() } },
                    animationSpec = tween(durationMillis = 270, easing = LinearEasing)
                ), exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 120, easing = LinearEasing
                    )
                ) + slideOutHorizontally(
                    targetOffsetX = { with(density) { (-20).dp.roundToPx() } },
                    animationSpec = tween(durationMillis = 120, easing = LinearEasing)
                ), label = "Menu navigation icon"
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_menu_cut_24px),
                    contentDescription = "Menu navigation icon"
                )
            }

            val logoTransition = updateTransition(
                targetState = backdropRevealed, label = "logoTransition"
            )
            val logoOffset by logoTransition.animateDp(
                transitionSpec = {
                    if (targetState) {
                        tween(durationMillis = 120, easing = LinearEasing)
                    } else {
                        tween(durationMillis = 270, easing = LinearEasing)
                    }
                }, label = "logoOffset"
            ) { revealed ->
                if (!revealed) 20.dp else 0.dp
            }

            Icon(
                painterResource(id = R.drawable.ic_shrine_logo),
                contentDescription = "Shrine logo",
                modifier = Modifier.offset(x = logoOffset)
            )
        }

        AnimatedContent(
            targetState = backdropRevealed, transitionSpec = {
                if (targetState) {
                    // Conceal to reveal
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 240, delayMillis = 120, easing = LinearEasing
                        )
                    ) + slideInHorizontally(
                        initialOffsetX = { with(density) { 30.dp.roundToPx() } },
                        animationSpec = tween(
                            durationMillis = 270, easing = LinearEasing
                        )
                    ) with fadeOut(
                        animationSpec = tween(
                            durationMillis = 120, easing = LinearEasing
                        )
                    ) + slideOutHorizontally(
                        targetOffsetX = { with(density) { (-30).dp.roundToPx() } },
                        animationSpec = tween(
                            durationMillis = 120, easing = LinearEasing
                        )
                    )
                } else {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 180, delayMillis = 90, easing = LinearEasing
                        )
                    ) + slideInHorizontally(
                        initialOffsetX = { with(density) { (-30).dp.roundToPx() } },
                        animationSpec = tween(
                            durationMillis = 270, easing = LinearEasing
                        )
                    ) with fadeOut(
                        animationSpec = tween(
                            durationMillis = 90, easing = LinearEasing
                        )
                    ) + slideOutHorizontally(
                        targetOffsetX = { with(density) { 30.dp.roundToPx() } },
                        animationSpec = tween(
                            durationMillis = 90, easing = LinearEasing
                        )
                    )
                }.using(SizeTransform(clip = false))
            }, contentAlignment = Alignment.CenterStart
        ) { revealed ->
            if (!revealed) TopAppBarText(text = "Shrine")
            else MenuSearchField()
        }
    }, actions = {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search action",
            tint = LocalContentColor.current.copy(alpha = ContentAlpha.high),
            modifier = Modifier.padding(end = 12.dp)
        )
    }, elevation = 0.dp
    )
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ShrineTopAppBarPreview() {
    ShrineTheme {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            ShrineTopAppBar(backdropRevealed = false)
            Spacer(modifier = Modifier.height(8.dp))
            ShrineTopAppBar(backdropRevealed = true)
        }
    }
}