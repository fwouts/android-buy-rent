package com.fwouts.buyrent.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fwouts.buyrent.R
import com.fwouts.buyrent.repositories.ListType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PropertyListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: PropertyListViewModel.Factory

    private lateinit var type: ListType
    private lateinit var listViewModel: PropertyListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getString(ARG_TYPE)?.let { ListType.valueOf(it) } ?: ListType.BUY
        listViewModel = viewModelFactory.create(type)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_property_list, container, false)
        val swipeRefreshContainer: SwipeRefreshLayout =
            root.findViewById(R.id.swipe_refresh_container)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        val emptyView: View = root.findViewById(R.id.empty_view)
        val errorView: View = root.findViewById(R.id.error_view)
        val retryButton: Button = root.findViewById(R.id.retry_button)

        recyclerView.adapter = listViewModel.adapter
        swipeRefreshContainer.setOnRefreshListener {
            listViewModel.refresh()
        }
        retryButton.setOnClickListener {
            listViewModel.refresh()
        }
        listViewModel.showEmpty.observe(viewLifecycleOwner, Observer { show ->
            emptyView.visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
        listViewModel.showError.observe(viewLifecycleOwner, Observer { show ->
            errorView.visibility = if (show) {
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