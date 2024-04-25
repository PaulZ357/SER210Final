package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Scroll
import edu.quinnipiac.ser210.milestone2.databinding.FragmentAddScrollBinding
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match

class AddScrollFragment : Fragment() {
    private lateinit var binding: FragmentAddScrollBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddScrollBinding.inflate(layoutInflater)
        binding.addScrollOkButton.setOnClickListener {
            // ok button
            val name = binding.addScrollText.text
            Toast.makeText(this.context,"Added scroll ",Toast.LENGTH_SHORT).show()
            val scrollDao =
                (activity?.application as DataApplication).scrollDatabase.scrollDao()
            val scroll = Scroll(id,name.toString(),0,0,0,0,0,0,0,0,0)
            lifecycleScope.launch {
                scrollDao.insert(scroll)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_scroll, container, false)
    }

}