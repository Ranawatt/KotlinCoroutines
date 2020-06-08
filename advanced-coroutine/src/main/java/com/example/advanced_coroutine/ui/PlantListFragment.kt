package com.example.advanced_coroutine.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.advanced_coroutine.PlantListViewModel
import com.example.advanced_coroutine.PlantRepository
import com.example.advanced_coroutine.R
import com.example.advanced_coroutine.databinding.FragmentPlantListBinding
import com.example.advanced_coroutine.util.Injector
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_plant_list.*

class PlantListFragment : Fragment() {

    private val viewModel: PlantListViewModel by viewModels {
        Injector.providePlantListViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlantListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // show the spinner when [MainViewModel.spinner] is true
//        viewModel.spinner.observe(viewLifecycleOwner) { show ->
//            binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
//        }
        viewModel.spinner.observe(viewLifecycleOwner, Observer {
            binding.spinner.visibility = if (it) View.VISIBLE else View.GONE
        })

        // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
        viewModel.snackbar.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })

        val adapter = PlantAdapter()
        binding.plantList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun subscribeUi(adapter: PlantAdapter) {
//        viewModel.plantsUsingFlow.observe(viewLifecycleOwner) { plants ->
//            adapter.submitList(plants)
//        }
//    }
    private fun subscribeUi(adapter: PlantAdapter) {
        viewModel.plantsUsingFlow.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearGrowZoneNumber()
            } else {
                setGrowZoneNumber(9)
            }
        }
    }
}

/**
 * Factory for creating a [PlantListViewModel] with a constructor that takes a [PlantRepository].
 */
class PlantListViewModelFactory(
    private val repository: PlantRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = PlantListViewModel(repository) as T
}