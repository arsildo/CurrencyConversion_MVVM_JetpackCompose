package com.example.exchangeratesconversion

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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

    val viewState by remember { viewModel.viewState }
    var amountEntered by remember { viewModel.amount }
    var converted by remember { viewModel.conversion }
    var fromCurrency by remember { viewModel.fromCurrency }
    var toCurrency by remember { viewModel.toCurrency }

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
                        onValueChange = {
                            amountEntered = it
                            if (amountEntered.isNotEmpty()) {
                                viewModel.convert(fromCurrency, toCurrency, amountEntered)
                            } else {
                                converted = "0"
                            }
                        },
                        label = {
                            Text(
                                "Amount",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
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
                        modifier = Modifier.weight(.4f).padding(bottom = 8.dp)
                    )

                    val list = viewModel.listOfCurrencies()
                    var selected by remember { mutableStateOf(list[3]) }

                    DropdownMenu(
                        list,
                        fromCurrency,
                        modifier = Modifier.weight(.3f),
                    ) {
                        selected = list[it]
                        fromCurrency = selected
                        viewModel.convert(fromCurrency, toCurrency, amountEntered)
                    }
                    DropdownMenu(
                        list,
                        toCurrency,
                        modifier = Modifier.weight(.3f),
                    ) {
                        selected = list[it]
                        toCurrency = selected
                        viewModel.convert(fromCurrency, toCurrency, amountEntered)
                    }

                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "$amountEntered $fromCurrency = $converted $toCurrency",
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


@Composable
fun DropdownMenu(
    list: List<String>,
    header: String,
    modifier: Modifier,
    onSelected: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(0) }
    var expand by remember { mutableStateOf(false) }
    var stroke by remember { mutableStateOf(1) }
    Box(
        modifier
            .padding(8.dp)
            .border(
                border = BorderStroke(stroke.dp, Black),
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                expand = true
                stroke = if (expand) 2 else 1
            },
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = header,
            color = Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        DropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false },
            modifier = Modifier
                .background(Color.White)
                .padding(2.dp)
                .fillMaxWidth(.2f)
        ) {
            list.forEachIndexed { index, name ->
                DropdownMenuItem(
                    onClick = {
                        selectedIndex = index
                        expand = false
                        stroke = if (expand) 2 else 1
                        onSelected(selectedIndex)
                    }
                ) {
                    Text(
                        text = name,
                        color = Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

    }
}