package com.traveo.app.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.roundToInt
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A single destination in a [FloatingNavBar].
 *
 * @property label Text shown next to the icon while the item is selected. Also used as the
 * accessibility description of the item.
 * @property iconSize Size of the icon. The height of the bar is derived from the largest icon
 * size plus the vertical indicator padding.
 * @property icon Draws the icon. Receives the current tint, which animates between the selected
 * and unselected content colors.
 */
@Immutable
class FloatingNavItem(
    val label: String,
    val iconSize: Dp = 24.dp,
    val icon: @Composable (tint: Color) -> Unit
)

/**
 * Creates a [FloatingNavItem] whose icon is a drawable or vector resource.
 *
 * @param label Text shown while the item is selected.
 * @param iconRes Drawable resource used as the icon.
 * @param iconSize Size of the icon.
 */
fun floatingNavItem(
    label: String,
    @DrawableRes iconRes: Int,
    iconSize: Dp = 24.dp
): FloatingNavItem = FloatingNavItem(label, iconSize) { tint ->
    Icon(
        painter = painterResource(id = iconRes),
        // The label already provides the accessible name.
        contentDescription = null,
        tint = tint,
        modifier = Modifier.size(iconSize)
    )
}

/**
 * Colors used by [FloatingNavBar].
 *
 * @property container Background of the bar.
 * @property indicator Background of the selected item.
 * @property selectedContent Color of the icon and label of the selected item.
 * @property unselectedContent Color of the icons of unselected items.
 */
@Immutable
class FloatingNavBarColors(
    val container: Color,
    val indicator: Color,
    val selectedContent: Color,
    val unselectedContent: Color
)

/**
 * Animation specs for [FloatingNavBar].
 *
 * Changing the selection plays three animations. Collapse and travel start together, and expand
 * starts after [expandDelayMillis]:
 *
 * 1. [collapse] shrinks the previously selected pill back to a circle.
 * 2. [travel] moves the icons and the indicator to their new positions.
 * 3. [expand] grows the new pill and reveals its label.
 *
 * Only the previously selected item and the newly selected item are animated, so the motion is
 * the same regardless of how far apart the two items are.
 *
 * @property collapse Spec for shrinking the previously selected item.
 * @property travel Spec for moving icons and the indicator.
 * @property expand Spec for growing the newly selected item.
 * @property expandDelayMillis Delay before [expand] starts, in milliseconds.
 */
@Immutable
class FloatingNavBarAnimation(
    val collapse: AnimationSpec<Float>,
    val travel: AnimationSpec<Float>,
    val expand: AnimationSpec<Float>,
    val expandDelayMillis: Int
)

/** Default values for [FloatingNavBar]. */
object FloatingNavBarDefaults {
    /** Space between the edge of the bar and the indicator. */
    val ContainerPadding = 6.dp

    /** Horizontal padding between the indicator edge and its content, when expanded. */
    val IndicatorHorizontalPadding = 18.dp

    /** Vertical padding between the indicator edge and the icon. */
    val IndicatorVerticalPadding = 14.dp

    /** Space between the icon and the label. */
    val LabelSpacing = 8.dp

    /** Shadow elevation of the bar. */
    val Elevation = 12.dp

    /**
     * Creates the default [FloatingNavBarAnimation].
     *
     * [travel] and [expand] use tweens with an overshoot easing curve instead of springs. They
     * produce a similar bounce, but finish at a fixed time with zero velocity, so the bar does
     * not keep moving by small amounts after the animation has visually ended. The second value
     * of each [CubicBezierEasing] controls the amount of overshoot.
     */
    fun animation(
        collapse: AnimationSpec<Float> = tween(durationMillis = 180, easing = FastOutSlowInEasing),
        travel: AnimationSpec<Float> = tween(
            durationMillis = 420,
            easing = CubicBezierEasing(0.34f, 1.15f, 0.64f, 1f)
        ),
        expand: AnimationSpec<Float> = tween(
            durationMillis = 380,
            easing = CubicBezierEasing(0.34f, 1.3f, 0.64f, 1f)
        ),
        expandDelayMillis: Int = 120
    ) = FloatingNavBarAnimation(collapse, travel, expand, expandDelayMillis)

