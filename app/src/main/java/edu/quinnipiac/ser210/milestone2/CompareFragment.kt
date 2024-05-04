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
	private lateinit var scrollDao: ScrollDao
	private val scrollIndex = MutableLiveData(0)
	private val scrollSums = MutableLiveData<HashMap<Scroll, Int>>(java.util.HashMap())
	var level: Int = 0

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		super.onCreate(savedInstanceState)
		// Inflate the layout for this fragment
		characterDao =
			(activity?.application as DataApplication).characterDatabase.characterDao()
		scrollDao =
			(activity?.application as DataApplication).scrollDatabase.scrollDao()
		val scrolls: Map<Scroll, Int> = mapOf()
		binding = FragmentCompareBinding.inflate(layoutInflater)

		characterIndex.observe(viewLifecycleOwner) { index: Int ->
			characterDao.getCharacters().asLiveData()
				.observe(viewLifecycleOwner) {
					if (index < it.size) {
						val character = it[index]
						val promotes = true
						binding.averageHP.text = "Average HP: "+character.getAverageHP(level, scrolls).toString()
						binding.averageStr.text = "Average Str: "+character.getAverageStr(level, promotes, scrolls).toString()
						binding.averageMag.text = "Average Magic: "+character.getAverageMag(level, promotes, scrolls).toString()
						binding.averageSpeed.text = "Average Speed: "+character.getAverageSpd(level, promotes, scrolls).toString()
						binding.averageSkill.text = "Average Skill: "+character.getAverageSkl(level, promotes, scrolls).toString()
						binding.averageLuck.text = "Average Luck: "+character.getAverageLck(level, scrolls).toString()
						binding.averageDef.text = "Average Def: "+character.getAverageDef(level, promotes, scrolls).toString()
						binding.averageCon.text = "Average Con: "+character.getAverageCon(level, promotes, scrolls).toString()
						binding.averageMove.text = "Average Move: "+character.getAverageMov(level, promotes, scrolls).toString()
					} else {
						characterIndex.value = characterIndex.value?.minus(1)
					}
				}
		}
		scrollIndex.observe(viewLifecycleOwner) { index: Int ->
			scrollDao.getScrolls().asLiveData()
				.observe(viewLifecycleOwner) {
					if (index < it.size) {
					} else {
						scrollIndex.value = scrollIndex.value?.minus(1)
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
			// this code gave me an error
		// scrollSums.value!![spinnerScroll.selectedItem as Scroll] = s.toString().toInt()
		}

		override fun afterTextChanged(s: Editable?) {
		}
	}
}