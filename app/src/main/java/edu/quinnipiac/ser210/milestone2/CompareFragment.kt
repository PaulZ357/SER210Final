package edu.quinnipiac.ser210.milestone2

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCompareBinding
import kotlinx.coroutines.launch

class CompareFragment : Fragment() {

	lateinit var binding: FragmentCompareBinding
	private val characterIndex = MutableLiveData(0)
	lateinit var characterDao: CharacterDao
    override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreate(savedInstanceState)
		// Inflate the layout for this fragment
		characterDao =
			(activity?.application as DataApplication).characterDatabase.characterDao()
		binding = FragmentCompareBinding.inflate(layoutInflater)

		/*lifecycleScope.launch {
			// looks for the
			val _CHARACTER = binding.spinner.selectedItemId.toInt()
			val character = characterDao.getCharacter(_CHARACTER)
			character.collect { data ->
				// Update the UI with the new data
				binding.compareCharHP.text = data.baseHP.toString()
				binding.compareCharStr.text = data.baseStr.toString()
				binding.compareCharMag.text = data.baseMag.toString()
				binding.compareCharSkill.text = data.baseSkl.toString()
				binding.compareCharSpeed.text = data.baseSpd.toString()
				binding.compareCharLuck.text = data.baseLck.toString()
				binding.compareCharDef.text = data.baseDef.toString()
				binding.compareCharCon.text = data.baseCon.toString()
				binding.compareCharHP.text = data.HPGrowth.toString()
				binding.compareCharStr.text = data.strGrowth.toString()
			}
		}*/
		characterIndex.observe(viewLifecycleOwner) {index: Int ->
			characterDao.getCharacters().asLiveData()
				.observe(viewLifecycleOwner) {
					if (index < it.size) {
						val character = it[index]
						binding.currentHP.setText(character.baseHP.toString())
						binding.currentStr.setText(character.baseStr.toString())
						binding.currentMag.setText(character.baseMag.toString())
						binding.currentSpeed.setText(character.baseSpd.toString())
						binding.currentSkill.setText(character.baseSkl.toString())
						binding.currentLuck.setText(character.baseLck.toString())
						binding.currentDef.setText(character.baseDef.toString())
						binding.currentCon.setText(character.baseCon.toString())
						binding.currentMove.setText(character.baseMov.toString())
					}
					else {
						characterIndex.value = characterIndex.value?.minus(1)
					}
				}
		}

		characterDao.getCharacters().asLiveData()
			.observe(viewLifecycleOwner) { characters: List<Character> ->
				binding.spinner.adapter = ArrayAdapter(
					requireContext(),
					R.layout.simple_spinner_item,
					Array(characters.size) { characters[it].name }
				)
			}

		binding.spinner.onItemSelectedListener =
			CharacterListener(characterIndex)

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
}