    /** Creates the default [FloatingNavBarColors]. */
    @Composable
    fun colors(
        container: Color = Color.White,
        indicator: Color = MaterialTheme.colorScheme.primary,
        selectedContent: Color = Color.White,
        unselectedContent: Color = Color(0xFFA0A4AE)
    ) = FloatingNavBarColors(container, indicator, selectedContent, unselectedContent)
}

/**
 * A floating, pill-shaped bottom navigation bar.
 *
 * Unselected items are square and show only their icon. The selected item is a pill that wraps
 * its icon and label. When the selection changes, the previous pill collapses into a circle, the
 * circle moves to the new item, and the new pill expands. Unselected items share the remaining
 * width equally.
 *
 * The bar is laid out in a custom [Layout]. Animated values are read during the layout phase, so
 * the bar does not recompose while animating, and positions are applied with sub-pixel precision.
 * The layout assumes a left-to-right layout direction.
 *
 * The label width of each item is measured once per [items] instance, so pass a stable list, such
 * as a top-level value.
 *
 * @param items Destinations to display. Must not be empty.
 * @param selectedIndex Index of the selected item.
 * @param onItemSelected Called with the index of an item when it is tapped.
 * @param modifier Modifier applied to the bar.
 * @param colors Colors of the bar. See [FloatingNavBarDefaults.colors].
 * @param containerPadding Space between the edge of the bar and the indicator.
 * @param indicatorHorizontalPadding Horizontal padding of the expanded indicator.
 * @param indicatorVerticalPadding Vertical padding of the indicator. The unselected state is a
 * circle of the icon size plus this padding on every side.
 * @param labelSpacing Space between the icon and the label.
 * @param elevation Shadow elevation of the bar.
 * @param animation Animation specs. See [FloatingNavBarDefaults.animation].
 * @param hapticFeedback Whether to perform haptic feedback when the selection changes.
 */
