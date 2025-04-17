package ui.components.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun Modifier.leftSwipeToMark(
    thresholdFraction: Float = 0.2f,
    goingBackAnimationDuration: Int = 800,
    onSwiped: () -> Unit
): Modifier = composed {
    var widthPx by remember { mutableFloatStateOf(0f) }
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    this
        .onSizeChanged { widthPx = it.width.toFloat() }
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
        .pointerInput(widthPx) {
            detectHorizontalDragGestures(
                onHorizontalDrag = { change, dragAmount ->
                    if (change.positionChange() != Offset.Zero) change.consume()
                    val newX = (offsetX.value + dragAmount)
                        .coerceIn(-widthPx, 0f)
                    scope.launch { offsetX.snapTo(newX) }
                },
                onDragEnd = {
                    val thresholdPx = widthPx * thresholdFraction
                    scope.launch {
                        if (-offsetX.value >= thresholdPx) {
                            onSwiped()
                        }
                        offsetX.animateTo(
                            targetValue = 0f,
                            animationSpec = tween(durationMillis = goingBackAnimationDuration)
                        )
                    }
                }
            )
        }
}
