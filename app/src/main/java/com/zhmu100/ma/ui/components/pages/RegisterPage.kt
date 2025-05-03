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
import com.zhmu100.ma.domain.viewModel.RegisterViewModel
import com.zhmu100.ma.ui.components.buttons.BackButton
import com.zhmu100.ma.ui.components.buttons.StringButton
import com.zhmu100.ma.ui.components.inputs.InputLine
import com.zhmu100.ma.ui.theme.LightGreen
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterPage(
    navController: NavController? = null,
    onBackClick: () -> Unit = {},
    viewModel: RegisterViewModel = koinViewModel(),
    onRegisterClick: (String, String, String, String) -> Unit = { name, email, password, confirmPassword ->viewModel.register(name, email, password, confirmPassword) },
    onLoginClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val message by viewModel.message.collectAsState()
    val isRegisterSuccessful by viewModel.isRegisterSuccessful.collectAsState()

    if (isRegisterSuccessful) {
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
                    text = "Создание Аккаунта",
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

            Spacer(modifier = Modifier.height(16.dp))


            InputLine(
                modifier = Modifier.padding(vertical = 8.dp),
                label = "Имя",
                onTextChanged = { name = it }
            )


            InputLine(
                modifier = Modifier.padding(vertical = 8.dp),
                label = "Почта",
                onTextChanged = { email = it }
            )


            InputLine(
                modifier = Modifier.padding(vertical = 8.dp),
                label = "Пароль",
                password = true,
                onTextChanged = { password = it }
            )


            InputLine(
                modifier = Modifier.padding(vertical = 8.dp),
                label = "Пароль",
                password = true,
                onTextChanged = { confirmPassword = it }
            )

            Spacer(modifier = Modifier.height(24.dp))


            Button(
                onClick = { onRegisterClick(name, email, password, confirmPassword) },
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
                    text = "Регистрация",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Уже есть аккаунт?")
                StringButton(
                    text = "Войди",
                    onClick = { navController?.navigate(LoginScreen) }
                )
            }
        }
    }
}

@Serializable
object RegisterScreen

@Preview(showBackground = true)
@Composable
private fun RegisterPagePreview() {
    MATheme {
        RegisterPage()
    }
}
