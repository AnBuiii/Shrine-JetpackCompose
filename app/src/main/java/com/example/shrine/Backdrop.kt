package com.example.shrine

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrine.ui.theme.ShrineTheme
import kotlinx.coroutines.launch

val menuData = listOf("feature", "apartment", "accessories", "shoes", "tops", "bottoms", "dress")

@ExperimentalMaterialApi
@Composable
fun Backdrop() {
    val scope = rememberCoroutineScope()
    var backDropState = rememberBackdropScaffoldState(BackdropValue.Revealed)
    var menuSelection by remember { mutableStateOf(0) }
    BackdropScaffold(
        scaffoldState = backDropState,
        appBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_menu_cut_24px),
                            contentDescription = "Menu icon",
                            modifier = Modifier.clickable {
                                scope.launch {
                                    if (backDropState.isConcealed) {
                                        backDropState.reveal()
                                    } else {
                                        backDropState.conceal()
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "shrine".uppercase(),
                            style = MaterialTheme.typography.subtitle1
                        )
                    }
                },
                elevation = 0.dp,
                actions = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search action"
                    )

                }
            )
        },
        frontLayerContent = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = menuData[menuSelection])
            }

        },
        backLayerContent = {
            BackDropMenuItems(
                activeMenuItem = menuSelection,
                onMenuItemSelect = {
                    menuSelection = it
                }
            )
        },
    )
}

@Composable
private fun BackDropMenuItems(
    activeMenuItem: Int = -1,
    onMenuItemSelect: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        menuData.forEachIndexed { idx, item ->
            MenuItem(
                index = idx,
                text = item,
                activeMenu = activeMenuItem
            ) {
                onMenuItemSelect(it)
            }
        }
        Divider(
            modifier = Modifier.width(56.dp),
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = "my account".uppercase(),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.clickable {
//                onClick(index)
            }
        )
    }
}

@Composable
fun MenuItem(
    index: Int = -1,
    text: String = "Menu item",
    activeMenu: Int = -1,
    onClick: (Int) -> Unit = {}
) {
    Box(
        modifier = Modifier.height(20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (activeMenu == index) {
            Image(
                painter = painterResource(id = R.drawable.ic_tab_indicator),
                contentDescription = null
            )
        }
        Text(
            text.uppercase(),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.clickable {
                onClick(index)
            }
        )
    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun BackdropPreview() {
    ShrineTheme {
        Backdrop()
    }
}
