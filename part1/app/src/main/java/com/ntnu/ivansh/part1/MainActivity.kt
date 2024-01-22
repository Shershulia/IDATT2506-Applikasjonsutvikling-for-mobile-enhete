package com.ntnu.ivansh.part1

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.ntnu.ivansh.part1.ui.theme.Part1Theme

class MainActivity : ComponentActivity() {
    private val randomRequestCode = 123;
    private var value by mutableStateOf((0..100).random())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Part1Theme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Column(modifier = Modifier.fillMaxHeight(0.8f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Button(onClick = { onClickShowToast(value) }) {
                                Text(text = "Get my random number")
                            }
                            Button(
                                onClick = {
                                    Intent(applicationContext, SecondActivity::class.java).also {
                                        it.putExtra("LIMIT", 200)
                                        startActivityForResult(it,randomRequestCode)
                                    }
                                },
                            ) {
                                Text(text = "Change scenes and get a new random number")
                            }
                            Text(text = "Current value : $value")
                        }
                        Text(
                            text = "Primary scene",
                            color = Color.Gray,
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }


    private fun onClickShowToast(value: Int) {
        Toast.makeText(this, "Random number is $value", Toast.LENGTH_LONG).show();

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            Log.e("onActivityResult()", "Noe gikk galt")
            return
        }

        if (requestCode==randomRequestCode) {
                value = data?.getIntExtra("NEW_RANDOM",0)!!;
                Toast.makeText(this, "New random number is $value", Toast.LENGTH_LONG).show();

            };
        }
}









