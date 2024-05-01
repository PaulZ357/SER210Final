package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentEditCharacterBinding
import kotlinx.coroutines.launch

class EditCharacterFragment : Fragment() {
	private lateinit var binding: FragmentEditCharacterBinding
	private lateinit var characterDao: CharacterDao
	private val characterIndex = MutableLiveData(0)
	private val canPromote = MutableLiveData<Boolean>()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreate(savedInstanceState)
		var charactername = ""
		characterDao =
			(activity?.application as DataApplication).characterDatabase.characterDao()
		binding = FragmentEditCharacterBinding.inflate(layoutInflater)

		canPromote.observe(viewLifecycleOwner) {
			binding.strGain.isEnabled = it
			binding.magGain.isEnabled = it
			binding.skillGain.isEnabled = it
			binding.speedGain.isEnabled = it
			binding.defGain.isEnabled = it
			binding.conGain.isEnabled = it
			binding.moveGain.isEnabled = it
		}
		characterIndex.observe(viewLifecycleOwner) {index: Int ->
			if (index < DataApplication.defaultCharacters.size) {
				binding.editCharacterDeleteButton.text = "Restore"
			} else {
				binding.editCharacterDeleteButton.text = "Delete"
			}
			characterDao.getCharacters().asLiveData()
				.observe(viewLifecycleOwner) {
					if (index < it.size) {
						val character = it[index]
						binding.name.setText(character.name)
						binding.currentLevel.setText(character.baseLevel.toString())
						binding.baseHP.setText(character.baseHP.toString())
						binding.baseStr.setText(character.baseStr.toString())
						binding.baseMag.setText(character.baseMag.toString())
						binding.baseSpeed.setText(character.baseSpd.toString())
						binding.baseSkill.setText(character.baseSkl.toString())
						binding.baseLuck.setText(character.baseLck.toString())
						binding.baseDef.setText(character.baseDef.toString())
						binding.baseCon.setText(character.baseCon.toString())
						binding.baseMove.setText(character.baseMov.toString())
						binding.hpGrowth.setText(character.HPGrowth.toString())
						binding.strGrowth.setText(character.strGrowth.toString())
						binding.magGrowth.setText(character.magGrowth.toString())
						binding.skillGrowth.setText(character.sklGrowth.toString())
						binding.speedGrowth.setText(character.spdGrowth.toString())
						binding.luckGrowth.setText(character.lckGrowth.toString())
						binding.defGrowth.setText(character.defGrowth.toString())
						binding.conGrowth.setText(character.conGrowth.toString())
						binding.moveGrowth.setText(character.movGrowth.toString())
						binding.canPromote.setSelection(if (character.canPromote) 0 else 1)
						binding.strGain.setText(character.strGain.toString())
						binding.magGain.setText(character.magGain.toString())
						binding.skillGain.setText(character.sklGain.toString())
						binding.speedGain.setText(character.spdGain.toString())
						binding.defGain.setText(character.defGain.toString())
						binding.conGain.setText(character.conGain.toString())
						binding.moveGain.setText(character.movGain.toString())

						canPromote.value = character.canPromote
					}
					else {
						characterIndex.value = characterIndex.value?.minus(1)
					}
				}
		}

		characterDao.getCharacters().asLiveData()
			.observe(viewLifecycleOwner) { characters: List<Character> ->
				binding.charSpinner.adapter = ArrayAdapter(
					requireContext(),
					android.R.layout.simple_spinner_item,
					Array(characters.size) { characters[it].name }
				)
			}

		binding.charSpinner.onItemSelectedListener = CharacterListener(characterIndex)

		binding.canPromote.adapter = ArrayAdapter(
			requireContext(),
			android.R.layout.simple_spinner_item,
			arrayOf(true, false)
		)
		binding.canPromote.onItemSelectedListener = LevelListener(canPromote)

		binding.editCharacterOKButton.setOnClickListener {
			// ok button
			Toast.makeText(this.context, "Edited character $charactername", Toast.LENGTH_SHORT)
				.show()
			lifecycleScope.launch {
				val character = Character(
					characterIndex.value!! + 1,
					binding.name.text.toString(),
					binding.currentLevel.text.toString().toInt(),
					binding.baseHP.text.toString().toInt(),
					binding.baseStr.text.toString().toInt(),
					binding.baseMag.text.toString().toInt(),
					binding.baseSpeed.text.toString().toInt(),
					binding.baseSkill.text.toString().toInt(),
					binding.baseLuck.text.toString().toInt(),
					binding.baseDef.text.toString().toInt(),
					binding.baseCon.text.toString().toInt(),
					binding.baseMove.text.toString().toInt(),
					binding.hpGrowth.text.toString().toInt(),
					binding.strGrowth.text.toString().toInt(),
					binding.magGrowth.text.toString().toInt(),
					binding.skillGrowth.text.toString().toInt(),
					binding.speedGrowth.text.toString().toInt(),
					binding.luckGrowth.text.toString().toInt(),
					binding.defGrowth.text.toString().toInt(),
					binding.conGrowth.text.toString().toInt(),
					binding.moveGrowth.text.toString().toInt(),
					binding.canPromote.selectedItem as Boolean,
					binding.strGain.text.toString().toInt(),
					binding.magGain.text.toString().toInt(),
					binding.skillGain.text.toString().toInt(),
					binding.speedGain.text.toString().toInt(),
					binding.defGain.text.toString().toInt(),
					binding.conGain.text.toString().toInt(),
					binding.moveGain.text.toString().toInt()
				)
				characterDao.update(character)
			}

		}
		binding.editCharacterDeleteButton.setOnClickListener {
			if (characterIndex.value!! < DataApplication.defaultCharacters.size) {
				// delete button
				Toast.makeText(
					this.context,
					"Restored character $charactername",
					Toast.LENGTH_SHORT
				)
					.show()
				val character = DataApplication.defaultCharacters[characterIndex.value!!]
				lifecycleScope.launch {
					characterDao.update(character)
				}
			} else {
				println("DeleteA")
				// delete button
				Toast.makeText(this.context, "Deleted character $charactername", Toast.LENGTH_SHORT)
					.show()
				lifecycleScope.launch {
					var delete = true
					val characters = characterDao.getCharacters()
					characters.collect { data ->
						// Update the UI with the new data
						if (delete) {
							println("DeleteB")
							characterDao.delete(data[characterIndex.value!!])
							delete = false
							characterIndex.value = data.size - 1
						}
					}
				}
			}
		}
		return binding.root
	}

	class CharacterListener(private val characterIndex: MutableLiveData<Int>) :
		AdapterView.OnItemSelectedListener {
		override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
			characterIndex.value = parent!!.selectedItemPosition
		}

		override fun onNothingSelected(parent: AdapterView<*>?) {
		}

	}

	class LevelListener(private val canPromote: MutableLiveData<Boolean>) :
		AdapterView.OnItemSelectedListener {
		override fun onItemSelected(
			parent: AdapterView<*>?,
			view: View?,
			position: Int,
			id: Long
		) {
			canPromote.value = parent!!.selectedItem as Boolean
		}

		override fun onNothingSelected(parent: AdapterView<*>?) {
		}
	}

}