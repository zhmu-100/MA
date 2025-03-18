package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.theme.MATheme
import com.zhmu100.ma.ui.theme.White

@Composable
fun GenderButtons(
    modifier: Modifier = Modifier,
    onMaleSelect: () -> Unit = {},
    onFemaleSelect: () -> Unit = {},
) {
    var isMaleSelected by remember { mutableStateOf(true) }

    val unselectedColors = ButtonColors(
        White,
        MaterialTheme.colorScheme.primary,
        White,
        MaterialTheme.colorScheme.primary
    )
    val selectedColors = ButtonColors(
        MaterialTheme.colorScheme.inversePrimary,
        White,
        MaterialTheme.colorScheme.inversePrimary,
        White
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Male button
        OutlinedButton(
            onClick = {
                onMaleSelect()
                isMaleSelected = true
            },
            contentPadding = PaddingValues(16.dp),
            colors = if (isMaleSelected) selectedColors else unselectedColors
        ) {
            Icon(
                painter = painterResource(R.drawable.male),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
        Spacer(Modifier.size(16.dp))
        // Female button
        OutlinedButton(
            onClick = {
                onFemaleSelect()
                isMaleSelected = false
            },
            contentPadding = PaddingValues(16.dp),
            colors = if (isMaleSelected) unselectedColors else selectedColors
        ) {
            Icon(
                painter = painterResource(R.drawable.female),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenderButtonsPreview() {
    MATheme {
        GenderButtons()
    }
}