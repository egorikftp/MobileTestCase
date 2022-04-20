package com.egoriku.mobiletestcase.list.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import com.egoriku.mobiletestcase.R
import com.egoriku.mobiletestcase.databinding.FragmentListBinding
import com.egoriku.mobiletestcase.detail.DetailFragment
import com.egoriku.mobiletestcase.extension.colorCompat
import com.egoriku.mobiletestcase.list.domain.model.CatalogModel
import com.egoriku.mobiletestcase.list.presentation.adapter.CatalogListAdapter
import com.egoriku.mobiletestcase.list.presentation.adapter.PaginationStateAdapter
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

private const val DIVIDER_THICKNESS = 48

class ListFragment : Fragment(R.layout.fragment_list) {

    private val binding: FragmentListBinding by viewBinding(FragmentListBinding::bind)

    private val listViewModel by viewModel<ListViewModel>()

    private var catalogAdapter: CatalogListAdapter by Delegates.notNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        catalogAdapter = CatalogListAdapter(::navigateToDetailPage)

        lifecycleScope.launchWhenResumed {
            catalogAdapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefreshLayout.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenResumed {
            listViewModel.pagingData.collectLatest {
                catalogAdapter.submitData(it)
            }
        }

        initRecyclerView()
        initSwipeRefresh()

        applyWorkaroundForScrollingIssue()
    }

    private fun applyWorkaroundForScrollingIssue() {
        //Workaround for 😒
        // https://issuetracker.google.com/issues/184874613
        // https://issuetracker.google.com/issues/186560106

        catalogAdapter.addOnPagesUpdatedListener {
            if (catalogAdapter.itemCount > 0 && listViewModel.isFirstLoad) {
                (binding.recyclerViewList.layoutManager as LinearLayoutManager)
                    .scrollToPositionWithOffset(0, 0)

                listViewModel.isFirstLoad = false
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = catalogAdapter.withLoadStateFooter(
                footer = PaginationStateAdapter {
                    catalogAdapter.retry()
                }
            )
            addItemDecoration(
                MaterialDividerItemDecoration(context, VERTICAL).apply {
                    dividerThickness = DIVIDER_THICKNESS
                    dividerColor = context.colorCompat(R.color.transparent)
                }
            )
        }
    }

    private fun initSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            catalogAdapter.refresh()
        }
    }

    private fun navigateToDetailPage(catalogModel: CatalogModel) {
        parentFragmentManager.commit {
            replace(R.id.fragment_container_view, DetailFragment.newInstance(catalogModel))
            addToBackStack(DetailFragment::class.java.simpleName)
        }
    }
}