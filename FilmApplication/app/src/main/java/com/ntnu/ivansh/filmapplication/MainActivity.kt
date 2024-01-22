package com.ntnu.ivansh.filmapplication

import android.content.res.Configuration
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.ntnu.ivansh.filmapplication.ui.theme.FilmApplicationTheme

data class Film(val name: String, val description: String, val image: Int);
class MainActivity : ComponentActivity() {

    private var db = listOf<Film>(
        Film(
            "The Shawshank Redemption",
            "Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.",
            R.drawable.shaw
        ),
        Film(
            "The Godfather",
            "Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.",
            R.drawable.god
        ),
        Film(
            "The Dark Knight",
            "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
            R.drawable.bat
        ),
        Film(
            "Schindler's List",
            "In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.",
            R.drawable.list
        ),
        Film(
            "The Lord of the Rings: The Return of the King",
            "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
            R.drawable.lord
        ),

        );

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val backgroundColor = ContextCompat.getColor(context, R.color.purple_200)
            val configuration = LocalConfiguration.current

            var chosenIndex by remember {
                mutableStateOf(0)
            }
            var chosenItem by remember {
                mutableStateOf(db[chosenIndex])
            }

            fun setNextItem() {
                chosenIndex++;
                if (chosenIndex == db.size) chosenIndex = 0
                chosenItem = db[chosenIndex];
                Log.i("set", "next")
            }

            fun setItem(index: Int) {
                chosenIndex = index;
                chosenItem = db[chosenIndex];
                Log.i("set", "by index $index")

            }

            fun setPreviousItem() {
                chosenIndex--;
                if (chosenIndex == -1) chosenIndex = db.size - 1
                chosenItem = db[chosenIndex];
                Log.i("set", "previous")
            }
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(backgroundColor))
                                .padding(10.dp)
                                .fillMaxWidth().fillMaxHeight(0.85f)

                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.4f)
                                    .background(Color(backgroundColor))
                                    .padding(6.dp, 0.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(
                                        1.dp,
                                        Color.Yellow,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .background(Color.White)
                            ) {
                                List(
                                    films = db,
                                    onClick = { index -> setItem(index) }, chosen = chosenIndex
                                )

                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()

                            ) {
                                BigScreen(film = chosenItem)
                            }
                        }
                        Navigation(next = { setNextItem() },
                            previous = { setPreviousItem() })
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.4f)

                            ) {
                                BigScreen(film = chosenItem, modifier = Modifier.padding(10.dp))
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .background(Color(backgroundColor))
                                    .padding(6.dp, 0.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .border(
                                        1.dp,
                                        Color.Yellow,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .background(Color.White)


                            ) {
                                List(
                                    films = db, modifier = Modifier.fillMaxSize(),
                                    onClick = { index -> setItem(index) }, chosen = chosenIndex
                                )

                            }
                            Navigation(next = { setNextItem() },
                                previous = { setPreviousItem() },
                                modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp))
                        }
                    }
                }
            }
        }
    }
}


