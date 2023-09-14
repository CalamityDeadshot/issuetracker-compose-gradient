package com.google.gradientissuesmaple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.google.gradientissuesmaple.profile.Profile
import com.google.gradientissuesmaple.ui.theme.GradientIssueSmapleTheme

sealed class AppTab(
    val route: String,
    val icon: ImageVector,
    @DrawableRes val iconPainterResource: Int? = null,
    @StringRes val nameResource: Int
) {
    object Messages : AppTab(
        route = "messages_tab",
        icon = Icons.Default.Message,
        iconPainterResource = R.drawable.ic_chats,
        nameResource = R.string.tab_messages
    )

    object Tasks : AppTab(
        route = "tasks_tab",
        icon = Icons.Default.Task,
        nameResource = R.string.tab_tasks
    )

    object Profile : AppTab(
        route = "profile_tab",
        icon = Icons.Default.AccountCircle,
        nameResource = R.string.tab_profile
    )

    @Immutable
    companion object {
        val all
            get() = listOf(
                Messages,
                Tasks,
                Profile
            )
    }
}

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
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.background,
                                tonalElevation = 0.dp
                            ) {
                                AppTab.all.forEach { tab ->
                                    NavigationBarItem(
                                        selected = false,
                                        onClick = { },
                                        icon = {
                                            Icon(
                                                modifier = Modifier
                                                    .size(24.dp),
                                                imageVector = tab.iconPainterResource?.let {
                                                    ImageVector.vectorResource(it)
                                                } ?: tab.icon,
                                                contentDescription = null
                                            )
                                        },
                                        label = {
                                            Text(stringResource(tab.nameResource))
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = MaterialTheme.colorScheme.primary,
                                            selectedTextColor = MaterialTheme.colorScheme.primary,
                                            indicatorColor = MaterialTheme.colorScheme.background,
                                            unselectedIconColor = MaterialTheme.colorScheme.onBackground.copy(.6f),
                                            unselectedTextColor = MaterialTheme.colorScheme.onBackground.copy(.6f)
                                        )
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}