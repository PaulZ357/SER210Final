package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.quinnipiac.ser210.milestone2.databinding.FragmentCalculateAveragesBinding


class CalculateAveragesFragment : Fragment() {
    private lateinit var binding: FragmentCalculateAveragesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculateAveragesBinding.inflate(layoutInflater)
        val recyclerView = binding.recyclerView
        val leif = (activity?.application as DataApplication).defaultCharacters[0]
        recyclerView.adapter = AveragesAdapter(leif, 20)
        return binding.root
    }
}