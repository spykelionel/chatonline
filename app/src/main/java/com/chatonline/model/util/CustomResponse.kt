package com.chatonline.model.util

class CustomResponse{

    inner class BadRequest(
        val detail: String? = "Something went wrong",
        val instance: String? = "Unknown Instance",
        val status: Int? = 400,
        val title: String? = "Bad Request",
        val type: String? = "Response"
    )

}