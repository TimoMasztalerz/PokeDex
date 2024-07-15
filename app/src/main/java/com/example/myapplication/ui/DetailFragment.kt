package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.myapplication.MainViewModel
import com.example.myapplication.databinding.FragmentDetailBinding
import com.example.myapplication.model.PokemonDetailResponse

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonId = args.pokemonId
        viewModel.loadPokemonDetails(pokemonId)
        observeViewModel()
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewModel() {
        viewModel.pokemonDetails.observe(viewLifecycleOwner) { pokemon ->
            updateUI(pokemon)
        }

    }

    private fun updateUI(pokemon: PokemonDetailResponse) {
        binding.pokemonNameTextView.text = pokemon.name.capitalize()
        binding.pokemonIdTextView.text = "ID: ${pokemon.id}"
        binding.pokemonImageView.load(pokemon.sprites.front_default)
        binding.pokemonTypesTextView.text = pokemon.types.joinToString(", ") { it.type.name.capitalize() }
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}