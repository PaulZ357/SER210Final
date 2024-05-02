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
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.Scroll
import edu.quinnipiac.ser210.milestone2.data.ScrollDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentEditScrollBinding


class EditScrollFragment : Fragment() {
	private lateinit var binding: FragmentEditScrollBinding
	private lateinit var scrollDao: ScrollDao
	private val scrollIndex = MutableLiveData(0)

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		inflater.inflate(R.layout.fragment_edit_scroll, container, false)
		val scrollname = ""
		scrollDao =
			(activity?.application as DataApplication).scrollDatabase.scrollDao()
		binding = FragmentEditScrollBinding.inflate(layoutInflater)
		scrollIndex.observe(viewLifecycleOwner) {index: Int ->
			if (index < DataApplication.defaultScrolls.size) {
				binding.editScrollDeleteButton.text = "Restore"
			} else {
				binding.editScrollDeleteButton.text = "Delete"
			}
			scrollDao.getScrolls().asLiveData()
				.observe(viewLifecycleOwner) {
					if (index < it.size) {
						val scroll = it[index]
						binding.hpGrowthRate.setText(scroll.HPGrowth.toString())
						binding.strGrowthRate.setText(scroll.strGrowth.toString())
						binding.magicGrowthRate.setText(scroll.magGrowth.toString())
						binding.skillGrowthRate.setText(scroll.skillGrowth.toString())
						binding.speedGrowthRate.setText(scroll.speedGrowth.toString())
						binding.luckGrowthRate.setText(scroll.luckGrowth.toString())
						binding.defGrowthRate.setText(scroll.defGrowth.toString())
						binding.conGrowthRate.setText(scroll.conGrowth.toString())
						binding.moveGrowthRate.setText(scroll.moveGrowth.toString())

						/*
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

						canPromote.value = character.canPromote*/
					}
					else {
						scrollIndex.value = scrollIndex.value?.minus(1)
					}
				}
		}

		scrollDao.getScrolls().asLiveData()
			.observe(viewLifecycleOwner) { scrolls: List<Scroll> ->
				binding.scrollSpinner.adapter = ArrayAdapter(
					requireContext(),
					android.R.layout.simple_spinner_item,
					Array(scrolls.size) { scrolls[it].name }
				)
			}

		binding.scrollSpinner.onItemSelectedListener = ScrollListener(scrollIndex)

		binding.editScrollButton.setOnClickListener {
			// ok button
			Toast.makeText(this.context, "Edited scroll " + scrollname, 4).show()
		}
		binding.editScrollDeleteButton.setOnClickListener {
			// delete button
			Toast.makeText(this.context, "Deleted scroll " + scrollname, 4).show()
		}

		return binding.root
	}

	class ScrollListener(private val scrollIndex: MutableLiveData<Int>) :
		AdapterView.OnItemSelectedListener {
		override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
			scrollIndex.value = parent!!.selectedItemPosition
		}

		override fun onNothingSelected(parent: AdapterView<*>?) {
		}

	}
}