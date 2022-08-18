package com.chatonline.view

import android .os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.chatonline.model.room.ChatRoom
import com.chatonline.repository.ChatRoomsRepository
import com.chatonline.repository.MessagesRepository

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val chatRoomViewModel by lazy { ChatRoomsRepository() }
    val messageViewModel by lazy { MessagesRepository() }
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ChatRoomScreen.route) {

        composable(route = Screen.ChatRoomScreen.route) {
            Column {
                var showMenu by rememberSaveable { mutableStateOf(false) }
                TopAppBar(
                    title = {
                        Text(
                            text = "ChatOnline",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                            ),
                            color = Color.LightGray,
                            fontSize = 25.sp
                        )
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
                            DropdownMenuItem(onClick = { /*TODO*/ }) {
                                Row(
                                    horizontalArrangement = Arrangement.Center
                                ) {

                                    Text(
                                        text = "Create New Private Conversation",
                                        modifier = Modifier
                                            .padding(horizontal = 5.dp)
                                            .clickable {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Not Implemented!",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }
                                    )
                                }

                            }
                            DropdownMenuItem(onClick = { /*TODO*/ }) {
                                Row(
                                    horizontalArrangement = Arrangement.Center
                                ) {

                                    Text(
                                        text = "Create New Group Conversation",
                                        modifier = Modifier
                                            .padding(horizontal = 5.dp)
                                            .clickable {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Not Implemented!",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }
                                    )
                                }
                            }
                        }
                    }
                )

                // chatRoomViewModel.getAllChatRooms()
                ChatRoomList(
                    chatRooms = chatRoomViewModel.getAllChatRooms(),
                    navController = navController
                )
            }
        }
        composable(
            route = Screen.MessageScreen.route + "?chatRoomId={chatRoomId}",
            arguments = listOf(
                navArgument("chatRoomId") {
                    type = NavType.IntType
                    defaultValue = 1
                    nullable = false
                }
            )
        ) {
            val chatRoomInfo =
                navController.previousBackStackEntry?.savedStateHandle?.get<ChatRoom>("chatRoomInfo")
            val id: Int? = chatRoomInfo?.id
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "chatRoomInfo",
                chatRoomInfo
            )
            Column {
                var showMenu by rememberSaveable { mutableStateOf(false) }
                var showMessageMenu by rememberSaveable { mutableStateOf(false) }

                /*
                if(true){
                    TopAppBar(
                        title = {
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Column(
                                    modifier = Modifier.padding(5.dp)
                                ) {
                                    Text(
                                        text = chatRoomInfo?.name!!,
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
                                    navController.navigate(Screen.FilteredMessageScreen.route + "?chatRoomId=${id}")
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
                    )
                }
                */

                if (id != null) {
                    Messages(messageViewModel.getAllMessages(id), chatRoomInfo, navController)
                } else {
                    Text(
                        text = "NO ID",
                        color = Color.Red
                    )
                }
            }
        }

        composable(
            route = Screen.FilteredMessageScreen.route + "?chatRoomId={chatRoomId}",
            arguments = listOf(
                navArgument("chatRoomId") {
                    type = NavType.IntType
                    defaultValue = 1
                    nullable = false
                }
            )
        ) {
            val textMessage = remember { mutableStateOf("") }
            val chatRoomInfo =
                navController.previousBackStackEntry?.savedStateHandle?.get<ChatRoom>("chatRoomInfo")
            val id: Int? = chatRoomInfo?.id
            Column {

                TopAppBar(
                    title = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Text(
                                    text = "Filter Messages",
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
                    }
                )
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(modifier = Modifier) {
                    Card(
                        modifier = Modifier,
                        backgroundColor = Color.Transparent,
                        elevation = 0.dp,

                        ){
                        OutlinedTextField(
                            modifier = Modifier
//                                .fillMaxHeight(.05f)
                                .fillMaxWidth(.5f),
                            value = textMessage.value,
                            onValueChange = {
                                textMessage.value = it
                            },
                            placeholder = {
                                Text(text= "Search by tags")
                            },
                            trailingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Send Icon, Click to send",
                                    tint = if(textMessage.value.isNotEmpty())MaterialTheme.colors.primary else Color.Gray,
                                    modifier = Modifier.clickable {
                                        Toast.makeText(
                                            context,
                                            "Unimplemented!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        )
                    }

                    }
                }

                Column(){
                    val filtered = id?.let { it1 -> messageViewModel.getAllMessages(it1).filter { it.body?.contains("", ignoreCase = true)
                        ?:false } }
//                    LazyColumn( modifier = Modifier) {
//                       if(filtered != null){
//                           itemsIndexed(items = filtered) { _, message ->
//                               Spacer(modifier = Modifier.width(3.dp))
//                               Row(
//                                   modifier = Modifier
//                                       .fillMaxWidth()
//                               ) {
//                                   Column(
//                                       modifier = Modifier
//                                           .fillMaxWidth()
//                                           .padding(vertical = 5.dp),
//                                   ) {
//                                       Text(
//                                           text = message.senderName,
//                                           color = Color.Gray,
//                                           modifier = Modifier.padding(bottom = 3.dp),
//                                           fontSize = 15.sp
//                                       )
//                                       Box(
//                                           modifier = Modifier
//                                               .background(CustomSecondary)
//                                               .padding(
//                                                   top = 5.dp,
//                                                   bottom = 8.dp,
//                                                   start = 10.dp,
//                                                   end = 10.dp
//                                               )
//                                               .widthIn(0.dp, 320.dp)
//
//                                       ) {
//
//                                           Column() {
//                                               message.body?.apply {
//                                                   Text(
//                                                       text = this,
//                                                       color = Color.Black
//                                                   )
//                                               }
//
//                                           }
//                                       }
//                                       Text(
//                                           text = message.dateSent,
//                                           fontSize = 13.sp,
//                                           color = CustomTextColor,
//                                           modifier = Modifier.padding(start = 0.dp)
//                                       )
//                                   }
//                               }
//                           }
//
//                       }                       }

                }
                Divider(modifier = Modifier)
                if (id != null) {
                    FilteredMessages(chatRoomMessages = messageViewModel.getAllMessages(id), tag=textMessage.value.trim())
                } else {
                    Text(
                        text = "NO ID: ${chatRoomInfo?.name}",
                        color = Color.Red
                    )
                }
            }
        }

    }

}