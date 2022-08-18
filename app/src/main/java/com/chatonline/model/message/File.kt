package com.chatonline.model.message

data class File(
    val dateDeleted: String,
    val dateUploaded: String,
    val id: Int,
    val name: String,
    val purpose: Int,
    val size: Int
)