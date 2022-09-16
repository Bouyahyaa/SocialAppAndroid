package com.example.socialapp.feature_auth.presentation.confirmation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.socialapp.R

@Composable
fun ConfirmationDialog(
    openDialogCustom: MutableState<Boolean>,
    onButtonClick: () -> Unit,
) {
    if (openDialogCustom.value) {
        Dialog(
            onDismissRequest = { openDialogCustom.value = false },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                securePolicy = SecureFlagPolicy.Inherit,
            )) {
            CustomConfirmationDialogUI(openDialogCustom = openDialogCustom, onButtonClick = onButtonClick)
        }
    }
}

//Layout
@Composable
fun CustomConfirmationDialogUI(
    modifier: Modifier = Modifier,
    openDialogCustom: MutableState<Boolean>,
    onButtonClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(modifier.background(Color.White)) {

            Image(
                painter = painterResource(id = R.drawable.baseline_mark_email_read_24),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(color = Color(0xFFFA95AC)),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Verified!",
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)
                Text(text = "Yahoo! You have successfully verified the account",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.body1)
            }

            Row(Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFF71458), Color(0xFFFA95AC)),
                )), horizontalArrangement = Arrangement.SpaceAround) {

                TextButton(onClick = {
                    openDialogCustom.value = false
                    onButtonClick()
                }) {
                    Text("Log In",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp))
                }
            }
        }
    }
}