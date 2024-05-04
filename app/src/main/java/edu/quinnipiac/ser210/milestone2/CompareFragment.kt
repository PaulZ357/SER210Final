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
import edu.quinnipiac.ser210.milestone2.data.Scroll
import edu.quinnipiac.ser210.milestone2.data.ScrollDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCompareBinding
import kotlinx.coroutines.launch

class CompareFragment : Fragment() {

	lateinit var binding: FragmentCompareBinding
	private val characterIndex = MutableLiveData(0)
	lateinit var characterDao: CharacterDao
	lateinit var scroll: Map<Scroll, Int>
	private lateinit var scrollDao: ScrollDao
	private val scrollIndex = MutableLiveData(0)
	var level: Int
    override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreate(savedInstanceState)
		// Inflate the layout for this fragment
		characterDao =
			(activity?.application as DataApplication).characterDatabase.characterDao()
		binding = FragmentCompareBinding.inflate(layoutInflater)

		characterIndex.observe(viewLifecycleOwner) {index: Int ->
			characterDao.getCharacters().asLiveData()
				.observe(viewLifecycleOwner) {
					if (index < it.size) {
						val character = it[index]
						binding.averageHP.text = "Average HP: "+character.getAverageHP(level, scroll).toString()
						binding.averageStr.text = "Average Str: "+character.getAverageStr(level, scroll).toString()
						binding.averageMag.text = "Average Magic: "+character.getAverageMag(level, scroll).toString()
						binding.averageSpeed.text = "Average Speed: "+character.getAverageSpd(level, scroll).toString()
						binding.averageSkill.text = "Average Skill: "+character.getAverageSkl(level, scroll).toString()
						binding.averageLuck.text = "Average Luck: "+character.getAverageLck(level, scroll).toString()
						binding.averageDef.text = "Average Def: "+character.getAverageDef(level, scroll).toString()
						binding.averageCon.text = "Average Con: "+character.getAverageCon(level, scroll).toString()
						binding.averageMove.text = "Average Move: "+character.getAverageMov(level, scroll).toString()
						/*binding.currentHP.setText(character.baseHP.toString())
						binding.currentStr.setText(character.baseStr.toString())
						binding.currentMag.setText(character.baseMag.toString())
						binding.currentSpeed.setText(character.baseSpd.toString())
						binding.currentSkill.setText(character.baseSkl.toString())
						binding.currentLuck.setText(character.baseLck.toString())
						binding.currentDef.setText(character.baseDef.toString())
						binding.currentCon.setText(character.baseCon.toString())
						binding.currentMove.setText(character.baseMov.toString())*/
					}
					else {
						characterIndex.value = characterIndex.value?.minus(1)
					}
				}
		}
		scrollIndex.observe(viewLifecycleOwner) {index: Int ->
			scrollDao.getScrolls().asLiveData()
				.observe(viewLifecycleOwner) {
					if (index < it.size) {
						// WIL N
						/*binding.currentHP.setText(character.baseHP.toString())
						binding.currentStr.setText(character.baseStr.toString())
						binding.currentMag.setText(character.baseMag.toString())
						binding.currentSpeed.setText(character.baseSpd.toString())
						binding.currentSkill.setText(character.baseSkl.toString())
						binding.currentLuck.setText(character.baseLck.toString())
						binding.currentDef.setText(character.baseDef.toString())
						binding.currentCon.setText(character.baseCon.toString())
						binding.currentMove.setText(character.baseMov.toString())*/
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
		scrollDao.getScrolls().asLiveData()
			.observe(viewLifecycleOwner) { scrolls: List<Scroll> ->
				binding.spinnerScroll.adapter = ArrayAdapter(
					requireContext(),
					android.R.layout.simple_spinner_item,
					Array(scrolls.size) { scrolls[it].name }
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