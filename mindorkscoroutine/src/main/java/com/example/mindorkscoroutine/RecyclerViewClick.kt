package com.example.mindorkscoroutine

interface RecyclerViewClick {
    fun onItemClick(position: Int)
    fun onLongItemClick(position: Int): Boolean
}