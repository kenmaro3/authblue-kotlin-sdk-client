package com.authblue.authbluesdkclient.View


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.authblue.authbluesdkclient.View.Component.CustomButton

@OptIn(ExperimentalTextApi::class)
@Composable
fun Home(navController: NavHostController){
    val GradientColors = listOf(Cyan, Magenta)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "AUTHBLUE",
                    fontSize = 42.sp,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = GradientColors
                        )
                    )
                )
                Text(
                    text = "Sample",
                    fontSize = 42.sp,
                )
                Text(
                    text = "App",
                    fontSize = 42.sp,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top=32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomButton(text="Register", callback = {
                    navController.navigate("upload_certificate")
                })

                CustomButton(text="ReadInfo", callback = {
                    navController.navigate("read_info")
                })

                CustomButton(text="Agreement", callback = {
                    navController.navigate("agreement")
                })
            }
        }
    }
}