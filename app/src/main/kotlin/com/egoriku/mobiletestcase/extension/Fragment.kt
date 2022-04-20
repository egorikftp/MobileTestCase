package com.egoriku.mobiletestcase.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.google.android.material.composethemeadapter.MdcTheme

inline fun <reified T : Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    requireNotNull(if (value is T) value else default) { key }
}

@Suppress("NOTHING_TO_INLINE")
inline fun Fragment.setThemeContent(
    noinline content: @Composable () -> Unit
) = ComposeView(requireContext()).apply {
    setContent {
        MdcTheme {
            content()
        }
    }
}