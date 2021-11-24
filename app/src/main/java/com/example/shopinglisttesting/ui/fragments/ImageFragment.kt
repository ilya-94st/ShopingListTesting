package com.example.shopinglisttesting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shopinglisttesting.adapters.ImageAdapter
import com.example.shopinglisttesting.base.BaseFragment
import com.example.shopinglisttesting.databinding.FragmentImageBinding
import com.example.shopinglisttesting.other.Constants
import com.example.shopinglisttesting.other.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageFragment @Inject constructor(
   val imageAdapter: ImageAdapter
) : BaseFragment<FragmentImageBinding>() {
    lateinit var viewModel: ShoppingViewModel

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentImageBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        initAdapter()
        searchImages()
        subscribeObserver()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setImagesUrl(it)
        }
    }

    // функция поиска
    private fun searchImages() {
        var job: Job? = null // куротина
        binding.etSearch.addTextChangedListener {
            editable->
            job?.cancel() // имитируем задержку при наборе текста(каждый раз когда мы вбиваем букву в эдит текст происходи задержка в нашем случае 500 млсекунд)
            job = MainScope().launch { // вызываем куротину в MainScope()
                delay(500)
                editable.let {
                    if(editable.toString().isNotEmpty()){
                        viewModel.searchImagesPix(editable.toString()) // вызываем нашу функцию поиска из viewModel
                        binding.etSearch.hideKeyboard()
                    }
                }
            }
        }
    }

    private fun subscribeObserver() {
        viewModel.imagesPix().observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled().let {
                result->
                when(result?.status) {
                    Resources.Status.SUCCESSES -> {
                        val urls = result.data?.hits?.map { imageResult ->  imageResult.previewURL }
                        imageAdapter.images = urls ?: listOf()
                        hideProgressBar()
                    }
                    Resources.Status.ERROR -> {
                        hideProgressBar()
                        snackBar(result.message?: "Error")
                    }
                    Resources.Status.LOADING -> {
                        showProgressBar()
                    }
                }
            }
        })
    }

    private fun initAdapter() {
        binding.rvImages.adapter = imageAdapter
        binding.rvImages.layoutManager = GridLayoutManager(requireContext(), Constants.GRID_SPAN_COUNT)
    }


    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}