package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Scroll
import edu.quinnipiac.ser210.milestone2.databinding.FragmentAddScrollBinding
import kotlinx.coroutines.launch

class AddScrollFragment : Fragment() {
	private lateinit var binding: FragmentAddScrollBinding
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		inflater.inflate(R.layout.fragment_add_scroll, container, false)
		binding = FragmentAddScrollBinding.inflate(layoutInflater)
		binding.addScrollOkButton.setOnClickListener {
			// ok button
			val name = binding.addScrollText.text.toString()
			Toast.makeText(this.context, "Added scroll ", Toast.LENGTH_SHORT).show()
			val scrollDao =
				(activity?.application as DataApplication).scrollDatabase.scrollDao()
			val scroll = Scroll(id, name.toString(), 0, 0, 0, 0, 0, 0, 0, 0, 0)
			lifecycleScope.launch {
				scrollDao.insert(scroll)
			}
		}
		return binding.root
	}

}