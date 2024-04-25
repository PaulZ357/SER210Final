package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import edu.quinnipiac.ser210.milestone2.databinding.FragmentEditCharacterBinding

class EditCharacterFragment : Fragment() {
    lateinit var binding: FragmentEditCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val charactername = ""
        binding = FragmentEditCharacterBinding.inflate(layoutInflater)
        binding.editCharacterOKButton.setOnClickListener {
            // ok button
            Toast.makeText(this.context,"Edited character "+charactername,4).show()
        }
        binding.editCharacterDeleteButton.setOnClickListener {
            // delete button
            Toast.makeText(this.context,"Deleted character "+charactername,4).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_character, container, false)
    }

}