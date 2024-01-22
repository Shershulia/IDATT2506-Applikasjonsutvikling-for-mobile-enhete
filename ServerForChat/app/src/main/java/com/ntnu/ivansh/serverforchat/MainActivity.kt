package com.ntnu.ivansh.serverforchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.CopyOnWriteArrayList

class MainActivity : ComponentActivity() {
    private val PORT: Int = 12345;
    private val logs = mutableStateListOf<String>()
    private val connectedClients = CopyOnWriteArrayList<Socket>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            start()
        }

        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                Column (verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Logs", fontSize = 32.sp)
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        LazyColumn (
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            items(logs){ message ->
                                Message(msg = message)
                            }
                        }
                    }
                }

            }
        }
    }

    @Composable
    fun Message(msg:String){
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize(0.7f)
                .padding(4.dp)
                .border(1.dp, Color.Black, RectangleShape)
                .padding(2.dp)
        ){
            Text(text = msg, textAlign = TextAlign.Center, fontSize = 16.sp)
        }
    }

    private fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            logs+=("Starter Tjener ...")
            withContext(Dispatchers.IO) {
                ServerSocket(PORT).use { serverSocket: ServerSocket ->
                    logs += ("ServerSocket opprettet, venter paa at en klient kobler seg til....")
                    while (true) {
                        val clientSocket = serverSocket.accept()
                        connectedClients.add(clientSocket)
                        CustomerHandler(clientSocket, logs, connectedClients).start()

                    }
                }
            }
        }
    }

}