@Composable
fun FloatingNavBar(
    items: List<FloatingNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    colors: FloatingNavBarColors = FloatingNavBarDefaults.colors(),
    containerPadding: Dp = FloatingNavBarDefaults.ContainerPadding,
    indicatorHorizontalPadding: Dp = FloatingNavBarDefaults.IndicatorHorizontalPadding,
    indicatorVerticalPadding: Dp = FloatingNavBarDefaults.IndicatorVerticalPadding,
    labelSpacing: Dp = FloatingNavBarDefaults.LabelSpacing,
    elevation: Dp = FloatingNavBarDefaults.Elevation,
    animation: FloatingNavBarAnimation = FloatingNavBarDefaults.animation(),
    hapticFeedback: Boolean = true
) {
    require(items.isNotEmpty()) { "FloatingNavBar needs at least one item" }

    // Per-item animation state, both in the range 0..1:
    // - expansion: 0 is a circle showing only the icon, 1 is the full pill with its label.
    //   Drives the indicator width and the label reveal.
    // - selection: how selected the item is. Drives slot widths and icon positions.
    // Only the previously selected and the newly selected item change on a selection change.
    val expansions = remember(items.size) {
        List(items.size) { Animatable(if (it == selectedIndex) 1f else 0f) }
    }
    val selection = remember(items.size) {
        List(items.size) { Animatable(if (it == selectedIndex) 1f else 0f) }
    }

    LaunchedEffect(selectedIndex, items.size) {
        coroutineScope {
            expansions.forEachIndexed { i, expansion ->
                if (i != selectedIndex) launch { expansion.animateTo(0f, animation.collapse) }
            }
            selection.forEachIndexed { i, state ->
                launch { state.animateTo(if (i == selectedIndex) 1f else 0f, animation.travel) }
            }
            launch {
                delay(animation.expandDelayMillis.toLong())
                expansions[selectedIndex].animateTo(1f, animation.expand)
            }
        }
    }

    val haptics = LocalHapticFeedback.current
    val barShape = RoundedCornerShape(50)

    val labelStyle = MaterialTheme.typography.labelLarge.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.W400
    )
    val textMeasurer = rememberTextMeasurer()
    val labelWidths = remember(items, labelStyle, textMeasurer) {
        IntArray(items.size) { textMeasurer.measure(items[it].label, labelStyle).size.width }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = elevation,
                shape = barShape,
                clip = false,
                ambientColor = Color.Black.copy(alpha = 0.08f),
                spotColor = Color.Black.copy(alpha = 0.14f)
            )
            .background(colors.container, barShape)
            .padding(containerPadding)
    ) {
        // Children of the layout, in order:
        //   0:                 the indicator
        //   1 + 3 * i .. +2:   touch target, icon and label of item i
        Layout(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup(),
            content = {
                Box(Modifier.background(colors.indicator, barShape))
                items.forEachIndexed { index, item ->
                    FloatingNavBarItem(
                        item = item,
                        selected = index == selectedIndex,
                        colors = colors,
                        labelStyle = labelStyle,
                        tintDelayMillis = animation.expandDelayMillis,
                        onClick = {
                            if (index != selectedIndex) {
                                if (hapticFeedback) {
                                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                }
                                onItemSelected(index)
                            }
                        }
                    )
                }
            }
        ) { measurables, constraints ->
            val count = items.size
            val total = constraints.maxWidth
            val totalF = total.toFloat()
            val hPad = indicatorHorizontalPadding.toPx()
            val vPad = indicatorVerticalPadding.toPx()
            val gap = labelSpacing.toPx()
            val iconSizes = FloatArray(count) { items[it].iconSize.toPx() }

            // The bar height equals the width of an unselected item, which is a circle of the
            // icon size plus the vertical padding on every side.
            val barHeightF = (iconSizes.maxOrNull() ?: 0f) + 2f * vPad
            val barHeight = barHeightF.roundToInt()

            // Width of an expanded item: padding, icon, gap, label and padding.
            val expandedWidths = FloatArray(count) { iconSizes[it] + gap + labelWidths[it] + 2f * hPad }

            // Slot layout. Each item's slot blends between the width of an unselected item and
            // its expanded width according to its selection value. Unselected items share the
            // remaining width equally. The values are not clamped, so easing overshoot passes
            // through, and the remaining width is normalised so the slots always fill the bar.
            val selectedness = FloatArray(count) { selection[it].value }

            var selectedTotal = 0f
            var unselectedUnits = 0f
            for (i in 0 until count) {
                selectedTotal += selectedness[i] * expandedWidths[i]
                unselectedUnits += 1f - selectedness[i]
            }
            val restWidth =
                if (unselectedUnits > 0.001f) {
                    (totalF - selectedTotal).coerceAtLeast(0f) / unselectedUnits
                } else 0f

            val edges = FloatArray(count + 1)
            for (i in 0 until count) {
                edges[i + 1] = edges[i] +
                        selectedness[i] * expandedWidths[i] + (1f - selectedness[i]) * restWidth
            }
            edges[count] = totalF

            // Icons are centered in an unselected slot and start-aligned in a selected one.
            val iconLeft = FloatArray(count) { i ->
                edges[i] + iconOffset(edges[i + 1] - edges[i], iconSizes[i], hPad, selectedness[i])
            }

            // Indicator position. It is anchored to the average icon position of the items that
            // are currently selected, weighted by selection, so it moves from the previous icon
            // to the new one.
            var anchorWeight = 0f
            var anchorSum = 0f
            for (i in 0 until count) {
                val weight = selectedness[i].coerceIn(0f, 1f)
                anchorWeight += weight
                anchorSum += weight * iconLeft[i]
            }
            val anchorIconLeft =
                if (anchorWeight > 0.001f) anchorSum / anchorWeight
                else iconLeft[selectedIndex.coerceIn(0, count - 1)]

            // Indicator size. The width grows from a circle by each item's expansion, and the
            // start padding grows from the vertical to the horizontal padding. The icon position
            // does not depend on either, so expanding never moves an icon.
            var indicatorWidthF = barHeightF
            var expansionSum = 0f
            for (i in 0 until count) {
                val expansion = expansions[i].value
                indicatorWidthF += expansion * (expandedWidths[i] - barHeightF)
                expansionSum += expansion
            }
            indicatorWidthF = max(indicatorWidthF, 1f)
            val startPadding = vPad + (hPad - vPad) * expansionSum
            val indicatorLeftF = anchorIconLeft - startPadding

            // Layout sizes are whole pixels. The indicator is measured at the next whole width
            // and scaled to the exact width when placed.
            val indicatorWidthInt = ceil(indicatorWidthF).toInt().coerceAtLeast(1)
            val indicator = measurables[0].measure(Constraints.fixed(indicatorWidthInt, barHeight))

            val touchTargets = ArrayList<Placeable>(count)
            val icons = ArrayList<Placeable>(count)
            val labels = ArrayList<Placeable>(count)
            val labelProgress = FloatArray(count)
            for (i in 0 until count) {
                val base = 1 + 3 * i
                val slotWidth = (edges[i + 1] - edges[i]).roundToInt().coerceAtLeast(0)
                touchTargets += measurables[base].measure(Constraints.fixed(slotWidth, barHeight))
                icons += measurables[base + 1].measure(Constraints())

                // The label is measured at its revealed width and clips the rest of the text, so
                // it unfolds from the start. Progress is not capped at 1, so the label follows
                // any overshoot of the indicator.
                labelProgress[i] = expansions[i].value.coerceAtLeast(0f)
                val revealedWidth = ceil(labelWidths[i] * labelProgress[i]).toInt()
                labels += measurables[base + 2].measure(
                    Constraints(minWidth = 0, maxWidth = revealedWidth)
                )
            }

            layout(total, barHeight) {
                indicator.placeWithLayer(0, 0) {
                    translationX = indicatorLeftF
                    scaleX = indicatorWidthF / indicatorWidthInt
                    transformOrigin = TransformOrigin(0f, 0.5f)
                }
                for (i in 0 until count) {
                    touchTargets[i].place(edges[i].roundToInt(), 0)

                    val icon = icons[i]
                    icon.placeWithLayer(0, (barHeight - icon.height) / 2) {
                        translationX = iconLeft[i]
                    }

                    val label = labels[i]
                    label.placeWithLayer(0, (barHeight - label.height) / 2) {
                        translationX = iconLeft[i] + iconSizes[i] + gap
                        alpha = labelProgress[i].coerceIn(0f, 1f)
                    }
                }
            }
        }
    }
}

