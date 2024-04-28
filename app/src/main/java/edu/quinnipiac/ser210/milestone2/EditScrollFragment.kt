package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import edu.quinnipiac.ser210.milestone2.databinding.FragmentEditScrollBinding


class EditScrollFragment : Fragment() {
	private lateinit var binding: FragmentEditScrollBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val scrollname = ""
		binding = FragmentEditScrollBinding.inflate(layoutInflater)
		binding.editScrollButton.setOnClickListener {
			// ok button
			Toast.makeText(this.context, "Edited scroll " + scrollname, 4).show()
		}
		binding.editScrollDeleteButton.setOnClickListener {
			// delete button
			Toast.makeText(this.context, "Deleted scroll " + scrollname, 4).show()
		}
	}
}