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
		characterIndex.observe(viewLifecycleOwner) {
			if (characterIndex.value!! < DataApplication.defaultCharacters.size) {
				binding.editCharacterDeleteButton.text = "Restore"
			} else {
				binding.editCharacterDeleteButton.text = "Delete"
			}
			characterDao.getCharacter(characterIndex.value!! + 1).asLiveData()
				.observe(viewLifecycleOwner) {
					binding.name.setText(it.name)
					binding.currentLevel.setText(it.baseLevel.toString())
					binding.baseHP.setText(it.baseHP.toString())
					binding.baseStr.setText(it.baseStr.toString())
					binding.baseMag.setText(it.baseMag.toString())
					binding.baseSpeed.setText(it.baseSpd.toString())
					binding.baseSkill.setText(it.baseSkl.toString())
					binding.baseLuck.setText(it.baseLck.toString())
					binding.baseDef.setText(it.baseDef.toString())
					binding.baseCon.setText(it.baseCon.toString())
					binding.baseMove.setText(it.baseMov.toString())
					binding.hpGrowth.setText(it.HPGrowth.toString())
					binding.strGrowth.setText(it.strGrowth.toString())
					binding.magGrowth.setText(it.magGrowth.toString())
					binding.skillGrowth.setText(it.sklGrowth.toString())
					binding.speedGrowth.setText(it.spdGrowth.toString())
					binding.luckGrowth.setText(it.lckGrowth.toString())
					binding.defGrowth.setText(it.defGrowth.toString())
					binding.conGrowth.setText(it.conGrowth.toString())
					binding.moveGrowth.setText(it.movGrowth.toString())
					binding.canPromote.setSelection(if (it.canPromote) 0 else 1)
					binding.strGain.setText(it.strGain.toString())
					binding.magGain.setText(it.magGain.toString())
					binding.skillGain.setText(it.sklGain.toString())
					binding.speedGain.setText(it.spdGain.toString())
					binding.defGain.setText(it.defGain.toString())
					binding.conGain.setText(it.conGain.toString())
					binding.moveGain.setText(it.movGain.toString())

					canPromote.value = it.canPromote
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
				// delete button
				Toast.makeText(this.context, "Deleted character $charactername", Toast.LENGTH_SHORT)
					.show()
				val character = characterDao.getCharacter(characterIndex.value!! + 1)
				lifecycleScope.launch {
					character.collect { data ->
						// Update the UI with the new data
						characterDao.delete(data)
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