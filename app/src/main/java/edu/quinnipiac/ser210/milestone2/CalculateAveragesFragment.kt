package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCalculateAveragesBinding
import kotlinx.coroutines.launch


class CalculateAveragesFragment : Fragment() {
	private lateinit var binding: FragmentCalculateAveragesBinding
	private lateinit var characterDao: CharacterDao

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentCalculateAveragesBinding.inflate(layoutInflater)

		characterDao = (activity?.application as DataApplication).characterDatabase.characterDao()
		characterDao.getCharacters().asLiveData()
			.observe(viewLifecycleOwner) { characters: List<Character> ->
				binding.characterNameSpinner.adapter =
					ArrayAdapter(
						requireContext(),
						android.R.layout.simple_spinner_item,
						Array(characters.size) { characters[it].name })
				val character = characters[0]
				binding.promotionLevelSpinner.adapter = ArrayAdapter(
					requireContext(),
					android.R.layout.simple_spinner_item,
					arrayOf(if (character.baseLevel > 10) character.baseLevel else 10)
				)
				update()
			}

		binding.updateButton.setOnClickListener { update() }

		return binding.root
	}

	private fun update() {
		val recyclerView = binding.recyclerView
		val promotionLevel = binding.promotionLevelSpinner.selectedItem as Int
		lifecycleScope.launch {
			characterDao.getCharacter(binding.characterNameSpinner.selectedItem as String)
				.collect { character: Character ->
					recyclerView.adapter =
						AveragesAdapter(character, if (character.canPromote) promotionLevel else 20)
					val startingLevel = if (character.baseLevel > 10) character.baseLevel else 10
					if (character.canPromote) {
						val levels = Array(21 - startingLevel) { it + 10 }
						binding.promotionLevelSpinner.adapter = ArrayAdapter(
							requireContext(),
							android.R.layout.simple_spinner_item,
							levels
						)
					}
					binding.promotionLevelSpinner.isEnabled = character.canPromote
				}
		}
	}
}