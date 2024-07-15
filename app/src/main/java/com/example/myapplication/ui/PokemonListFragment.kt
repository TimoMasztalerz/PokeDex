package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication.MainViewModel
import com.example.myapplication.databinding.FragmentPokemonListBinding

class PokemonListFragment : Fragment() {
    private lateinit var binding: FragmentPokemonListBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            Log.d("pokemonList", list.toString())
            val adapter = ItemAdapter(list)
            binding.pokemonRecyclerView.adapter = adapter
        }
    }
}