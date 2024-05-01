package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.CharacterDao
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCompareBinding
import kotlinx.coroutines.launch

class CompareFragment : Fragment() {

	lateinit var binding: FragmentCompareBinding
	lateinit var characterDao: CharacterDao
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		characterDao =
			(activity?.application as DataApplication).characterDatabase.characterDao()
		binding = FragmentCompareBinding.inflate(layoutInflater)

		lifecycleScope.launch {
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
		}
	}
    override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_compare, container, false)
	}
}