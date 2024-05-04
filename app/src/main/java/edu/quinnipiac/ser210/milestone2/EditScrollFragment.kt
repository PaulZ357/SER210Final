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
import edu.quinnipiac.ser210.milestone2.data.Scroll
import edu.quinnipiac.ser210.milestone2.data.ScrollDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentEditScrollBinding
import kotlinx.coroutines.launch


class EditScrollFragment : Fragment() {
	private lateinit var binding: FragmentEditScrollBinding
	private lateinit var scrollDao: ScrollDao
	private val scrollIndex = MutableLiveData(0)

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		inflater.inflate(R.layout.fragment_edit_scroll, container, false)
		val scrollname = ""
		scrollDao =
			(activity?.application as DataApplication).scrollDatabase.scrollDao()
		binding = FragmentEditScrollBinding.inflate(layoutInflater)
		scrollIndex.observe(viewLifecycleOwner) { index: Int ->
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

					} else {
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
			lifecycleScope.launch {
				scrollDao.update(
					Scroll(
						scrollIndex.value!!,
						binding.scrollSpinner.selectedItem as String,
						binding.hpGrowthRate.text.toString().toInt(),
						binding.strGrowthRate.text.toString().toInt(),
						binding.magicGrowthRate.text.toString().toInt(),
						binding.skillGrowthRate.text.toString().toInt(),
						binding.speedGrowthRate.text.toString().toInt(),
						binding.luckGrowthRate.text.toString().toInt(),
						binding.defGrowthRate.text.toString().toInt(),
						binding.conGrowthRate.text.toString().toInt(),
						binding.moveGrowthRate.text.toString().toInt()
					)
				)
			}
		}
		binding.editScrollDeleteButton.setOnClickListener {
			if (scrollIndex.value!! < DataApplication.defaultScrolls.size) {
				lifecycleScope.launch {
					scrollDao.update(DataApplication.defaultScrolls[scrollIndex.value!!])
				}
			} else {
				lifecycleScope.launch {
					var delete = true
					val characters = scrollDao.getScrolls()
					characters.collect { data ->
						// Update the UI with the new data
						if (delete) {
							scrollDao.delete(data[scrollIndex.value!!])
							delete = false
							scrollIndex.value = data.size - 1
						}
					}
				}
			}
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