package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCalculateAveragesBinding
import kotlinx.coroutines.launch


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
        val characterDao = (activity?.application as DataApplication).characterDatabase.characterDao()
        lifecycleScope.launch {
            characterDao.getCharacter(binding.characterNameEditText.text.toString()).collect {
                recyclerView.adapter = AveragesAdapter(it, 20)
            }
        }
    }
}