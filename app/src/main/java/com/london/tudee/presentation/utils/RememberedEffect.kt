package com.london.tudee.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.remember


@Composable
@NonRestartableComposable
fun RememberedEffect(
    vararg keys: Any?,
    effect: () -> Unit,
) {
    remember(*keys) { RememberedEffectImpl(effect) }
}

internal class RememberedEffectImpl(
    val effect: () -> Unit,
) : RememberObserver {
    override fun onRemembered() = effect()
    override fun onForgotten() = Unit
    override fun onAbandoned() = Unit
}
