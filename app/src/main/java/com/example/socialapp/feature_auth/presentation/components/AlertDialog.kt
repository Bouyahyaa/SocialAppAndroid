package com.example.socialapp.feature_auth.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.socialapp.R
import com.example.socialapp.feature_auth.presentation.login.LoginFormEvent

@Composable
fun AlertDialogSample(
    openDialog: MutableState<Boolean>,
    dialogTitle: String,
    dialogText: String,
) {
    Column {
        if (openDialog.value) {

            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = dialogTitle)
                },
                text = {
                    Text(dialogText)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        },
                        shape = RoundedCornerShape(50.dp),
                        border = BorderStroke(
                            1.dp,
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFF71458),
                                    Color(0xFFFA95AC)
                                ),
                            )),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Black
                        ),
                    ) {
                        Image(
                            painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "OK button icon",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(text = "OK",
                            modifier = Modifier.padding(
                                start = 8.dp,
                                bottom = 2.dp
                            )
                        )
                    }
                }
            )
        }
    }
}