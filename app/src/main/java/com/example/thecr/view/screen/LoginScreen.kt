package com.example.thecr.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.thecr.R
import com.example.thecr.navigation.TheCRScreen
import com.example.thecr.view.OtpTextField
import com.example.thecr.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val phoneNumber by viewModel.number
    val code by viewModel.code.collectAsState("")
    var openDialog by remember { mutableStateOf(false) }
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize().padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, colors = CardDefaults.cardColors(
                    Color.White
                ), elevation = CardDefaults.elevatedCardElevation(20.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_cr_foreground),
                    "logo",
                    modifier = Modifier.size(100.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                phoneNumber,
                viewModel::onNumberChange,
                shape = RoundedCornerShape(20.dp),
                prefix = { Text("+91") },
                placeholder = { Text("Enter Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {
                openDialog = true
                viewModel.authenticatePhone(phoneNumber)
            }, content = {
                Text("Login")
            }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White))
        }

        if (openDialog) {
            Box {
                if (viewModel.loading.collectAsState(false).value) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                AlertDialog(
                    {
                        openDialog = false
                    },
                    modifier = with(Modifier) {
                        fillMaxWidth().height(150.dp).clip(RoundedCornerShape(20.dp)).background(Color.White)
                    }) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Enter OTP")
                        OtpTextField(
                            otpText = code,
                            onOtpTextChange = { value, otpInputFilled ->
                                viewModel.onCodeChange(value)
                            }
                        )

                        Row {
                            Button({ openDialog = false }) {
                                Text("Cancel")
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Button({
                                viewModel.verifyOtp(code)
                                openDialog = false
                            }) {
                                Text("Verify")
                            }
                        }
                    }
                }
            }

        }

        if (viewModel.success.collectAsState(false).value) {
            navController.navigate(
                TheCRScreen.CALENDAR_SCREEN.name
            )
        }
    }
}
