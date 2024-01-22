package com.ntnu.ivansh.ving5http

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONArray

var URL = "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp"
class MainActivity : ComponentActivity() {
    private val network: HttpWrapper = HttpWrapper(URL)

    private var name by mutableStateOf("");
    private var card by mutableStateOf("");
    private var value by mutableStateOf("");
    private var response by mutableStateOf("");


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier= Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = response, fontSize = 32.sp)

                    if(response==""
                        || response.contains("ingen flere sjanser")
                        || response.contains("du har vunnet")) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            FirstRequest()
                            Button(onClick = {
                                performRequest(
                                    HTTP.GET_WITH_HEADER,
                                    mapOf("navn" to name, "kortnummer" to card)
                                )
                            }) {
                                Text(text = "Send info")
                            }
                        }
                    }else{
                        GuessNumber();
                        Button(onClick = {
                            performRequest(
                                HTTP.GET_WITH_HEADER,
                                mapOf("tall" to value)
                            )
                        }) {
                            Text(text = "Send number")
                        }
                    }
                }
                

            }
        }
    }

    private fun performRequest(typeOfRequest: HTTP, parameterList: Map<String, String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val responseSer: String = try {
                when (typeOfRequest) {
                    HTTP.GET -> network.get(parameterList)
                    HTTP.POST -> network.post(parameterList)
                    HTTP.GET_WITH_HEADER -> network.getWithHeader(parameterList)
                }
            } catch (e: Exception) {
                Log.e("performRequest()", e.message!!)
                e.toString()
            }

            // Endre UI på hovedtråden
            MainScope().launch {
                Log.d("response",responseSer)
                response=responseSer
                //setResult(formatJsonString(response))
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun FirstRequest(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(0.dp,20.dp),
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Enter your name", fontSize = 30.sp)
                TextField(value = name, onValueChange = {name=it})
            }
            Column (verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Enter your card", fontSize = 30.sp)
                TextField(value = card, onValueChange = {card=it})
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GuessNumber(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Enter number", fontSize = 30.sp)
                TextField(value = value, onValueChange = {value=it})
            }
        }
    }
    private fun formatJsonString(str: String): String {
        return try {
            JSONArray(str).toString(4)
        } catch (e: Exception) {
            Log.e("formatJsonString()", e.toString())
            e.message!!
        }
    }



}




