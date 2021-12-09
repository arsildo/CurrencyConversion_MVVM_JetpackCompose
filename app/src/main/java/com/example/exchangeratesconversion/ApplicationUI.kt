package com.example.exchangeratesconversion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.exchangeratesconversion.ui.theme.Black
import com.example.exchangeratesconversion.ui.theme.Grey
import com.example.exchangeratesconversion.ui.theme.Purple500
import com.example.exchangeratesconversion.view_model.RatesViewModel
import com.example.exchangeratesconversion.view_model.ViewState


@Composable
fun ApplicationUI(
    viewModel: RatesViewModel = hiltViewModel()
) {

    DisposableEffect(key1 = Unit) {
        onDispose { }
    }

    val viewState by remember { viewModel.viewState }
    var amountEntered by remember { mutableStateOf("") }
    when (val state = viewState) {
        is ViewState.LoadingState -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp), color = Purple500)
                Text("Loading...", color = Purple500, modifier = Modifier.padding(16.dp))
            }
        }
        is ViewState.SuccessState -> {
            Column(
                modifier = Modifier.fillMaxSize().background(Color.White).padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Convert",
                    fontSize = 32.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.height(32.dp))


                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(modifier = Modifier.weight(.4f))

                    Text(
                        text = "from",
                        fontSize = 16.sp,
                        color = Grey,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(.3f)
                    )
                    Text(
                        text = "to",
                        fontSize = 16.sp,
                        color = Grey,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(.3f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = amountEntered,
                        onValueChange = { amountEntered = it },
                        label = {
                            Text(
                                "Amount",
                                fontWeight = FontWeight.SemiBold,
                                fontSize= 16.sp,
                                color = Black,
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Black,
                            cursorColor = Black,
                            unfocusedIndicatorColor = Black,
                            focusedIndicatorColor = Black,
                        ),
                        modifier = Modifier.weight(.4f)
                    )

                    Text(
                        text = "EUR",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(.3f)
                    )
                    Text(
                        text = "USD",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(.3f)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "$amountEntered XX = 32 XX ",
                    fontSize = 32.sp,
                    color = Black,
                )


            }

        }
        is ViewState.FailedState -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Failed to acquire API information.", color = Color.Black)
            }
        }
    }


}
