package com.fwouts.buyrent.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fwouts.buyrent.R
import com.fwouts.buyrent.repositories.ListType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PropertyListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: PropertyListViewModel.Factory

    private lateinit var type: ListType
    private lateinit var listViewModel: PropertyListViewModel
    private lateinit var adapter: PropertyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getString(ARG_TYPE)?.let { ListType.valueOf(it) } ?: ListType.BUY
        adapter = PropertyListAdapter()
        listViewModel = viewModelFactory.create(type)
        lifecycleScope.launch {
            listViewModel.list.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val swipeRefreshContainer: SwipeRefreshLayout =
            root.findViewById(R.id.swipe_refresh_container)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        val emptyView: View = root.findViewById(R.id.empty_view)

        recyclerView.adapter = adapter
        swipeRefreshContainer.setOnRefreshListener {
            adapter.refresh()
        }
        val loadingState = adapter.loadStateFlow.asLiveData()
        loadingState.observe(viewLifecycleOwner, Observer { state ->
            swipeRefreshContainer.isRefreshing = (state.refresh is LoadState.Loading)
            emptyView.visibility =
                if (state.refresh is LoadState.NotLoading && adapter.itemCount == 0) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        })
        return root
    }

    companion object {
        private const val ARG_TYPE = "type"

        @JvmStatic
        fun newInstance(type: ListType): PropertyListFragment {
            return PropertyListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TYPE, type.name)
                }
            }
        }
    }
}