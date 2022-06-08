package com.example.cookbookht.extension

import androidx.lifecycle.MutableLiveData
import com.example.cookbookht.utils.Resource

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.add(item)
    this.value = value
}

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: MutableList<T>) {
    val value = this.value ?: mutableListOf()
    value.addAll(item)
    this.postValue(value)
}

fun <T> MutableLiveData<Resource<MutableList<T>>>.plusAssignResource(item: MutableList<T>) {
    val value = this.value?.data ?: mutableListOf()
    value.addAll(item)
    this.postValue(Resource.success(value))
}
