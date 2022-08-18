package com.chatonline.model.user

data class User(
    val authorization: String,
    val email: String,
    val id: Int,
    val password: String,
    val phoneNumber: String,
    val rememberMe: Boolean
)

data class UserDto(
    val email: String? = null,
    val phoneNumber: String? = null,
    val password: String,
    val rememberMe: Boolean = true
)

data class UserResponse(
    var authorization: String?,
    val id: Int,
    val phoneNumber: String? = null,
    val email: String? = null
)