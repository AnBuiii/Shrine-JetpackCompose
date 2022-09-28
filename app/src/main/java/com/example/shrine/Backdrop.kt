package com.example.shrine

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shrine.ui.theme.ShrineTheme
import kotlinx.coroutines.launch

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
private fun ShrineTopAppBar(
    backdropRevealed: Boolean,
    onBackdropReveal: (Boolean) -> Unit = {}
) {
    TopAppBar(
        title = {
            val density = LocalDensity.current

            Box(
                Modifier
                    .width(46.dp)
                    .fillMaxHeight()
                    .toggleable(
                        value = backdropRevealed,
                        onValueChange = { onBackdropReveal(it) },
                        indication = rememberRipple(bounded = false, radius = 56.dp),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                contentAlignment = Alignment.CenterStart
            ) {
                AnimatedVisibility(
                    visible = !backdropRevealed,
                    enter = fadeIn(
                        animationSpec = tween(
                            durationMillis = 180,
                            delayMillis = 90,
                            easing = LinearEasing
                        )
                    )
                            + slideInHorizontally(
                        initialOffsetX = { with(density) { (-20).dp.roundToPx() } },
                        animationSpec = tween(durationMillis = 270, easing = LinearEasing)
                    ),
                    exit = fadeOut(
                        animationSpec = tween(
                            durationMillis = 120,
                            easing = LinearEasing
                        )
                    )
                            + slideOutHorizontally(
                        targetOffsetX = { with(density) { (-20).dp.roundToPx() } },
                        animationSpec = tween(durationMillis = 120, easing = LinearEasing)
                    ),
                    label = "Menu navigation icon"
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_menu_cut_24px),
                        contentDescription = "Menu navigation icon"
                    )
                }

                val logoTransition = updateTransition(
                    targetState = backdropRevealed,
                    label = "logoTransition"
                )
                val logoOffset by logoTransition.animateDp(
                    transitionSpec = {
                        if (targetState) {
                            tween(durationMillis = 120, easing = LinearEasing)
                        } else {
                            tween(durationMillis = 270, easing = LinearEasing)
                        }
                    },
                    label = "logoOffset"
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
                targetState = backdropRevealed,
                transitionSpec = {
                    if (targetState) {
                        // Conceal to reveal
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 240,
                                delayMillis = 120,
                                easing = LinearEasing
                            )
                        ) + slideInHorizontally(
                            initialOffsetX = { with(density) { 30.dp.roundToPx() } },
                            animationSpec = tween(
                                durationMillis = 270,
                                easing = LinearEasing
                            )
                        ) with fadeOut(
                            animationSpec = tween(
                                durationMillis = 120,
                                easing = LinearEasing
                            )
                        ) + slideOutHorizontally(
                            targetOffsetX = { with(density) { (-30).dp.roundToPx() } },
                            animationSpec = tween(
                                durationMillis = 120,
                                easing = LinearEasing
                            )
                        )
                    } else {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 180,
                                delayMillis = 90,
                                easing = LinearEasing
                            )
                        ) + slideInHorizontally(
                            initialOffsetX = { with(density) { (-30).dp.roundToPx() } },
                            animationSpec = tween(
                                durationMillis = 270,
                                easing = LinearEasing
                            )
                        ) with fadeOut(
                            animationSpec = tween(
                                durationMillis = 90,
                                easing = LinearEasing
                            )
                        ) + slideOutHorizontally(
                            targetOffsetX = { with(density) { 30.dp.roundToPx() } },
                            animationSpec = tween(
                                durationMillis = 90,
                                easing = LinearEasing
                            )
                        )
                    }.using(SizeTransform(clip = false))
                },
                contentAlignment = Alignment.CenterStart
            ) { revealed ->
                if (!revealed) TopAppBarText(text = "Shrine")
                else MenuSearchField()
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search action",
                tint = LocalContentColor.current.copy(alpha = ContentAlpha.high),
                modifier = Modifier
                    .padding(end = 12.dp)
            )
        },
        elevation = 0.dp
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

@Composable
private fun MenuText(
    text: String = "Item",
    activeDecoration: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier.height(44.dp),
        contentAlignment = Alignment.Center
    ) {
        activeDecoration()
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .clip(MaterialTheme.shapes.medium)
            .then(modifier)
    ) {
        content()
    }

}

@Composable
private fun NavigationMenu(
    modifier: Modifier = Modifier,
    backdropRevealed: Boolean = true,
    activeCategory: Category = Category.All,
    onMenuSelect: (Category) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Category.values().forEachIndexed { _, category ->
            MenuItem(
                modifier = Modifier.clickable {
                    onMenuSelect(category)
                }
            ) {
                MenuText(
                    text = category.toString(),
                    activeDecoration = {
                        if (category == activeCategory) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_tab_indicator),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
        Divider(
            modifier = Modifier.width(56.dp), color = MaterialTheme.colors.onBackground
        )
        Text(text = "log out".uppercase(),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.clickable {
//                onClick(index)
            }
        )
    }
}

@Preview
@Composable
fun NavigationMenuReview() {
    ShrineTheme {
        Surface(
            color = MaterialTheme.colors.secondary
        ) {
            var activeCategory by remember { mutableStateOf(Category.All) }
            NavigationMenu(
                modifier = Modifier.padding(vertical = 8.dp),
                activeCategory = activeCategory,
                onMenuSelect = { activeCategory = it }
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Backdrop(
    onBackdropReveal: (Boolean) -> Unit = {}
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var backdropRevealed by rememberSaveable { mutableStateOf(scaffoldState.isRevealed) }
    val scope = rememberCoroutineScope()
    var menuSelection by remember { mutableStateOf(0) }
    var activeCategory by rememberSaveable { mutableStateOf(Category.All) }
    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            ShrineTopAppBar(
                backdropRevealed = backdropRevealed,
                onBackdropReveal = {
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
            )
        },
        frontLayerContent = {
//            Column(
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Text(text = menuData[menuSelection])
//            }

        },
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

