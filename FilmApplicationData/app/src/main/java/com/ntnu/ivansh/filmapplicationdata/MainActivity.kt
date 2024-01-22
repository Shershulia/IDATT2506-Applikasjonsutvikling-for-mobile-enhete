package com.ntnu.ivansh.filmapplicationdata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntnu.ivansh.filmapplicationdata.ui.theme.FilmApplicationDataTheme

class MainActivity : ComponentActivity() {
    private val colors = arrayOf("White","Red","Cyan")
    private lateinit var db: Database
    private var selectedBg = mutableStateOf(colors[0])
    private var choosenOption = mutableStateOf(0)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        db = Database(this)

        setContent {
            var author by remember { mutableStateOf("") }

            Box(
                modifier = Modifier.fillMaxSize()
            ){

                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (selectedBg.value == "White") {
                                Modifier.background(Color.White)
                            } else if (selectedBg.value == "Red") {
                                Modifier.background(Color.Red)
                            } else if (selectedBg.value == "Cyan") {
                                Modifier.background(Color.Cyan)

                            } else {
                                Modifier
                            }
                        )
                ) {
                    Background()
                    Options()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(choosenOption.value==0){
                            Text(text = "All films titles:",fontSize = 20.sp)
                            LazyColumn(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                items(db.allTitles){ title ->
                                    Text(text = title)
                                }
                            }
                        }else if(choosenOption.value==1){
                            var films by remember { mutableStateOf<List<String>>(listOf()) }
                            var previousAuthor by remember {
                                mutableStateOf("")
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                            ){
                                TextField(
                                    value = author,
                                    onValueChange = { author = it },
                                    label = { Text(text = "Write author")},
                                    modifier = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth(.7f)
                                )
                                Button(onClick = {
                                    films = db.getAllFilmsByDirector(author)
                                    previousAuthor=author
                                    author=""
                                },
                                    shape = RectangleShape,
                                    modifier = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth()) {
                                    Text(text = "Search")
                                }
                            }

                            if (films.isEmpty()){
                                Text(text = "Not films in db by such author $previousAuthor")
                            }else{
                                Text(text = "Films by author $previousAuthor",
                                    modifier = Modifier.padding(5.dp),
                                    fontSize = 20.sp)
                                LazyColumn {
                                    items(films) { filmTitle ->
                                        Text(text = filmTitle)
                                    }
                                }
                            }

                        }else if (choosenOption.value==2){
                            Text(text = "Films with actors:", fontSize = 20.sp)
                            LazyColumn(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ){
                                items(db.allFilmsWithActors){ title ->
                                    val items_properties = title.split("[")
                                    val name = items_properties[0]
                                    val actor = items_properties[1]
                                    val actors = actor.substring(0,actor.length-2)
                                    Text(text = "Film $name. Actors: $actors", modifier = Modifier.fillMaxWidth())
                                }
                            }
                        }



                    }
                }


            }
        }
    }

    @Composable
    fun Background(){
        var expanded by remember { mutableStateOf(false) }

        Row (modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(10.dp),
        ){
            Row (
                modifier=Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Selected Color: ${selectedBg.value}",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp
                )

                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },


                ) {
                    colors.forEach { item ->
                        DropdownMenuItem(
                            text = {Text(text = item)},
                            onClick = {
                                selectedBg.value = item
                                expanded = false
                            },

                        ) 
                    }
                }
            }



            }
        }
    @Composable
    fun Options(){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)

        ) {
            TextButton(
                onClick={choosenOption.value=0},
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .height(80.dp)
                    .border(1.dp, if (choosenOption.value != 0) Color.White else Color.Yellow)
                    .padding(12.dp)
            ){
                Text(text = "All films",color = if (choosenOption.value != 0) Color.White else Color.Yellow, )
            }
            TextButton(
                onClick={choosenOption.value=1},
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(80.dp)
                    .border(1.dp, if (choosenOption.value != 1) Color.White else Color.Yellow)
                    .padding(12.dp)
            ){
                Text(text = "Films by author",color = if (choosenOption.value != 1) Color.White else Color.Yellow, )
            }
            TextButton(
                onClick={choosenOption.value=2},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .border(1.dp, if (choosenOption.value != 2) Color.White else Color.Yellow)
                    .padding(12.dp)
            ){
                Text(text = "Films with actors",color = if (choosenOption.value != 2) Color.White else Color.Yellow, )
            }
        }
    }
    }





