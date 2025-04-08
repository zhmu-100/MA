package com.zhmu100.ma.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.theme.Black
import com.zhmu100.ma.ui.theme.MATheme


@Composable
fun CategoryButton(
    text: String,
    isActive: Boolean,
    iconResource: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) = BaseCategoryButton(text, isActive, modifier, onClick)
{
    Icon(painter = painterResource(iconResource), null)
}

@Composable
fun CategoryButton(
    text: String,
    isActive: Boolean,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) = BaseCategoryButton(text, isActive, modifier, onClick)
{
    Icon(imageVector = imageVector, null)
}

@Composable
fun CategoryButton(
    text: String,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) = BaseCategoryButton(text, isActive, modifier, onClick) {}

@Composable
private fun BaseCategoryButton(
    text: String,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
            contentColor = if (isActive) MaterialTheme.colorScheme.background else Black
        ),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
        modifier = modifier
            .border(BorderStroke(if (isActive) 0.dp else 2.dp, Black), CircleShape)
            .background(
                color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                shape = CircleShape
            )
            .height(24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            content()
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CategoryButtonPreview() {
    var text by remember { mutableStateOf("Test") }
    var active by remember { mutableStateOf(false) }
    MATheme {
        CategoryButton(
            text,
            active,
            iconResource = R.drawable.male,
            onClick = {
                text = "Test1"
                active = !active
            }
        )
    }
}