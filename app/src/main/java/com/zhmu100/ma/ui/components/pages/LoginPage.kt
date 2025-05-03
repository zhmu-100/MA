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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zhmu100.ma.domain.viewModel.LoginViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.StringButton
import com.zhmu100.ma.ui.components.inputs.InputLine
import com.zhmu100.ma.ui.theme.LightGreen
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginPage(
    navController: NavController? = null,
    onBackClick: () -> Unit = {},
    viewModel: LoginViewModel = koinViewModel(),
    onLoginClick: (String, String) -> Unit = { email, password ->viewModel.login(email, password) },
    onRegisterClick: () -> Unit = {},
    onResetPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val message by viewModel.message.collectAsState()
    val isLoginSuccessful by viewModel.isLoginSuccessful.collectAsState()

    if (isLoginSuccessful) {
        navController?.navigate(ProfileScreen)
    }

    if (message != null) {
        Text(text = message!!, color = Color.Red)
    }

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
                    text = "Вход",
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
                text = "Создайте свой аккаунт для доступа к персонализированным планам здоровья и фитнеса.",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))


            InputLine(
                modifier = Modifier.padding(vertical = 8.dp),
                label = "Почта",
                onTextChanged = { email = it }
            )

            Spacer(modifier = Modifier.height(16.dp))


            InputLine(
                modifier = Modifier.padding(vertical = 8.dp),
                label = "Пароль",
                password = true,
                onTextChanged = { password = it }
            )

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = { onLoginClick(email, password) },
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
                    text = "Вход",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Еще нет аккаунта?")
                StringButton(
                    text = "Зарегистрируйся",
                    onClick = { navController?.navigate(RegisterScreen) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Забыли пароль?")
                StringButton(
                    text = "Восстановить пароль",
                    onClick = { navController?.navigate(ResetPasswordScreen) }
                )
            }
        }
    }
}

@Serializable
object LoginScreen

@Preview(showBackground = true)
@Composable
private fun LoginPagePreview() {
    MATheme {
        LoginPage()
    }
}