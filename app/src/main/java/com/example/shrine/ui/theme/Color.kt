package com.example.shrine.ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val ShrinePink10 = Color(0xfffffbfa)
val ShrinePink50 = Color(0xfffeeae6)
val ShrinePink100 = Color(0xfffedbd0)
val ShrinePink500 = Color(0xfffbb8ac)
val ShrinePink900 = Color(0xff442c2e)

internal val ShrineLightColorPalette = lightColors(
    primary = ShrinePink100,
    primaryVariant = ShrinePink500,
    secondary = ShrinePink50,
    background = ShrinePink100,
    surface = ShrinePink10,
    error = Color(0xffc5032b),
    onPrimary = ShrinePink900,
    onSecondary = ShrinePink900,
    onBackground = ShrinePink900,
    onError = ShrinePink10
)
