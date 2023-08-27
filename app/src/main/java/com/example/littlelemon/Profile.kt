package com.example.littlelemon

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    var firstName by rememberSaveable{ mutableStateOf(getUserFirstName(context)) }
    var lastName by rememberSaveable {mutableStateOf(getUserLastName(context))}
    var email by rememberSaveable {mutableStateOf(getUserEmail(context))}
    var editing by rememberSaveable {mutableStateOf(false)}
    Column(modifier = Modifier.fillMaxSize()) {
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
        Row(){
            IconButton(onClick = { navController.navigate(Home.route) }) {
                Image(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
            }
        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.15f)
                    .padding(PaddingValues(18.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
        ) {
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
        ) {
            Column {
                Text("First name")
                OutlinedTextField(
                        value = firstName,
                        enabled = editing,
                        onValueChange = {firstName = it},
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
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Last name")
                OutlinedTextField(
                        value = lastName,
                        enabled = editing,
                        onValueChange = { lastName = it },
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
        ) {
            Column {
                Text("Email")
                OutlinedTextField(
                        value = email,
                        enabled = editing,
                        onValueChange = { email = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 14.sp)
                )
            }

        }
        Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(PaddingValues(18.dp)),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
        ) {
            Button(
                    onClick = {
                        editing = !editing
                        val validFields = checkEmptyFields(firstName,lastName, email)
                        if (!editing){
                            if (validFields){
                                saveUserInfoToPreferences(context,firstName,lastName,email)
                                Toast.makeText(context,"Changes saved!",Toast.LENGTH_SHORT).show()
                            }
                            else Toast.makeText(context,"Please enter all data.",Toast.LENGTH_SHORT).show()
                        }



                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = yellow)
            ) {
                Text(
                        text = if (editing) "Save" else "Edit",
                        color = Color.Black
                )
            }

        }

        Row(
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(PaddingValues(18.dp)),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
        ) {
            Button(
                    onClick = {
                        clearSharedPreferences(context)
                        navController.navigate(Ondoarding.route)

                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = yellow)
            ) {
                Text(
                        text = "Log out",
                        color = Color.Black
                )
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile(navController = rememberNavController())
}

fun getUserFirstName(context : Context) : String {
    val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPrefs.getString("first_name", "") ?: ""
}

fun getUserLastName(context : Context) : String {
    val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPrefs.getString("last_name", "") ?: ""
}

fun getUserEmail(context : Context) : String {
    val sharedPrefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return sharedPrefs.getString("email", "") ?: ""
}

fun clearSharedPreferences(context : Context) {
    val sharedPrefs : SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    sharedPrefs.edit().clear().apply()
}