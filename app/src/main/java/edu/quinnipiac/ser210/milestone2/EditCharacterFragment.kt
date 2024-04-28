package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentEditCharacterBinding
import kotlinx.coroutines.launch

class EditCharacterFragment : Fragment() {
	lateinit var binding: FragmentEditCharacterBinding
	lateinit var characterDao: CharacterDao
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		var charactername = ""
		var character: Character?
		characterDao =
			(activity?.application as DataApplication).characterDatabase.characterDao()
		binding = FragmentEditCharacterBinding.inflate(layoutInflater)
		/*characterDao.getCharacters().asLiveData()
			.observe(viewLifecycleOwner) { characters: List<Character> ->
				binding.charSpinner.adapter =
					ArrayAdapter(
						requireContext(),
						android.R.layout.simple_spinner_item,
						Array(characters.size) { characters[it].name })
				character = characters[0]
				charactername = character!!.name
			}*/
		binding.editCharacterOKButton.setOnClickListener {
			// ok button
			Toast.makeText(this.context, "Edited character " + charactername, 4).show()
			lifecycleScope.launch {
			}
		}
		binding.editCharacterDeleteButton.setOnClickListener {
			// delete button
			Toast.makeText(this.context, "Deleted character " + charactername, 4).show()
			lifecycleScope.launch {
				/*if (character) {
					characterDao.delete(character!!)
				}*/
			}
		}
	}

}