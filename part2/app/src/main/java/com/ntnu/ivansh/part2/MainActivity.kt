package com.ntnu.ivansh.part2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val randomRequestCode = 123;
    private var valueOne by  mutableStateOf(3)
    private var valueTwo by  mutableStateOf(5)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                var answerState by remember { mutableStateOf("8") }
                var limitState by remember { mutableStateOf("10") }

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(text = "Value one : $valueOne",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center)
                            Text(text = "Value two : $valueTwo",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center)
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            TextField(
                                value = answerState,
                                modifier = Modifier.weight(1f),
                                textStyle = TextStyle(textAlign = TextAlign.Center),
                                onValueChange = { text-> answerState=text },
                                label = { Text(text = getString(R.string.answ),
                                    modifier = Modifier.padding(5.dp))
                                }
                            )
                            TextField(
                                modifier = Modifier.weight(1f),
                                textStyle = TextStyle(textAlign = TextAlign.Center),
                                value = limitState   ,
                                onValueChange = { text-> limitState=text },
                                label = { Text(text = getString(R.string.limit)
                                    , modifier = Modifier.padding(5.dp)) }
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                handleOnClick(Operation.ADD,valueOne,valueTwo,answerState,limitState)
                                             },
                                modifier = Modifier.weight(1f).padding(20.dp,5.dp),
                                ) {
                                Text(text = getString(R.string.adds))
                            }

                            Button(onClick = {
                                handleOnClick(Operation.MULT,valueOne,valueTwo,answerState,limitState)
                            },
                                modifier = Modifier.weight(1f).padding(20.dp,5.dp),
                            ) {
                                Text(text = getString(R.string.mults))
                            }
                        }
                    }
                }

        }
    }
    private fun handleOnClick(operation:Operation, first: Int, second: Int, answ:String, limit : String) {
        if(operation == Operation.ADD){
            var rightAnswer : Int = first+second;
            if (rightAnswer==answ.toInt()){
                Toast.makeText(applicationContext,
                    R.string.right,
                    Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext,
                    getString(R.string.fail) +" "+rightAnswer ,
                    Toast.LENGTH_SHORT).show()
            }
        }else{
            var rightAnswer : Int = first*second;
            if (rightAnswer==answ.toInt()){
                Toast.makeText(applicationContext,
                    R.string.right,
                    Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext,
                    getString(R.string.fail) +" "+rightAnswer ,
                    Toast.LENGTH_SHORT).show()
            }
        }
        Intent(applicationContext, RandomActivity::class.java).also {
            it.putExtra("LIMIT", limit.toInt())
            startActivityForResult(it,randomRequestCode)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            Log.e("onActivityResult()", "Noe gikk galt")
            return
        }

        if (requestCode==randomRequestCode) {
            valueOne = data?.getIntExtra("FIRST_RANDOM",0)!!;
            valueTwo = data?.getIntExtra("SECOND_RANDOM",0)!!;
        };
    }
}
enum class Operation {
    MULT,ADD
}

