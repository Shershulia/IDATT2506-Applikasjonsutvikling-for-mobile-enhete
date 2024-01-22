package com.ntnu.ivansh.filmapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@Composable
fun Navigation(next: () -> Unit, previous: () -> Unit, modifier: Modifier = Modifier){
    val context = LocalContext.current
    val contentColor = ContextCompat.getColor(context, R.color.purple_500)
    val backgroundColor = ContextCompat.getColor(context, R.color.purple_200)
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(backgroundColor))
            .padding(10.dp,0.dp)
            .then(modifier)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Film application",
            fontSize = 16.sp,
            modifier = Modifier.padding(2.dp).fillMaxWidth(0.3f),
            textAlign = TextAlign.Center)
        Row (

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick =  previous  ,
                modifier = Modifier.fillMaxWidth(0.5f).padding(1.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(contentColor)
                ),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color.Yellow)
            ) {
                Text(text = "Previous", fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Button(onClick =  next ,
                modifier = Modifier.fillMaxWidth().padding(1.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(contentColor)
                ),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color.Yellow)
            ) {
                Text(text = "Next", fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }

}