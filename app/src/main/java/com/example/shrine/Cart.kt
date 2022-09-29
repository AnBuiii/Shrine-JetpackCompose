package com.example.shrine


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shrine.data.ItemData
import com.example.shrine.data.SampleItemsData
import com.example.shrine.ui.theme.ShrineTheme

@Composable
fun Cart() {
    Surface(
        color = MaterialTheme.colors.secondary,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            CartHeader()
            SampleItemsData.forEach { item ->
                CartItem(item)
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                Text("This is a button".uppercase())
            }
        }
    }

}

@Composable
private fun CartHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "down arrow "
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "Cart".uppercase())
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = "${SampleItemsData.size}".uppercase())
    }
}

@Composable
private fun CartItem(item: ItemData) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { },
            Modifier.padding(horizontal = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.RemoveCircleOutline,
                contentDescription = "Remove item icon"
            )
        }
        Column(
            Modifier.fillMaxWidth()
        ) {
            Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
            Row(
                Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = item.photoResId),
                    contentDescription = null
                )
                Spacer(Modifier.width(20.dp))
                Column(
                    Modifier.padding(end = 16.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${item.vendor}".uppercase(),
                            style = MaterialTheme.typography.body2,
                        )
                        Text(
                            text = "${item.price}".uppercase(),
                            style = MaterialTheme.typography.body2,
                        )
                    }
                    Text(
                        text = item.title.uppercase(),
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartPreview() {
    ShrineTheme {
        Cart()
    }
}