package com.ntnu.ivansh.filmapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@Composable
fun BigScreen(film:Film, modifier: Modifier = Modifier){
    val context = LocalContext.current
    val backgroundColor = ContextCompat.getColor(context, R.color.purple_200)

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color(backgroundColor)).then(modifier)
    ){
        Image(painter = painterResource(id = film.image),
            contentDescription = film.name,
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth(0.4f))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = film.name,fontSize = 36.sp, color = Color.Black, textAlign = TextAlign.Center
                )
            Text(text = film.description,fontSize = 16.sp,
                color = Color.DarkGray, textAlign = TextAlign.Center)
        }
    }
}