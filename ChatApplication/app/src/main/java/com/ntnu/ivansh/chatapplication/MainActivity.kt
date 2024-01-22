package com.ntnu.ivansh.chatapplication

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class MainActivity : ComponentActivity() {
    private var message by mutableStateOf("");
    private var db = mutableStateListOf<String>()


    private val SERVER_IP: String = "10.0.2.2"
    private val SERVER_PORT: Int = 12345

    private var socket2: Socket? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                Column (verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    YourMessage()
                    Chat()
                }

            }
        }
    }
    private fun readFromServer(socket: Socket) {
        val reader =
            BufferedReader(InputStreamReader(socket.getInputStream()))
        val message = reader.readLine()
        if (message!==null){
        db += "\n$message"}
    }
    private fun sendToServer(socket: Socket?, message: String) {
        val writer = PrintWriter(socket?.getOutputStream(), true)
        try {
            writer.println(message)
            db += "Sendte foelgende til tjeneren: \n\"$message\""
        }catch (e: Exception){
            e.printStackTrace()
            db += e.toString()
            db+=socket.toString()
        }
    }

    private fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            db +=  "Kobler til tjener..."
            try {
                withContext(Dispatchers.IO) {
                    Socket(SERVER_IP, SERVER_PORT).use { socket: Socket ->
                        db += "Koblet til tjener:\n$socket"
                        socket2 = socket

                        readFromServer(socket)


                        while (true) {
                            readFromServer(socket)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                db += e.message.toString()
            }
        }

    }





    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun YourMessage() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter your message",
                modifier = Modifier.padding(10.dp),
                fontSize = 30.sp
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(value = message, onValueChange = { message = it })
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            sendToServer(socket2, message)
                            message=""
                        }},
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(Color.Green)
                ) {
                    Text(
                        text = "Send message", color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    @Composable
    fun Chat() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Chat",
                modifier = Modifier.padding(10.dp),
                fontSize = 42.sp
            )
            LazyColumn (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                items(db){ message ->
                    Message(msg = message)
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
}

