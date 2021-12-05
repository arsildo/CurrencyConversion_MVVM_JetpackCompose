package com.example.exchangeratesconversion

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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
            val lastTimeUpdated by remember { viewModel.lastTimeUpdated }
            Column(
                modifier = Modifier.fillMaxSize().background(Color.White).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Convert",
                    color = Color.Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ExpandableCards("EUR")
                    var amountEntered by remember { mutableStateOf("100") }
                    ExchangeBar(amountEntered) {
                        amountEntered = it
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ExpandableCards("USD")
                    var amountReturned by remember { mutableStateOf("121") }
                    ExchangeBar(amountReturned) {
                        amountReturned = it
                    }
                }

                Text(
                    lastTimeUpdated,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
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
fun ExchangeBar(amount: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        value = amount,
        onValueChange = { onValueChanged(it) },
        textStyle = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Purple500,
            unfocusedBorderColor = Purple500
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth(.4f).padding(8.dp)
    )
}

//TODO Complete
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableCards(
    text: String
) {
    var expandedState by remember { mutableStateOf(false) }
    var stroke by remember { mutableStateOf(1) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(.3f)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        backgroundColor = Color.White,
        border = BorderStroke(stroke.dp, Purple500),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            expandedState = !expandedState
            stroke = if (expandedState) 2 else 1
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = text,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                        stroke = if (expandedState) 2 else 1
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Text("TODO") //TODO
            }
        }
    }

}