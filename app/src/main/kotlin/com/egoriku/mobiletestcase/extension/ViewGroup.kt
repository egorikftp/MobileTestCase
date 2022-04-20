@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.mobiletestcase.extension

import android.view.LayoutInflater
import android.view.ViewGroup

inline fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)