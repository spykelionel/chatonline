package com.chatonline.view

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chatonline.model.message.MessageItem
import com.chatonline.model.message.MessageItemDto
import com.chatonline.model.message.MessageTagDto
import com.chatonline.model.room.ChatRoom
import com.chatonline.repository.MessagesRepository
import com.chatonline.ui.theme.CustomSecondary
import com.chatonline.ui.theme.CustomTertiary
import com.chatonline.ui.theme.CustomTextColor
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Messages(
    chatRoomMessages: List<MessageItem>,
    chatRoomInfo: ChatRoom?,
    navController: NavController
) {
    val context = LocalContext.current
    var showMenu by rememberSaveable { mutableStateOf(false) }
    var (showMessageMenu, setShowMessageMenu) = remember { mutableStateOf(false) }
    var (isMessageMenu, setIsMessageMenu) =  remember { mutableStateOf(false) }
    var (title, setTitle)  =  remember { mutableStateOf(chatRoomInfo?.name!!) }

    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = "$title",
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                showMenu = true
            }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Icon"
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = {
                    showMenu = false
                }
            ) {
                if(isMessageMenu){
                    DropdownMenuItem(onClick = {
                        Toast.makeText(context, "Not yet Implemented", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Copy",
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                        }

                    }
                    DropdownMenuItem(onClick = {
                        Toast.makeText(context, "Not yet Implemented!", Toast.LENGTH_SHORT)
                            .show()
                        return@DropdownMenuItem
                    }) {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Copy",
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                        }
                    }
                }

                // Not a message menu
                else {
                    DropdownMenuItem(onClick = {
                        Toast.makeText(context, "Not yet Implemented", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "View profile",
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                        }

                    }
                    DropdownMenuItem(onClick = {
                        if (chatRoomInfo != null) {
                            navController.navigate(Screen.FilteredMessageScreen.route + "?chatRoomId=${chatRoomInfo.id}")
                        }
                    }) {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Filter messages by tag",
                                modifier = Modifier.padding(horizontal = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    )


    val menuItems = listOf("Copy", "Edit", "Tag")
    Log.d("CHAT_INFO", chatRoomInfo.toString())
    val messageTagDto = chatRoomInfo?.let { MessageTagDto(chatRoomId = it.id) }
    val message = chatRoomInfo?.let {
        MessageItemDto(
            body = "Hey from kotlin. It worked",
            senderId = it.userChatRoomId,
            messageTag = messageTagDto,
            dateSent = LocalDateTime.now().toString()
        )
    }

    val chatRoomId = chatRoomInfo?.let {
        it.id
    }

 Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(.9f)
            ) {
                itemsIndexed(items = chatRoomMessages) { _, message ->
                    Spacer(modifier = Modifier.width(3.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),

                    ) {
                        val isSender: Boolean =
                            message.senderId == (chatRoomInfo?.userChatRoomId ?: false)
                        Row(modifier=Modifier

                                ){
                            NewMessage(message = message, time = "00:00", isSender = isSender,
                                modifier = Modifier.clickable {
                                    Log.d("MessageClicked", "Menu State: $isMessageMenu")
                                    setTitle("${message.id}")
                                    setIsMessageMenu(true)
                                    return@clickable
                                }
                                )
                        }

                    }
                }

        }
        WriteMessage(chatRoomInfo = chatRoomInfo, navController, modifier= Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max))
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageBox(chatRoomInfo: ChatRoom?) {
    val messageViewModel by lazy { MessagesRepository() }
    val messageTagDto = chatRoomInfo?.let { MessageTagDto(chatRoomId = it.id) }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box() {
            Row() {
                val myText = remember { mutableStateOf("Send") }
                TextField(
                    value = myText.value, onValueChange = {
                        myText.value = it
                    },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = {
                    val message = chatRoomInfo?.let {
                        MessageItemDto(
                            body = myText.value,
                            senderId = it.userChatRoomId,
                            messageTag = messageTagDto,
                            dateSent = LocalDateTime.now().toString()
                        )
                    }
                    message?.let {
//                        messageViewModel.postMessage(message)
                    }
                    chatRoomInfo?.let {
                        messageViewModel.getAllMessages(chatRoomInfo.id)
                    }
                }) {
                    Text(text = "Send")
                }
            }
        }

    }
}

@Composable
fun Message(message: MessageItem, isSender: Boolean) {
    Box(
        modifier = Modifier.shadow(
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        )
    ) {
        if (isSender) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .shadow(
                        elevation = 0.dp,
                        shape = RoundedCornerShape(10.dp)
                    ),
                horizontalArrangement = Arrangement.End,
            ) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {
                    Text(
                        text = "${message.body}",
                        modifier = Modifier
                            .background(CustomSecondary)
                            .padding(5.dp),
                        color = Color.White
                    )
                }
            }


        } else {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .shadow(
                        elevation = 0.dp,
                        shape = RoundedCornerShape(10.dp)
                    ),
                horizontalArrangement = Arrangement.End,
            ) {
                Box(
                    modifier = Modifier
                        .shadow(
                            elevation = 0.dp,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .fillMaxWidth()
                        .padding(5.dp)

                ) {
                    Text(
                        text = "${message.body}",
                        modifier = Modifier
                            .background(CustomTertiary)
                            .padding(5.dp),
                        color = Color.White
                    )
                }
            }

        }

    }
}

