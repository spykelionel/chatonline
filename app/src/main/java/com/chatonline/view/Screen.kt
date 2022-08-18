package com.chatonline.view

sealed class Screen(val route: String) {
    object ChatRoomScreen : Screen("chat_room")
    object MessageScreen : Screen("message")
    object FilteredMessageScreen : Screen("filtered_message")
    object ProfileScreen : Screen("profile")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append(it)
            }
        }
    }
}
