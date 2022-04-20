@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.mobiletestcase.extension

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

inline fun Context.colorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)