@Composable
fun NewMessage(message: MessageItem?, time: String, isSender: Boolean, modifier: Modifier) {
    val BotChatBubbleShape = RoundedCornerShape(0.dp, 30.dp, 30.dp, 8.dp)
    val AuthorChatBubbleShape = RoundedCornerShape(30.dp, 0.dp, 8.dp, 30.dp)
    val hasBody: Boolean = message?.body !== null
    val context = LocalContext.current

    if (hasBody) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .clickable {
                    Toast
                        .makeText(context, "Not Implemented!", Toast.LENGTH_SHORT)
                        .show()
                    return@clickable
                }
            ,
            horizontalAlignment = if (isSender) Alignment.End else Alignment.Start
        ) {
            if (!isSender) {
                Text(
                    text = message?.senderName!!,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 3.dp),
                    fontSize = 15.sp
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        if (isSender) CustomSecondary else CustomTertiary,
                        shape = if (isSender) AuthorChatBubbleShape else BotChatBubbleShape
                    )
                    .padding(
                        top = 5.dp,
                        bottom = 8.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                    .widthIn(0.dp, 320.dp)

            ) {

                Column(
                    modifier = modifier
                ) {
                    message?.body?.apply {
                            Text(
                                text = this,
                                color = Color.Black
                            )
                        val topics = listOf("topic1", "topic2", "topic3")
                        LazyRow(){
                            itemsIndexed(items = topics){index, topic ->
                                Text(
                                    text = "#$topic",
                                    color = Color.Magenta,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight(500)
                                )
                                if (topics.size - 1 != index){
                                    Text(
                                        text = ", ",
                                        color = Color.Magenta,
                                        fontSize = 15.sp
                                    )
                                }

                            }
                        }

                        // This will fail as of now(No messageTags available in messages)
                        message.messageTag?.apply {
                            Text(
                                text = this.name,
                                color = Color.Magenta
                            )
                        }
                    }
                }
            }
            Text(
                text = time,
                fontSize = 13.sp,
                color = CustomTextColor,
                modifier = Modifier.padding(start = 0.dp)
            )
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WriteMessage(chatRoomInfo: ChatRoom?, navController: NavController, modifier:Modifier =Modifier) {
    val messageViewModel by lazy { MessagesRepository() }
    val context = LocalContext.current
    val textMessage = remember { mutableStateOf("") }
    Card(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        OutlinedTextField(
            value = textMessage.value,
            onValueChange = {
                textMessage.value = it
            },
            shape = RoundedCornerShape(25.dp),
            placeholder = {
                Text(text = "Message", fontStyle = FontStyle(1))
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.AttachFile,
                    contentDescription = "Send Icon, Click to send",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "Unimplemented!", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            trailingIcon = {
                if (textMessage.value.isNotEmpty() || textMessage.value!="") {
                    Icon(
                        Icons.Rounded.Send,
                        contentDescription = "Send Icon, Click to send",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.clickable {
                            // send message.
                            val messageTagDto =
                                chatRoomInfo?.let { MessageTagDto(chatRoomId = it.id) }
                            val message = chatRoomInfo?.let {
                                MessageItemDto(
                                    body = textMessage.value.trim(),
                                    senderId = it.userChatRoomId,
                                    messageTag = messageTagDto,
                                    dateSent = LocalDateTime.now().toString()
                                )
                            }
                            if (message != null) {
                                if(textMessage.value!=""){
                                    messageViewModel.postMessage(textMessage.value.trim())
                                    Toast.makeText(context, "${textMessage.value} Sent: Message Count: ${messageViewModel.listOfMessages.size}", Toast.LENGTH_SHORT)
                                        .show()
//                                    val chatRoom =
//                                        navController.previousBackStackEntry?.savedStateHandle?.get<ChatRoom>("chatRoomInfo")
//                                    val id: Int? = chatRoom?.id
                                    navController.popBackStack()
//                                    navController.navigate(Screen.MessageScreen.route + "?chatRoomId=${chatRoomInfo.id}")
//                                    navController.navigate(Screen.MessageScreen.route + "?chatRoomId=${id}")
                                    textMessage.value = ""
//                                    Log.d("ChatRoom", "$chatRoom")
                                } else {
                                    Toast.makeText(context, "Message cannot be empty!", Toast.LENGTH_SHORT)
                                        .show()
                                }

                            }

                        }

                    )
                } else {
                    Icon(
                        Icons.Rounded.Send,
                        contentDescription = "Send Icon disabled",
                        tint = Color.Gray,
                        modifier = Modifier.clickable {
                            Toast.makeText(
                                context,
                                "Message can not be empty!",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@clickable
                        }

                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding((10.dp))
        )
    }
}


@Composable
fun FilteredMessages(chatRoomMessages: List<MessageItem>, tag: String){
val filteredMessages = chatRoomMessages.filter { it.body?.contains(tag, ignoreCase = true) ?: false }
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(.9f)
        ) {

            itemsIndexed(items = filteredMessages) { _, message ->
                Spacer(modifier = Modifier.width(3.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                    ) {
                            Text(
                                text = message.senderName,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 3.dp),
                                fontSize = 15.sp
                            )
                        Box(
                            modifier = Modifier
                                .background(CustomSecondary)
                                .padding(
                                    top = 5.dp,
                                    bottom = 8.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                )
                                .widthIn(0.dp, 320.dp)

                        ) {

                            Column() {
                                message.body?.apply {
                                        Text(
                                            text = this,
                                            color = Color.Black
                                        )
                                }

                            }
                        }
                        Text(
                            text = message.dateSent,
                            fontSize = 13.sp,
                            color = CustomTextColor,
                            modifier = Modifier.padding(start = 0.dp)
                        )
                    }
                }
            }
        }
    }
}

