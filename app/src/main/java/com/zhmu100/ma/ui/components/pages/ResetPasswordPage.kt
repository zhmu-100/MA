package com.zhmu100.ma.ui.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.inputs.InputLine
import com.zhmu100.ma.ui.theme.LightGreen
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White
import kotlinx.serialization.Serializable


@Composable
fun ResetPasswordPage(
    navController: NavController? = null,
    onBackClick: () -> Unit = {},
    onSendCodeClick: (String) -> Unit = { _ -> },
    onContinueClick: (String, String) -> Unit = { _, _ -> }
) {
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var codeSent by remember { mutableStateOf(false) }

    BasePage(
        hasNavBar = false,
        navController = navController
    ) { modifier ->
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(onClick = onBackClick)
                Text(
                    text = "Забыли Пароль?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGreen,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Box(modifier = Modifier.weight(0.2f))
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Введите ваш адрес электронной почты, чтобы получить инструкции по восстановлению пароля.",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))


            InputLine(
                modifier = Modifier.padding(vertical = 8.dp),
                label = "Почта",
                onTextChanged = { email = it }
            )

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = { 
                    onSendCodeClick(email)
                    codeSent = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LightGreen,
                    contentColor = White
                ),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Отправить",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (codeSent) {
                Spacer(modifier = Modifier.height(32.dp))
                

                InputLine(
                    modifier = Modifier.padding(vertical = 8.dp),
                    label = "Код",
                    onTextChanged = { code = it }
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = { 
                        onContinueClick(email, code)
                        navController?.navigate(NewPasswordScreen)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightGreen,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Продолжить",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Serializable
object ResetPasswordScreen

@Preview(showBackground = true)
@Composable
private fun ResetPasswordPagePreview() {
    MATheme {
        ResetPasswordPage()
    }
}
