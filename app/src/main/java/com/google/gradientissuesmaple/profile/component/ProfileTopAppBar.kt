@file:OptIn(ExperimentalMotionApi::class)
@file:Suppress("AnimateAsStateLabel")

package com.google.gradientissuesmaple.profile.component

import android.text.format.DateFormat
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutScope
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.google.gradientissuesmaple.R
import com.google.gradientissuesmaple.profile.User
import kotlin.math.absoluteValue

@Composable
fun ProfileTopAppBar(
    user: User,
    onNavigateBack: () -> Unit,
    progress: Float,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable MotionLayoutScope.() -> Unit
) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.profile_tob_bar_scene)
            .readBytes()
            .decodeToString()
    }
    val avatarShape by remember(progress) {
        mutableStateOf(
            RoundedCornerShape(
                percent = ((progress - 1).absoluteValue * 50).toInt()
            )
        )
    }

    val statusBarsInsets = WindowInsets.statusBars.asPaddingValues()
    val containerTopPadding by remember(progress) {
        mutableStateOf(
            statusBarsInsets.calculateTopPadding() * (progress - 1).absoluteValue
        )
    }
    val actionsTopPadding by remember(progress) {
        mutableStateOf(
            statusBarsInsets.calculateTopPadding() * progress.absoluteValue + 8.dp
        )
    }

    val fabScale by animateFloatAsState(
        targetValue = if (progress <= .1f) .3f else 1f
    )
    val fabAlpha by animateFloatAsState(
        targetValue = if (progress <= .1f) 0f else 1f
    )

    val breakpointTransitionProgress by animateFloatAsState(
        targetValue = if (progress <= .7f) 0f else 1f
    )

    val contentColor by animateColorAsState(
        targetValue = if (progress <= .7f)
            MaterialTheme.colorScheme.onBackground
        else MaterialTheme.colorScheme.onPrimary
    )

    val subscriptTextColor by animateColorAsState(
        targetValue = if (progress <= .7f)
            LocalContentColor.current.copy(.6f)
        else MaterialTheme.colorScheme.onPrimary
    )

    Surface(
        modifier = Modifier
            .heightIn(min = 66.dp)
            .fillMaxWidth()
    ) {
        MotionLayout(
            modifier = Modifier
                .padding(top = containerTopPadding)
                .fillMaxSize(),
            motionScene = MotionScene(motionScene),
            progress = progress
        ) {
            Image(
                modifier = Modifier
                    .clip(avatarShape)
                    .drawWithContent {
                        drawContent()
                        /*drawRect(
                            color = Color.Magenta.copy(.4f),
                            size = size.copy(height = size.height * .3f * breakpointTransitionProgress)
                        )*/
                        drawRect(
                            brush = Brush.verticalGradient(
                                0f to Color.Black.copy(.4f),
                                1f to Color.Transparent,
                                endY = size.height * .3f * breakpointTransitionProgress
                            ),
                            size = size.copy(height = size.height * .3f * breakpointTransitionProgress)
                        )
                        drawRect(
                            brush = Brush.verticalGradient(
                                0f to Color.Transparent,
                                1f to Color.Black.copy(.4f),
                                startY = size.height * lerp(1f, .7f, breakpointTransitionProgress),
                            ),
                            size = size.copy(height = size.height * .3f),
                            topLeft = Offset(
                                x = 0f,
                                y = size.height * lerp(1f, .7f, breakpointTransitionProgress)
                            )
                        )
                    }
                    .layoutId("avatar"),
                painter = painterResource(id = R.drawable.chat_pattern_png),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            HorizontalDivider(
                modifier = Modifier
                    .layoutId("divider"),
                thickness = .5.dp
            )
            Column(
                modifier = Modifier
                    .layoutId("name"),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = user.fullName,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = lerp(18f, 22f, breakpointTransitionProgress).sp,
                    fontWeight = androidx.compose.ui.text.font.lerp(FontWeight.SemiBold, FontWeight.Medium, breakpointTransitionProgress),
                    color = contentColor,
                    modifier = Modifier
                        .layoutId("name")
                )

                Text(
                    text = "Online",
                    overflow = TextOverflow.Ellipsis,
                    color = subscriptTextColor,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2
                )
            }

            CompositionLocalProvider(
                LocalContentColor provides contentColor
            ) {
                Row(
                    modifier = Modifier
                        .layoutId("actions")
                        .padding(top = actionsTopPadding),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions
                )
            }

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = fabScale
                        scaleY = fabScale
                        alpha = fabAlpha
                    }
                    .size(55.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = CircleShape
                    )
                    .border(
                        width = .5.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable {

                    }
                    .layoutId("fab"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(25.dp),
                    painter = painterResource(R.drawable.ic_upload_photo_alt),
                    contentDescription = null,
                    tint = Color.Gray
                )
            }

            content()
        }
    }
}