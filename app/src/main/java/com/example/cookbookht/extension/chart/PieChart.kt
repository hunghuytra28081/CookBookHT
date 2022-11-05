package com.example.cookbookht.extension.chart

import com.example.cookbookht.data.model.Nutrient

fun nutrientToGam(unit: String, amount: Double): Float {
    var gam = 0.0
    when (unit) {
        "mg" -> {
            gam = amount / 1000
        }
        "µg" -> {
            gam = amount / 1000000
        }
        "IU" -> {
            gam = amount / 30000000
        }
        "g" -> {
            gam = amount
        }
        else -> {
            gam = 0.0
        }
    }
    return gam.toFloat()
}

//fun listHighestValue(listData: MutableList<Nutrient>){
//    if (listData.size > 4){
//
//    }
//    else
//}