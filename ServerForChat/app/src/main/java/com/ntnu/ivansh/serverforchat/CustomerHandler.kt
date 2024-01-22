package com.ntnu.ivansh.serverforchat

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.concurrent.CopyOnWriteArrayList

class CustomerHandler(private val clientSocket: Socket,
            private var logs: MutableList<String>,
                      private val connectedClients: CopyOnWriteArrayList<Socket>


) {

    fun start() {
        CoroutineScope(Dispatchers.IO).launch {

            try {
                logs += ("En Klient koblet seg til:\n$clientSocket")
                sendToClient("Connected")
                while (true) {
                    val msg = readFromClient()
                    if (msg != null) {
                        broadcastMessage(msg)
                    }
                }

        } catch (e: Exception) {
            e.printStackTrace()
            logs += (e.message.toString())
        }


    }
}

    private fun readFromClient(): String? {
        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        val message = reader.readLine()
        logs+=("Klienten sier:\n$message")
        return message
    }

    private fun sendToClient(message: String) {
        val writer = PrintWriter(clientSocket.getOutputStream(), true)
        writer.println(message)
        logs+=("Sendte følgende til klienten:\n$message")
    }

    private fun broadcastMessage(message: String) {
        for (client in connectedClients) {
            try {
                val writer = PrintWriter(client.getOutputStream(), true)
                writer.println("Port " + clientSocket.port.toString() + " sier : " +message + "\n")
            } catch (e: Exception) {
                e.printStackTrace()
                logs += ("Error sending to client: ${e.message}")
            }
        }
    }
}
