package com.example.a4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    private val _order = MutableLiveData(Order())
    val order: LiveData<Order> = _order

    fun setMainMeal(meal: String?) {
        val current = _order.value ?: Order()
        current.mainMeal = meal
        _order.value = current
    }

    fun setSideDishes(dishes: List<String>) {
        val current = _order.value ?: Order()
        current.sideDishes.clear()
        current.sideDishes.addAll(dishes)
        _order.value = current
    }

    fun setDrink(drink: String?) {
        val current = _order.value ?: Order()
        current.drink = drink
        _order.value = current
    }

    fun resetOrder() {
        _order.value = Order()
    }
}