/**
 * Emits the three children of one item into the [FloatingNavBar] layout, in this order: touch
 * target, icon and label. Their size and position are decided by the parent layout.
 */
@Composable
private fun FloatingNavBarItem(
    item: FloatingNavItem,
    selected: Boolean,
    colors: FloatingNavBarColors,
    labelStyle: TextStyle,
    tintDelayMillis: Int,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (pressed) 0.9f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "navItemPressScale"
    )

    // The tint of a newly selected icon changes after the delay, once the indicator has started
    // expanding behind it.
    val tint by animateColorAsState(
        targetValue = if (selected) colors.selectedContent else colors.unselectedContent,
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = if (selected) tintDelayMillis else 0
        ),
        label = "navItemTint"
    )

    // Touch target. Carries the selection and accessibility semantics of the item.
    Box(
        Modifier
            .selectable(
                selected = selected,
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
            .semantics { contentDescription = item.label }
    )

    // Icon, scaled down while pressed.
    Box(
        Modifier
            .clearAndSetSemantics { }
            .graphicsLayer {
                scaleX = pressScale
                scaleY = pressScale
            }
    ) {
        item.icon(tint)
    }

    // Label. Its width and alpha are set by the parent layout.
    Text(
        text = item.label,
        style = labelStyle,
        color = colors.selectedContent,
        maxLines = 1,
        softWrap = false,
        modifier = Modifier.clearAndSetSemantics { }
    )
}

/**
 * Returns the horizontal offset of an icon inside its slot: centered when [selectedness] is 0 and
 * [startPadding] from the start edge when it is 1.
 */
private fun iconOffset(
    slotWidth: Float,
    iconSize: Float,
    startPadding: Float,
    selectedness: Float
): Float {
    val centered = (slotWidth - iconSize) / 2f
    return centered + (startPadding - centered) * selectedness
}