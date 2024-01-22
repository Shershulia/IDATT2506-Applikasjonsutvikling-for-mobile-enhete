package com.ntnu.ivansh.part1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.ntnu.ivansh.part1.ui.theme.Part1Theme

class SecondActivity : ComponentActivity() {
    private var limit = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        limit = intent.getIntExtra("LIMIT",100);

        val value = (0..limit).random()
//        setResult(RESULT_OK, Intent().putExtra("NEW_RANDOM", value));
//        finish()

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
                                Button(onClick = { onClickShowToast(value)} ) {
                                    Text(text = "Get a new random number")
                                }
                                Button(
                                    onClick = {
                                        setResult(RESULT_OK,
                                            Intent().putExtra("NEW_RANDOM", value))
                                        finish();},
                                ) {
                                    Text(text = "Change to primary scene to save number")
                                }
                                Text(text = "New value : $value")

                            }

                            Text(
                                text = "Second scene. Limit $limit",
                                color = Color.Gray,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
            }

    }

    private fun onClickShowToast(value: Int) {
        Toast.makeText(this, "Random number is $value", Toast.LENGTH_LONG).show()

    }
}