package com.egoriku.mobiletestcase.list.presentation.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.egoriku.mobiletestcase.databinding.AdapterItemCatalogFooterBinding
import com.egoriku.mobiletestcase.extension.inflater

class PaginationStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<PaginationStateAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): VH {
        return VH(
            binding = AdapterItemCatalogFooterBinding.inflate(parent.inflater(), parent, false),
            retry = retry
        )
    }

    override fun onBindViewHolder(holder: VH, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class VH(
        private val binding: AdapterItemCatalogFooterBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
            }
        }
    }
}