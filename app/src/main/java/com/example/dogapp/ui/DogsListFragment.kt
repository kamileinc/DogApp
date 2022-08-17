package com.example.dogapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.di.MyViewModelFactory
import com.example.dogapp.adapter.RecyclerAdapter
import com.example.dogapp.utils.Resource
import com.example.dogapp.databinding.FragmentDogsListBinding
import com.example.dogapp.viewModel.DogsListViewModel

class DogsListFragment : Fragment() {

    private var _binding: FragmentDogsListBinding? = null
    private val binding get() = _binding!!
    var recyclerView: RecyclerView? = null
    var manager: GridLayoutManager? = null
    var adapter: RecyclerAdapter? = null
    private lateinit var viewModel: DogsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogsListBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpViewModel()

        viewModel.fetchData()

        setUpObserver(view)

        return view
    }

    private fun setUpViewModel() {
        val assets = requireContext().assets
        val viewModelFactory = MyViewModelFactory(assets)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DogsListViewModel::class.java)
    }
    private fun setUpObserver(view: View) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.urlList.observe(requireActivity()) { event ->
                when (event) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        recyclerView = binding.rvDesign
                        manager = GridLayoutManager(requireContext(), 2)
                        recyclerView!!.layoutManager = manager
                        adapter = event.data?.let { RecyclerAdapter(requireContext(), it, view) }
                        recyclerView!!.adapter = adapter
                    }
                    is Resource.Failure -> {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            event.message?.asString(context),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
