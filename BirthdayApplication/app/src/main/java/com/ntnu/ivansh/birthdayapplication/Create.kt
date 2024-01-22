package com.ntnu.ivansh.birthdayapplication

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewFriend(
    modifier: Modifier = Modifier, friends: MutableList<Person>
) {
    val context = LocalContext.current

    var name by remember {
        mutableStateOf("")
    }
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    Row(
        modifier= modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(2.dp, 6.dp),


        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ){

        TextField(value = name , onValueChange = {text->name=text},
            modifier= Modifier
                .fillMaxWidth(0.5f)
                .padding(4.dp, 0.dp)
                .background(Color.White)
                .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
            ,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = Color.Magenta,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )

            ,label = { Text("Name") })

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(0.dp)

        ) {
            Button(
                modifier= Modifier.border(1.dp, Color.DarkGray, shape = RoundedCornerShape(5.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor  = Color.Transparent),
                contentPadding = PaddingValues(1.dp),
                onClick = { dateDialogState.show();
                },
            ) {
                Image(painter = painterResource(id = R.drawable.calendar_symbol),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp, 30.dp)
                        .background(Color.White))


            }

            Text(text = "$formattedDate", textAlign = TextAlign.Center,fontSize = 14.sp,
                style = TextStyle(
                    color = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth(0.5f))

        }

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green,
                contentColor = Color.Black
            ),
            modifier = Modifier
                .background(
                    color = Color.Green,
                    shape = CircleShape
                )
                .padding(3.dp)
                .size(30.dp),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                if(name.isBlank()){
                    Toast.makeText(context, "Please enter the name", Toast.LENGTH_LONG).show()

                }
                else {
                    friends.add(Person(name, pickedDate))
                    Toast.makeText(context, "$name`s birthday was added", Toast.LENGTH_LONG).show()

                    name = ""

                }

            }
        ) {
            Text(
                text = "+",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                modifier = Modifier.fillMaxSize(),
                fontWeight = FontWeight.Bold,

                )
        }

    }


    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                Toast.makeText(context, "Date set successfull", Toast.LENGTH_LONG).show()
            }
            negativeButton(text = "Cancel") {
                Toast.makeText(context, "Action is cancelled", Toast.LENGTH_LONG).show()
            }

        }
    ) {
        this.datepicker(
            initialDate = LocalDate.now(),
            title="Pick a birthday",
            allowedDateValidator = {
                it.isBefore(LocalDate.now())
            }
        ){
            pickedDate=it
        }
    }

}