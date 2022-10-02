package com.example.shrine.utils

import androidx.compose.ui.graphics.painter.Painter
import com.example.shrine.R
import com.example.shrine.data.Vendor

fun getVendorResId(vendor : Vendor): Int {
    when(vendor) {
        Vendor.Alphi -> {
            return R.drawable.logo_alphi
        }
        Vendor.Lmbrjk -> {
            return R.drawable.logo_lmb
        }
        Vendor.Mal -> {
            return R.drawable.logo_mal
        }
        Vendor.Six -> {
            return R.drawable.logo_6
        }
        else -> {
            return R.drawable.logo_squiggle
        }
    }
}