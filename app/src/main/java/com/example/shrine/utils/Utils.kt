package com.example.shrine.utils

import com.example.shrine.R
import com.example.shrine.data.Vendor

fun getVendorResId(vendor: Vendor): Int {
    when (vendor) {
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

fun <T> transformToWeavedList(items: List<T>): List<List<T>> {
    var i = 0
    val list = mutableListOf<List<T>>()
    while (i < items.size) {
        val even = i % 3 == 0
        val wList = mutableListOf<T>()
        wList.add(items[i])
        if (even && i + 1 < items.size) wList.add(items[i + 1])
        list.add(wList.toList())
        i += if (even) 2 else 1
    }
    return list.toList()
}