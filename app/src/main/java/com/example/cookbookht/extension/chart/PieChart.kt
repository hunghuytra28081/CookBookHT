package com.example.cookbookht.extension.chart

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
//        "kcal" -> {
//            gam = amount * 0.033
//        }
        else -> {
            gam = 0.0
        }
    }
    return gam.toFloat()
}