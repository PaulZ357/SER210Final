package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCalculateAveragesBinding
import kotlinx.coroutines.launch
import kotlin.math.max


class CalculateAveragesFragment : Fragment() {
	private lateinit var binding: FragmentCalculateAveragesBinding

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentCalculateAveragesBinding.inflate(layoutInflater)
		update()
		binding.updateButton.setOnClickListener {
			update()
		}
		return binding.root
	}

	private fun update() {
		val recyclerView = binding.recyclerView
		val characterDao =
			(activity?.application as DataApplication).characterDatabase.characterDao()
		val promotionLevel: Int = binding.promotionLevelEditText.text.toString().toInt()
		lifecycleScope.launch {
			characterDao.getCharacter(binding.characterNameEditText.text.toString()).collect {
				if (promotionLevel <= 20 && (promotionLevel >= max(10, it.baseLevel))) {
					recyclerView.adapter =
						AveragesAdapter(it, if (it.canPromote) promotionLevel else 20)
					binding.promotionLevelEditText.isEnabled = it.canPromote
				}
			}
		}
	}
}