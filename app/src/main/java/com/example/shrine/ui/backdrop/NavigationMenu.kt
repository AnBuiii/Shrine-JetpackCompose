package com.example.shrine.ui.backdrop

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrine.R
import com.example.shrine.data.Category
import com.example.shrine.ui.theme.ShrineTheme

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

@ExperimentalAnimationApi
@Composable
private fun AnimatedVisibilityScope.MenuItem(
    modifier: Modifier = Modifier,
    index: Int,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .animateEnterExit(
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 240,
                        delayMillis = index * 15 + 30,
                        easing = LinearEasing
                    )
                ),
                exit = fadeOut(
                    animationSpec = tween(
                        durationMillis = 90,
                        easing = LinearEasing
                    )
                ),
                label = "Menu item $index"
            )
            .fillMaxWidth(0.5f)
            .clip(MaterialTheme.shapes.medium)
            .then(modifier)
    ) {
        content()
    }

}

@ExperimentalAnimationApi
@Composable
internal fun NavigationMenu(
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
        AnimatedVisibility(
            visible = backdropRevealed,
            enter = EnterTransition.None,
            exit = ExitTransition.None,
            label = "NavigationMenuTransition"
        ) {
            Column {
                Category.values().forEachIndexed { idx, category ->
                    MenuItem(
                        index = idx,
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
                MenuItem(index = Category.values().size) {
                    Divider(
                        modifier = Modifier
                            .width(56.dp)
                            .padding(vertical = 12.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                    )
                }
                MenuItem(index = Category.values().size + 1) {
                    MenuText("Logout")
                }
            }
        }

    }
}

@ExperimentalAnimationApi
@Preview
@Composable
private fun NavigationMenuReview() {
    ShrineTheme {
        Surface(
            color = MaterialTheme.colors.background
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