package com.chatonline.util

interface Event {
    fun updateEvent(type: Object, id: Int)
    fun getEvent(type: String): String
    fun setEvent(type: String)
    fun freezeEvent(type: String, id: Int)
}