package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import edu.quinnipiac.ser210.milestone2.databinding.FragmentEditCharacterBinding

class EditCharacterFragment : Fragment() {
	private lateinit var binding: FragmentEditCharacterBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val charactername = ""
		binding = FragmentEditCharacterBinding.inflate(layoutInflater)
		binding.editCharacterOKButton.setOnClickListener {
			// ok button
			Toast.makeText(this.context, "Edited character $charactername", 4).show()
		}
		binding.editCharacterDeleteButton.setOnClickListener {
			// delete button
			Toast.makeText(this.context, "Deleted character $charactername", 4).show()
		}
	}

}