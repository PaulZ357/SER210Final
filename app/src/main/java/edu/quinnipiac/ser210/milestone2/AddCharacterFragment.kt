package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.databinding.FragmentAddCharacterBinding
import kotlinx.coroutines.launch

class AddCharacterFragment : Fragment() {
	private lateinit var binding: FragmentAddCharacterBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = FragmentAddCharacterBinding.inflate(layoutInflater)
		binding.addCharacterOkButton.setOnClickListener {
			// ok button
			val name = binding.addCharacterText.text
			Toast.makeText(this.context, "Added character", Toast.LENGTH_SHORT).show()
			val characterDao =
				(activity?.application as DataApplication).characterDatabase.characterDao()
			val char = Character(
				id,
				name.toString(),
				binding.addCharacterBaseLevel.text.toString().toInt(),
				binding.addCharacterBaseHP.text.toString().toInt(),
				binding.addCharacterBaseStr.text.toString().toInt(),
				binding.addCharacterBaseMag.text.toString().toInt(),
				binding.addCharacterBaseSkill.text.toString().toInt(),
				binding.addCharacterBaseSpeed.text.toString().toInt(),
				binding.addCharacterBaseLuck.text.toString().toInt(),
				binding.addCharacterBaseDef.text.toString().toInt(),
				binding.addCharacterBaseCon.text.toString().toInt(),
				binding.addCharacterBaseMove.text.toString().toInt(),
				binding.addCharacterHPGrowth.text.toString().toInt(),
				binding.addCharacterStrGrowth.text.toString().toInt(),
				binding.addCharacterMagGrowth.text.toString().toInt(),
				binding.addCharacterSkillGrowth.text.toString().toInt(),
				binding.addCharacterSpeedGrowth.text.toString().toInt(),
				binding.addCharacterLuckGrowth.text.toString().toInt(),
				binding.addCharacterDefGrowth.text.toString().toInt(),
				binding.addCharacterConGrowth.text.toString().toInt(),
				binding.addCharacterMoveGrowth.text.toString().toInt(),
				true
			)
			lifecycleScope.launch {
				characterDao.insert(char)
			}
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_add_character, container, false)
	}
}