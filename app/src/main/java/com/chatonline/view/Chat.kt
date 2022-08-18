package com.chatonline.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.chatonline.R
import com.chatonline.model.room.ChatRoom
import com.chatonline.repository.ChatRoomsRepository
import com.chatonline.ui.theme.CustomTextColor
import com.chatonline.util.Helper

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatRoomList(chatRooms: List<ChatRoom>, navController: NavController) {
    val chatRoomViewModel by lazy { ChatRoomsRepository() }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            LazyColumn {
                itemsIndexed(items = chatRooms) { index, room ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "chatRoomInfo",
                                    room
                                )
                                navController.navigate(Screen.MessageScreen.route + "?chatRoomId=${room.id}")
                            }
                            .fillMaxWidth()
                    ) {
                        ChatRoomItem(chatRoom = room)
                    }
                    if (chatRooms.size - 1 != index)
                        Divider()
                }
            }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatRoomItem(chatRoom: ChatRoom?) {

    val painter = rememberAsyncImagePainter(R.drawable.user)

    var checkmark = "";
    chatRoom?.latestMessage?.apply {
        if (this.notReceivedCount!! > 0) {
            checkmark = "\u2713";
        } else if (this.notReadCount!! > 0) {
            checkmark = "\u2713\u2713";
        } else if (this.id != 0) {
            checkmark = "\u2713\u2713\u2713";
        }
    }

    val containsLatestMessage: Boolean = chatRoom?.latestMessage?.shortBody != null

    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Surface() {
            if (chatRoom != null) {
                Row(
                    Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Chat Room Icon",
                        modifier = Modifier
                            .fillMaxHeight()
                            .size(45.dp)
                            .clip(CircleShape)
                    )
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxHeight()
                            .weight(0.8f)
                    ) {
                        Text(
                            text = chatRoom.name,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )

                        chatRoom.latestMessage?.let {
                            if (it.shortBody == null) {
                                Text(
                                    text = "(no message)",
                                    style = MaterialTheme.typography.body1,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontStyle = FontStyle(1)
                                )
                            }
                            it.shortBody?.let { shortBody ->
                                Text(
                                    text = shortBody,
                                    fontSize = 13.sp,
                                    style = MaterialTheme.typography.body1,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = CustomTextColor
                                )
                            }
                        }
                    }
                    if (containsLatestMessage)
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(4.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(text = Helper().toLocalDate(chatRoom.latestMessage?.dateSent!!, context = 0))
                            Text(text = checkmark)
                        }

                }
            }

        }
    }

}