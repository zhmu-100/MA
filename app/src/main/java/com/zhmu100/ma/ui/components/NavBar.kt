package com.zhmu100.ma.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhmu100.ma.R
import com.zhmu100.ma.ui.theme.MATheme


@Composable
fun NavBar(
    startingActiveInd: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController? = null
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.shadow(2.dp, shape = RoundedCornerShape(15))
    ) {
        val icons = listOf(
            R.drawable.chat,
            R.drawable.timer,
            R.drawable.cookie,
            R.drawable.note,
            R.drawable.person,
        )

        var activeInd by remember { mutableIntStateOf(startingActiveInd) }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier.fillMaxWidth()
        ) {
            icons.forEachIndexed { ind, item ->
                Icon(
                    painter = painterResource(item),
                    null,
                    tint = if (ind == activeInd) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            activeInd = ind
                            onClick(ind)

                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavBarPreview() {
    MATheme {
        var text by remember { mutableStateOf("Test") }
        Column {
            Text(text)
            NavBar(
                0,
                { text = it.toString() },
                Modifier.padding(16.dp)
            )
        }
    }
}
