package com.example.socialapp.feature_auth.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldAuth(
    modifier: Modifier = Modifier,
    currentState: String,
    currentStatePlaceHolder: String,
    currentStateError: String?,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation,
    onValueChange: (String) -> Unit,
) {

    val maxLength = 30

    var focusable by remember {
        mutableStateOf(false)
    }

    TextField(
        value = currentState,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.LightGray,
            cursorColor = Color.Black,
            disabledLabelColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        onValueChange = {
            if (it.length <= maxLength)
                onValueChange(it)
        },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        isError = currentStateError != null,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                focusable = it.isFocused
            },
        placeholder = {
            Text(text = currentStatePlaceHolder)
        },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (currentState.isNotEmpty() && focusable) {
                IconButton(onClick = {
                    onValueChange("")
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = null
                    )
                }
            }
        }
    )
    if (currentStateError != null) {
        Text(
            text = currentStateError,
            color = MaterialTheme.colors.error,
            modifier = modifier,
        )
    } else if (currentState.isNotEmpty() && focusable) {
        Text(
            text = "${currentState.length} / $maxLength",
            modifier = modifier.padding(end = 10.dp),
            color = Color.Black
        )
    }
}