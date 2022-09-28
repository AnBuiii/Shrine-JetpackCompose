package com.example.shrine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shrine.ui.theme.ShrineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShrineTheme {
                Cart()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column{
        Text(text = "Hello $name!")
        Text(text = "ok")
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShrineTheme {
        Greeting("Android")
    }
}