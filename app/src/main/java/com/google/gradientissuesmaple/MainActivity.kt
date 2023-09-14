package com.google.gradientissuesmaple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.google.gradientissuesmaple.ui.theme.GradientIssueSmapleTheme
import com.google.gradientissuesmaple.profile.Profile

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GradientIssueSmapleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        content = {
                            Box(
                                modifier = Modifier.padding(it)
                            ) {
                                Profile()
                            }
                        },
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = false,
                                    onClick = { },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Message,
                                            contentDescription = null
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected = false,
                                    onClick = { },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.FormatListBulleted,
                                            contentDescription = null
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected = true,
                                    onClick = { },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.AccountCircle,
                                            contentDescription = null
                                        )
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}