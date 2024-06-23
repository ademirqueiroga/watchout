package com.admqueiroga.common.compose.ui

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.admqueiroga.common.compose.theme.AverageScoreProgressColor
import com.admqueiroga.common.compose.theme.AverageScoreTrackColor
import com.admqueiroga.common.compose.theme.HighScoreProgressColor
import com.admqueiroga.common.compose.theme.HighScoreTrackColor
import com.admqueiroga.common.compose.theme.LowScoreProgressColor
import com.admqueiroga.common.compose.theme.LowScoreTrackColor

/**
 * A circular indicator that displays a score percentage.
 *
 * @param modifier The modifier to be applied to the indicator.
 * @param scorePercentage The score percentage to be displayed, between 0.0 and 1.0.
 * @param trackColor The color of the track.
 * @param progressColor The color of the progress.
 */
@Composable
fun ScoreCircularIndicator(
    modifier: Modifier = Modifier,
    @FloatRange(0.0, 1.0) scorePercentage: Float,
    trackColor: Color = when (scorePercentage) {
        in 0f..0.4f -> LowScoreTrackColor
        in 0.41f..0.7f -> AverageScoreTrackColor
        else -> HighScoreTrackColor
    },
    progressColor: Color = when (scorePercentage) {
        in 0f..0.4f -> LowScoreProgressColor
        in 0.41f..0.7f -> AverageScoreProgressColor
        else -> HighScoreProgressColor
    }
) {
    val strokeWidth = 2.dp
    val circularProgressModifier = Modifier.padding(2.dp)
    Surface(
        modifier = modifier.size(48.dp),
        elevation = 1.dp,
        shape = CircleShape,
        color = MaterialTheme.colors.onSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            // Background track
            CircularProgressIndicator(
                modifier = circularProgressModifier,
                progress = 1f,
                strokeWidth = strokeWidth,
                color = trackColor
            )
            // Score progress
            CircularProgressIndicator(
                modifier = circularProgressModifier,
                progress = scorePercentage,
                strokeWidth = strokeWidth,
                color = progressColor,
                strokeCap = StrokeCap.Round
            )
            Text(
                text = AnnotatedString.Builder().apply {
                    append((scorePercentage * 100).toInt().toString())
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colors.surface.copy(alpha = 0.7f),
                            fontSize = 8.sp,
                            baselineShift = BaselineShift(0.35f),
                        )
                    ) {
                        append("%")
                    }
                }.toAnnotatedString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.surface,
            )
        }
    }
}
