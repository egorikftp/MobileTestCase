package com.egoriku.mobiletestcase.list.presentation.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.egoriku.mobiletestcase.R
import com.egoriku.mobiletestcase.databinding.AdapterItemCatalogListBinding
import com.egoriku.mobiletestcase.extension.inflater
import com.egoriku.mobiletestcase.list.domain.model.CatalogModel

class CatalogListAdapter(
    private val onClick: (CatalogModel) -> Unit
) : PagingDataAdapter<CatalogModel, CatalogListAdapter.VH>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(
        binding = AdapterItemCatalogListBinding.inflate(parent.inflater(), parent, false),
        onClick = onClick
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.run {
            holder.bind(this)
        }
    }

    inner class VH(
        private val binding: AdapterItemCatalogListBinding,
        private val onClick: (CatalogModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private var catalogModel: CatalogModel? = null

        init {
            binding.root.setOnClickListener {
                catalogModel?.run(onClick)
            }
        }

        fun bind(model: CatalogModel) = binding.bind(model)

        private fun AdapterItemCatalogListBinding.bind(model: CatalogModel) {
            catalogModel = model

            adapterItemCatalogImage.load(model.imageUrl) {
                transformations(RoundedCornersTransformation(50f))
                placeholder(R.color.colorPlaceholder)
                crossfade(true)
            }
            adapterItemCatalogDescription.text = model.description

            adapterItemCatalogConfidence.text = String.format(
                root.context.getString(R.string.catalog_confidence_template),
                model.confidence
            )

            adapterItemCatalogId.text = String.format(
                root.context.getString(R.string.catalog_id_template),
                model.id
            )
        }
    }

    internal class DiffCallback : DiffUtil.ItemCallback<CatalogModel>() {

        override fun areItemsTheSame(
            oldItem: CatalogModel,
            newItem: CatalogModel
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: CatalogModel,
            newItem: CatalogModel
        ) = oldItem.id == newItem.id
    }
}