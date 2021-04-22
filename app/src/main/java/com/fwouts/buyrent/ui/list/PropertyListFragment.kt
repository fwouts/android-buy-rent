package com.fwouts.buyrent.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.fwouts.buyrent.databinding.FragmentPropertyListBinding
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
        val binding = FragmentPropertyListBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = listViewModel.adapter
        binding.swipeRefreshContainer.setOnRefreshListener {
            listViewModel.refresh()
        }
        binding.retryButton.setOnClickListener {
            listViewModel.refresh()
        }
        listViewModel.refreshing.observe(viewLifecycleOwner, Observer { refreshing ->
            binding.swipeRefreshContainer.isRefreshing = refreshing
        })
        listViewModel.showEmpty.observe(viewLifecycleOwner, Observer { show ->
            binding.emptyView.visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
        listViewModel.showError.observe(viewLifecycleOwner, Observer { show ->
            binding.errorView.visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })

        return binding.root
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