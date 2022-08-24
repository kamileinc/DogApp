package com.example.dogapp.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentDogPhotoBinding
import com.example.dogapp.utils.Resource
import com.example.dogapp.viewModel.DogPhotoViewModel

class DogPhotoFragment : Fragment() {

    private var _binding: FragmentDogPhotoBinding? = null
    private val binding get() = _binding!!
    private val args: DogPhotoFragmentArgs by navArgs()
    private val viewModel: DogPhotoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogPhotoBinding.inflate(inflater, container, false)
        val view = binding.root

        setUpObserver()

        viewModel.getSum(args.url)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.urlNumber.observe(viewLifecycleOwner) { event ->
                when (event) {
                    is Resource.Success -> {
                        loadPhoto(event.data)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun loadPhoto(urlNumber: Int?) {
        Glide.with(requireContext())
            .load(args.url)
            .placeholder(R.drawable.ic_baseline_no_photography_24)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    target: Target<Drawable>?,
                    p3: Boolean
                ): Boolean {
                    Toast.makeText(requireContext(),
                        resources.getString(R.string.photo_not_found), Toast.LENGTH_SHORT).show()
                    return false
                }
                override fun onResourceReady(
                    p0: Drawable?,
                    p1: Any?,
                    target: Target<Drawable>?,
                    p3: DataSource?,
                    p4: Boolean
                ): Boolean {
                    binding.number.text = urlNumber.toString()
                    return false
                }
            })
            .skipMemoryCache(true)
            .into(binding.imageView)
    }
}
