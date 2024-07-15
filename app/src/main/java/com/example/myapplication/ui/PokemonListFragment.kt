package com.example.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainViewModel
import com.example.myapplication.databinding.FragmentPokemonListBinding

class PokemonListFragment : Fragment() {
    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: PokeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupTypeFilterSpinner()
        setupSortSpinner()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = PokeAdapter()
        binding.pokemonRecyclerView.apply {
            this.adapter = this@PokemonListFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupTypeFilterSpinner() {
        val types = listOf("All Types", "Normal", "Fire", "Water", "Grass", "Poison", "Flying", "Bug")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.typeFilterSpinner.adapter = spinnerAdapter
        binding.typeFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedType = types[position]
                val selectedSort = binding.sortSpinner.selectedItem.toString()
                viewModel.applyFilterAndSort(selectedType, selectedSort)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupSortSpinner() {
        val sortOptions = listOf("ID", "Alphabetical", "Type")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.sortSpinner.adapter = spinnerAdapter
        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedType = binding.typeFilterSpinner.selectedItem.toString()
                val selectedSort = sortOptions[position]
                viewModel.applyFilterAndSort(selectedType, selectedSort)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun observeViewModel() {
        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }}





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}