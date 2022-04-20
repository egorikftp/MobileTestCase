package com.egoriku.mobiletestcase.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.egoriku.mobiletestcase.detail.compose.DetailScreen
import com.egoriku.mobiletestcase.detail.compose.DetailToolbar
import com.egoriku.mobiletestcase.extension.extraNotNull
import com.egoriku.mobiletestcase.extension.setThemeContent
import com.egoriku.mobiletestcase.list.domain.model.CatalogModel

class DetailFragment : Fragment() {

    private val catalogModel: CatalogModel by extraNotNull(EXTRAS)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = setThemeContent {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                DetailToolbar(title = catalogModel.description, onClose = ::closeScreen)
            }
        ) {
            DetailScreen(model = catalogModel)
        }
    }

    private fun closeScreen() = parentFragmentManager.popBackStack()

    companion object {
        private const val EXTRAS = "EXTRAS"

        fun newInstance(model: CatalogModel) = DetailFragment().apply {
            arguments = bundleOf(EXTRAS to model)
        }
    }
}