package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentEditCharacterBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EditCharacterFragment : Fragment() {

    lateinit var binding: FragmentEditCharacterBinding
    lateinit var characterDao: CharacterDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var charactername = ""
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
            Toast.makeText(this.context,"Edited character "+charactername,Toast.LENGTH_SHORT).show()
            val character = characterDao.getCharacter(id)
            lifecycleScope.launch {
                // looks for the
                character.collect { data ->
                    // Update the UI with the new data
                    data.baseHP = binding.baseHP.toString().toInt()
                    data.baseStr = binding.baseStr.toString().toInt()
                    data.baseMag = binding.baseMag.toString().toInt()
                    data.baseSkl = binding.baseSkill.toString().toInt()
                    data.baseSpd = binding.baseSpeed.toString().toInt()
                    data.baseLck = binding.baseLuck.toString().toInt()
                    data.baseDef = binding.baseDef.toString().toInt()
                    data.baseCon = binding.baseCon.toString().toInt()
                    data.HPGrowth = binding.baseHP.toString().toInt()
                    data.strGrowth = binding.baseStr.toString().toInt()
                    data.magGrowth = binding.magGrowth.toString().toInt()
                    data.sklGrowth = binding.skillGrowth.toString().toInt()
                    data.spdGrowth = binding.speedGrowth.toString().toInt()
                    data.lckGrowth = binding.luckGrowth.toString().toInt()
                    data.defGrowth = binding.defGrowth.toString().toInt()
                    data.conGrowth = binding.conGrowth.toString().toInt()
                    characterDao.update(data)
                }
            }
        }
        binding.editCharacterDeleteButton.setOnClickListener {
            // delete button
            Toast.makeText(this.context,"Deleted character "+charactername,Toast.LENGTH_SHORT).show()
            val character = characterDao.getCharacter(id)
            lifecycleScope.launch {
                character.collect { data ->
                    // Update the UI with the new data
                    characterDao.delete(data)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_character, container, false)
    }

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