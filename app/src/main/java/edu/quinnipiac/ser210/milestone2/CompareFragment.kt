package edu.quinnipiac.ser210.milestone2

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.data.Scroll
import edu.quinnipiac.ser210.milestone2.data.ScrollDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCompareBinding

class CompareFragment : Fragment() {

	lateinit var binding: FragmentCompareBinding
	private val characterIndex = MutableLiveData(0)
	lateinit var characterDao: CharacterDao
	lateinit var scroll: Map<Scroll, Int>
	private lateinit var scrollDao: ScrollDao
	private val scrollIndex = MutableLiveData(0)
	private val scrollSums = MutableLiveData<HashMap<Scroll, Int>>(java.util.HashMap())
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

		characterIndex.observe(viewLifecycleOwner) { index: Int ->
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
					} else {
						characterIndex.value = characterIndex.value?.minus(1)
					}
				}
		}
		scrollIndex.observe(viewLifecycleOwner) { index: Int ->
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
					} else {
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
					R.layout.simple_spinner_item,
					Array(scrolls.size) { scrolls[it].name }
				)
			}

		binding.spinner.onItemSelectedListener =
			CharacterListener(characterIndex)

		binding.spinnerScroll.onItemSelectedListener = ScrollListener(binding, scrollSums)

		binding.currentScrollCount.addTextChangedListener(CurrentScrollCountWatcher(binding.spinnerScroll, scrollSums))

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

	class ScrollListener(
		private val binding: FragmentCompareBinding,
		private val scrollSums: LiveData<HashMap<Scroll, Int>>
	) :
		AdapterView.OnItemSelectedListener {
		override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
			binding.currentScrollCount.setText(
				scrollSums.value!![parent!!.selectedItem]?.toString()
					?: ""
			)
		}

		override fun onNothingSelected(parent: AdapterView<*>?) {
		}

	}

	class CurrentScrollCountWatcher(
		private val spinnerScroll: Spinner,
		private val scrollSums: MutableLiveData<HashMap<Scroll, Int>>
	) : TextWatcher {
		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
		}

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			scrollSums.value!![spinnerScroll.selectedItem as Scroll] = s.toString().toInt()
		}

		override fun afterTextChanged(s: Editable?) {
		}
	}
}