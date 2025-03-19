package com.zhmu100.ma.ui.components.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zhmu100.ma.ui.theme.MATheme

@Composable
fun ToggleButton(
    modifier: Modifier = Modifier,
    initState: Boolean = false,
    onCheckedChange: (isChecked: Boolean) -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(initState) }

    Switch(
        checked = isChecked,
        onCheckedChange = {
            onCheckedChange(!isChecked)
            isChecked = !isChecked
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.primary,
            checkedTrackColor = MaterialTheme.colorScheme.inversePrimary,
            uncheckedThumbColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ToggleButtonPreview() {
    MATheme {
        ToggleButton()
    }
}