package com.egoriku.mobiletestcase.detail.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.mobiletestcase.R
import com.egoriku.mobiletestcase.list.domain.model.CatalogModel

@Composable
fun DetailScreen(model: CatalogModel) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        DetailLogo(
            url = model.imageUrl,
            contentDescription = model.description
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = stringResource(R.string.catalog_id_template, model.id),
            style = MaterialTheme.typography.h6
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 8.dp),
            text = stringResource(
                R.string.catalog_confidence_template,
                model.confidence
            ),
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        model = CatalogModel(
            id = "catalog id",
            imageUrl = "",
            confidence = "0.00",
            description = "image description"
        )
    )
}

