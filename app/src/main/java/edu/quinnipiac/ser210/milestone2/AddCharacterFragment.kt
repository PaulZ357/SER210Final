package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            Toast.makeText(this.context,"Added character",Toast.LENGTH_SHORT).show()
            val characterDao =
                (activity?.application as DataApplication).characterDatabase.characterDao()
            val char = Character(id, name.toString(),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,true)
            lifecycleScope.launch {
                characterDao.insert(char)
            }
        }
    }
}