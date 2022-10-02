package com.example.shrine.backdrop

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrine.data.Category
import com.example.shrine.ui.theme.ShrineScrimColor
import com.example.shrine.ui.theme.ShrineTheme
import kotlinx.coroutines.launch


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun Backdrop(
    onBackdropReveal: (Boolean) -> Unit = {}
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var backdropRevealed by rememberSaveable { mutableStateOf(scaffoldState.isRevealed) }
    val scope = rememberCoroutineScope()
    var activeCategory by rememberSaveable { mutableStateOf(Category.All) }
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
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(text = activeCategory.toString())
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

