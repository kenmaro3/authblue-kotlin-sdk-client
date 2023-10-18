package com.authblue.authbluesdkclient

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.authblue.authbluesdkclient.ui.theme.AuthblueSDKClientTheme
import androidx.navigation.compose.rememberNavController
import androidx.fragment.app.FragmentActivity
import com.authblue.authbluekotlinsdk.API.APIClient
import com.authblue.authbluekotlinsdk.API.APIErrorType
import com.authblue.authbluekotlinsdk.View.MNCRegisterScreen
import com.authblue.authbluekotlinsdk.View.AgreementRequest
import com.authblue.authbluekotlinsdk.View.MNCReadInfoScreen
import com.authblue.authbluesdkclient.View.Home

class MainActivity : FragmentActivity() {
    private val userEmail = "info@authblue.jp"
    private val userName = "kenmaro"
    private val authblueClientId = "Wbr6uCMSaK89FjjUdIaEKaZM"
    private val authblueClientName = "チュートリアル同意クライアント"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("authblue_preferences", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(
            "personal_email",
            userEmail
        )
        editor.putString(
            "personal_name",
            userName
        )
        editor.commit()

        setContent {
            AuthblueSDKClientTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    var isLoading = remember {
                        mutableStateOf(false)
                    }
                    NavHost(navController = navController, startDestination = "home") {
                        composable("home"){
                            LaunchedEffect(key1=Unit){

                                val sharedPref = getSharedPreferences("authblue_preferences", MODE_PRIVATE)
                                val editor = sharedPref.edit()
                                APIClient().createUserWithAuth(
                                    context = this@MainActivity,
                                    username = userName,
                                    email=userEmail
                                ) { res ->
                                    res?.let {
                                        res.result?.let {
                                            editor.putString(
                                                "token",
                                                res.result!!.access_token
                                            )
                                        }
                                    }
                                    editor.apply()

                                    APIClient().requestCertificateExists(context = this@MainActivity) { res ->
                                        isLoading.value = false

                                        if (res.has_error) {
                                            if (res.error_message == APIErrorType.UNAUTHORIZED.content) {
                                            } else if (res.error_message == APIErrorType.NOT_FOUND.content) {
                                            }
                                        } else {
                                            if (!res.result!!.exists) {
                                                navController.navigate("upload_certificate")
                                            } else {

                                            }
                                        }
                                    }

                                }

                            }
                            Home(navController=navController)

                        }
                        composable("upload_certificate") {
                            MNCRegisterScreen(
                                skipCallback = {
                                    Log.d("DEBUG", "skipcallback")
                                    //userState.setIsUploadSkippedTrue()
                                    //navController.navigate("home")
                                },
                                goBackToHomeCallback = {
                                    navController.navigate("home")
                                }

                            )
                        }

                        composable("read_info") {
                            MNCReadInfoScreen(
                                goBackToHomeCallback = {
                                    navController.navigate("home")
                                }

                            )
                        }

                        composable("agreement") {
                            AgreementRequest(
                                client_name = authblueClientName,
                                client_id = authblueClientId,
                                dynamic_link_uid = "test_uid",
                                goBackToLoginCallback = {
                                    navController.navigate("home")
                                },
                                goBackToHomeCallback = {
                                    navController.navigate("home")
                                },
                                goToNotificationCallback = {
                                    navController.navigate("home")
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}

