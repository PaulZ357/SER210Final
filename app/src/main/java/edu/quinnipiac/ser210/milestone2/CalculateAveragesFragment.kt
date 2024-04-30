package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCalculateAveragesBinding


class CalculateAveragesFragment : Fragment() {
	private lateinit var binding: FragmentCalculateAveragesBinding

	private val viewModel: CalculateAveragesViewModel by activityViewModels {
		val application: DataApplication = activity?.application as DataApplication
		CalculateAveragesViewModelFactory(
			application.characterDatabase.characterDao(),
			application.scrollDatabase.scrollDao()
		)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentCalculateAveragesBinding.inflate(layoutInflater)
		viewModel.characters.observe(viewLifecycleOwner) { characters: List<Character> ->
			binding.characterNameSpinner.adapter =
				ArrayAdapter(
					requireContext(),
					android.R.layout.simple_spinner_item,
					Array(characters.size) { characters[it].name })
			binding.characterNameSpinner.setSelection(viewModel.characterIndex.value!!)
		}
		viewModel.adapter.observe(viewLifecycleOwner) {
			binding.recyclerView.adapter = it
		}

		viewModel.levels.observe(viewLifecycleOwner) {
			binding.promotionLevelSpinner.adapter =
				ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
			binding.promotionLevelSpinner.setSelection(viewModel.levelIndex.value!!)
		}

		binding.characterNameSpinner.onItemSelectedListener = CharacterListener(viewModel)
		binding.promotionLevelSpinner.onItemSelectedListener = LevelListener(viewModel)

		return binding.root
	}


	class CharacterListener(private val viewModel: CalculateAveragesViewModel) :
		OnItemSelectedListener {
		override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
			viewModel.setCharacterIndex(position)
		}

		override fun onNothingSelected(parent: AdapterView<*>?) {
		}

	}

	class LevelListener(private val viewModel: CalculateAveragesViewModel) :
		OnItemSelectedListener {
		override fun onItemSelected(
			parent: AdapterView<*>?,
			view: View?,
			position: Int,
			id: Long
		) {
			viewModel.setLevelIndex(position)
		}

		override fun onNothingSelected(parent: AdapterView<*>?) {
		}
	}
}