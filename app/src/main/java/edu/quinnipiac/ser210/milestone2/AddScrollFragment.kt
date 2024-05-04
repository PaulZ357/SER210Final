package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.data.Scroll
import edu.quinnipiac.ser210.milestone2.data.ScrollDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentAddScrollBinding
import kotlinx.coroutines.launch

class AddScrollFragment : Fragment() {
	private lateinit var binding: FragmentAddScrollBinding
	private lateinit var scrollDao: ScrollDao
	private var nextId = 0
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		// Inflate the layout for this fragment
		scrollDao = (activity?.application as DataApplication).scrollDatabase.scrollDao()
		binding = FragmentAddScrollBinding.inflate(layoutInflater)

		scrollDao.getScrolls().asLiveData().observe(viewLifecycleOwner) {
			nextId = it.size + 1
		}

		binding.addScrollOkButton.setOnClickListener {
			// ok button
			Toast.makeText(this.context, "Added scroll ", Toast.LENGTH_SHORT).show()
			val scroll = Scroll(
				nextId,
				binding.addScrollText.text.toString(),
				binding.addHpGrowthRate.text.toString().toInt(),
				binding.addStrGrowthRate.text.toString().toInt(),
				binding.addMagicGrowthRate.text.toString().toInt(),
				binding.addSkillGrowthRate.text.toString().toInt(),
				binding.addSpeedGrowthRate.text.toString().toInt(),
				binding.addLuckGrowthRate.text.toString().toInt(),
				binding.addDefGrowthRate.text.toString().toInt(),
				binding.addConGrowthRate.text.toString().toInt(),
				binding.addMoveGrowthRate.text.toString().toInt()
			)
			lifecycleScope.launch {
				scrollDao.insert(scroll)
			}
		}
		return binding.root
	}

}