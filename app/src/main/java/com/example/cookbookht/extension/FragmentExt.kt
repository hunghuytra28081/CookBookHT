package com.example.cookbookht.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(id: Int, fragment: Fragment) {
    supportFragmentManager.inTransaction { add(id, fragment) }
}

fun Fragment.addFragment(id: Int, fragment: Fragment) {
    fragmentManager?.inTransaction {
        add(id, fragment)
        addToBackStack(null)
    }
}
