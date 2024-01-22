package com.ntnu.ivansh.filmapplication

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

@Composable
fun List (films : List<Film>,modifier: Modifier = Modifier,onClick: (Int) -> Unit, chosen:Int){
    val context = LocalContext.current
    val contentColor = ContextCompat.getColor(context, R.color.teal_200)

    LazyColumn{
        itemsIndexed(films){index,it ->
            Column (
                modifier= Modifier.clickable { onClick(index) }
                .then(if (chosen == index) Modifier.background(Color(contentColor)) else Modifier),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (
                    modifier= modifier
                        .fillMaxWidth()
                        .padding(2.dp, 4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = it.image) , contentDescription = "Image of ${it.name}",
                        modifier = Modifier
                            .width(170.dp)
                            .height(140.dp)
                            .padding(0.dp, 5.dp))
                    Column{
                        Text(text = it.name,fontSize = 28.sp, color = Color.Black,
                            maxLines = 3, overflow = TextOverflow.Ellipsis,)
                        Text(text = it.description,fontSize = 14.sp,
                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                            color = Color.DarkGray)
                    }


                }
                if(index!=films.size-1){
                    Spacer(modifier = Modifier.fillMaxWidth(0.7f).height(1.dp).background(Color.LightGray))
                }
            }

        }
    }
}