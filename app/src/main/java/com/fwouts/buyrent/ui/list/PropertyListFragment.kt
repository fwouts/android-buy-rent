package com.fwouts.buyrent.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fwouts.buyrent.R
import dagger.hilt.android.AndroidEntryPoint

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
        listViewModel.list.observe(this, Observer {
            adapter.list = it
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        val progressBar: ProgressBar = root.findViewById(R.id.progress_bar)
        listViewModel.list.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it == null) View.VISIBLE else View.GONE
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