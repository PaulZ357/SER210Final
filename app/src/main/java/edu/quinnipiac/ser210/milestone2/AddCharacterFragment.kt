package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentAddCharacterBinding
import kotlinx.coroutines.launch

class AddCharacterFragment : Fragment() {
	private lateinit var binding: FragmentAddCharacterBinding
	private lateinit var characterDao: CharacterDao
	private val canPromote = MutableLiveData<Boolean>()
	private var nextId = 0

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreate(savedInstanceState)
		characterDao =
			(activity?.application as DataApplication).characterDatabase.characterDao()
		binding = FragmentAddCharacterBinding.inflate(layoutInflater)

		val initCharacter = DataApplication.defaultCharacters[0]
		binding.name.setText(initCharacter.name)
		binding.currentLevel.setText(initCharacter.baseLevel.toString())
		binding.baseHP.setText(initCharacter.baseHP.toString())
		binding.baseStr.setText(initCharacter.baseStr.toString())
		binding.baseMag.setText(initCharacter.baseMag.toString())
		binding.baseSpeed.setText(initCharacter.baseSpd.toString())
		binding.baseSkill.setText(initCharacter.baseSkl.toString())
		binding.baseLuck.setText(initCharacter.baseLck.toString())
		binding.baseDef.setText(initCharacter.baseDef.toString())
		binding.baseCon.setText(initCharacter.baseCon.toString())
		binding.baseMove.setText(initCharacter.baseMov.toString())
		binding.hpGrowth.setText(initCharacter.HPGrowth.toString())
		binding.strGrowth.setText(initCharacter.strGrowth.toString())
		binding.magGrowth.setText(initCharacter.magGrowth.toString())
		binding.skillGrowth.setText(initCharacter.sklGrowth.toString())
		binding.speedGrowth.setText(initCharacter.spdGrowth.toString())
		binding.luckGrowth.setText(initCharacter.lckGrowth.toString())
		binding.defGrowth.setText(initCharacter.defGrowth.toString())
		binding.conGrowth.setText(initCharacter.conGrowth.toString())
		binding.moveGrowth.setText(initCharacter.movGrowth.toString())
		binding.canPromote.setSelection(if (initCharacter.canPromote) 0 else 1)
		binding.strGain.setText(initCharacter.strGain.toString())
		binding.magGain.setText(initCharacter.magGain.toString())
		binding.skillGain.setText(initCharacter.sklGain.toString())
		binding.speedGain.setText(initCharacter.spdGain.toString())
		binding.defGain.setText(initCharacter.defGain.toString())
		binding.conGain.setText(initCharacter.conGain.toString())
		binding.moveGain.setText(initCharacter.movGain.toString())

		canPromote.value = initCharacter.canPromote
		canPromote.observe(viewLifecycleOwner) {
			binding.strGain.isEnabled = it
			binding.magGain.isEnabled = it
			binding.skillGain.isEnabled = it
			binding.speedGain.isEnabled = it
			binding.defGain.isEnabled = it
			binding.conGain.isEnabled = it
			binding.moveGain.isEnabled = it
		}

		binding.canPromote.adapter = ArrayAdapter(
			requireContext(),
			android.R.layout.simple_spinner_item,
			arrayOf(true, false)
		)
		binding.canPromote.onItemSelectedListener = LevelListener(canPromote)

		characterDao.getCharacters().asLiveData().observe(viewLifecycleOwner) {
			nextId = it.size + 1
		}

		binding.editCharacterOKButton.setOnClickListener {
			val characterName = binding.name.text.toString()
			Toast.makeText(this.context, "Added character $characterName", Toast.LENGTH_SHORT)
				.show()
			lifecycleScope.launch {
				val character = Character(
					nextId,
					characterName,
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
				characterDao.insert(character)
			}

		}

		binding.name.addTextChangedListener(TestListener())
		return binding.root
	}

	class TestListener(): TextWatcher {
		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
		}

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			println(s)
		}

		override fun afterTextChanged(s: Editable?) {
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