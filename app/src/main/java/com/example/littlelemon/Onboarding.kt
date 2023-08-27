package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.green
import com.example.littlelemon.ui.theme.grey
import com.example.littlelemon.ui.theme.yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Column(
            modifier = Modifier
                .fillMaxSize()
    ) {
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.15f)
        ) {
            Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(36.dp)
                        .fillMaxSize()
            )

        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.15f)
                    .background(green),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
        ) {
            Text(
                    text = "Let's get to know you",
                    color = Color.White,
                    fontSize = 24.sp
            )

        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.15f)
                    .padding(PaddingValues(18.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
        ){
            Text(
                    text = "Personal information",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                    )

        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(108.dp)
                    .padding(PaddingValues(18.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
        ){
        Column {
            Text("First name")
            OutlinedTextField(
                    value = firstName ,
                    onValueChange = { firstName = it},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 14.sp)
            )
        }

    }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(108.dp)
                    .padding(PaddingValues(18.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
        ){
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Last name")
                OutlinedTextField(
                        value = lastName ,
                        onValueChange = { lastName = it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 14.sp)
                )
            }

        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(108.dp)
                    .padding(PaddingValues(18.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
        ){
            Column {
                Text("Email")
                OutlinedTextField(
                        value = email ,
                        onValueChange = { email = it},
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 14.sp)
                )
            }

        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(PaddingValues(18.dp)),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
        ){
            Button(
                    onClick = {
                        val validFields = checkEmptyFields(firstName,lastName, email)
                        if (validFields){
                            saveUserInfoToPreferences(context,firstName,lastName,email)
                            navController.navigate(Home.route)
                            Toast.makeText(context,"Registration successful!",Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(context,"Registration unsuccessful. Please enter all data.",Toast.LENGTH_SHORT).show()

                              },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = yellow)
            ) {
                Text(
                        text = "Register",
                        color = Color.Black
                )
            }

        }


    }

}



fun saveUserInfoToPreferences(context: Context, firstName: String, lastName: String, email: String) {
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    sharedPrefs.edit {
        putString("first_name", firstName)
        putString("last_name", lastName)
        putString("email", email)
        putBoolean("onboarding_complete",true)
    }
}

fun checkEmptyFields(firstName:String, lastName:String, email:String):Boolean{
    if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank() ) return true
    return false
}