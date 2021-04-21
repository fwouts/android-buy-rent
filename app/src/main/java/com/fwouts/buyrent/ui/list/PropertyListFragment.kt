package com.fwouts.buyrent.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.fwouts.buyrent.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PropertyListFragment : Fragment() {
    enum class ListType {
        BUY,
        RENT
    }

    private lateinit var type: ListType
    private lateinit var listViewModel: PropertyListViewModel
    private lateinit var adapter: PropertyListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments?.getString(ARG_TYPE)?.let { ListType.valueOf(it) } ?: ListType.BUY
        adapter = PropertyListAdapter()
        listViewModel = ViewModelProvider(this).get(PropertyListViewModel::class.java)
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
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        val progressBar: ProgressBar = root.findViewById(R.id.progress_bar)
        adapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner, Observer { state ->
            progressBar.visibility = if (state.refresh is LoadState.Loading) View.VISIBLE else View.GONE
        })
        return root
    }

    companion object {
        private const val ARG_TYPE = "type"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
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