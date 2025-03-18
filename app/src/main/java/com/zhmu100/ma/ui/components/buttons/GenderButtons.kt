package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
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

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Male button
        CircleButton(isMaleSelected, onClick = {
            onMaleSelect()
            isMaleSelected = true
        }, iconRes = R.drawable.male)
        Spacer(Modifier.size(16.dp))
        // Female button
        CircleButton(!isMaleSelected, onClick = {
            onFemaleSelect()
            isMaleSelected = false
        }, iconRes = R.drawable.female)
    }
}

@Composable
fun CircleButton(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    iconRes: Int
) {
    OutlinedButton(
        onClick = onClick,
        contentPadding = PaddingValues(16.dp),
        colors = if (isSelected) ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.primary,
            White
        ) else ButtonDefaults.buttonColors(
            White,
            MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenderButtonsPreview() {
    MATheme {
        GenderButtons()
    }
}