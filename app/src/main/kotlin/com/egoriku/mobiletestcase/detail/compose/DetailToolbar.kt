package com.egoriku.mobiletestcase.detail.compose

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailToolbar(title: String, onClose: () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(Icons.Filled.Close, contentDescription = null)
            }
        }
    )
}

@Preview
@Composable
private fun DetailToolbarPreview() {
    DetailToolbar(title = "Test") {}
}
