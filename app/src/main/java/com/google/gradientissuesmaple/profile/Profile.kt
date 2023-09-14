@file:OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package com.google.gradientissuesmaple.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.layoutId
import com.google.gradientissuesmaple.swipeable.FractionalThreshold
import com.google.gradientissuesmaple.swipeable.rememberSwipeableState
import com.google.gradientissuesmaple.swipeable.swipeable
import com.google.gradientissuesmaple.profile.component.ProfileTopAppBar
import java.time.ZonedDateTime

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String?,
    val lastOnline: ZonedDateTime,
    val isOnline: Boolean,
    val username: String,
    val avatarUrl: String?
) {
    val initials = "${firstName.firstOrNull()}${lastName?.firstOrNull() ?: ""}"

    val fullName = "$firstName ${lastName ?: ""}"
}

@Composable
fun Profile() {
    ProfileContent()
}

private enum class SwipingState {
    COLLAPSED,
    EXPANDED
}

@Composable
private fun ProfileContent(
    user: User = User(
        avatarUrl = null,
        firstName = "Walter",
        lastName = "White",
        id = 13,
        lastOnline = ZonedDateTime.now(),
        isOnline = true,
        username = "@heisenberg"
    )
) {

    val scroll = rememberScrollState(0)

    /*val minTopBarHeight = with(LocalDensity.current) {
        (82.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()).toPx()
    }*/

    val swipingState = rememberSwipeableState(initialValue = SwipingState.COLLAPSED)

//    val maxTopBarHeight = LocalView.current.width.toFloat()

    val progress = if (swipingState.progress.to == SwipingState.EXPANDED)
        swipingState.progress.fraction
    else
        1f - swipingState.progress.fraction

    Scaffold(
        content = {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {

                val heightPx = with(LocalDensity.current) { maxHeight.toPx() }

                val connection = remember {
                    object : NestedScrollConnection {

                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            val delta = available.y
                            return if (delta < 0) {
                                swipingState.performDrag(delta).toOffset()
                            } else {
                                Offset.Zero
                            }
                        }

                        override fun onPostScroll(
                            consumed: Offset,
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            val delta = available.y
                            return swipingState.performDrag(delta).toOffset()
                        }

                        override suspend fun onPostFling(
                            consumed: Velocity,
                            available: Velocity
                        ): Velocity {
                            swipingState.performFling(velocity = available.y * 2)
                            return super.onPostFling(consumed, available)
                        }

                        private fun Float.toOffset() = Offset(0f, this)
                    }
                }

                Box(
                    modifier = Modifier
                        .swipeable(
                            state = swipingState,
                            thresholds = { _, _ -> FractionalThreshold(0.5f) },
                            orientation = Orientation.Vertical,
                            anchors = mapOf(
                                0f to SwipingState.COLLAPSED,
                                heightPx to SwipingState.EXPANDED,
                            )
                        )
                        .nestedScroll(connection)
                ) {
                    ProfileTopAppBar(
                        user = user,
                        onNavigateBack = {},
                        progress = progress,
                        actions = {
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.MoreHoriz,
                                    contentDescription = null
                                )
                            }
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(scroll)
                                .padding(it)
                                .layoutId("content")
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                            ) {
                                Text(
                                    text = "Account",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { }
                                ) {
                                    Text(
                                        text = "+1 (111) 111-11-11"
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(start = 24.dp),
                                thickness = .5.dp
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { },
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = user.username
                                    )
                                    Text(
                                        text = "Username",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = LocalContentColor.current.copy(.6f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider(
                                modifier = Modifier
                                    .padding(start = 24.dp),
                                thickness = .5.dp
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Column(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp)
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { },
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = "A high school chemistry teacher"
                                    )
                                    Text(
                                        text = "Bio",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = LocalContentColor.current.copy(.6f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}