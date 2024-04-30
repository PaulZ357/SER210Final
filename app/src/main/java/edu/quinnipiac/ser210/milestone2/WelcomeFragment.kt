package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.data.Scroll
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_welcome, container, false)
		val application: DataApplication = activity?.application as DataApplication
		val characterDao = application.characterDatabase.characterDao()
		val scrollDao = application.scrollDatabase.scrollDao()

		characterDao.getCharacters().asLiveData().observe(viewLifecycleOwner) {
			if (it.size < DataApplication.defaultCharacters.size) {
				lifecycleScope.launch {
					Log.d("Database", "Initializing Character Database")
					for (character: Character in DataApplication.defaultCharacters) {
						characterDao.insert(character)
					}
				}
			}
		}

		scrollDao.getScrolls().asLiveData().observe(viewLifecycleOwner) {
			if (it.size < DataApplication.defaultScrolls.size) {
				lifecycleScope.launch {
					Log.d("Database", "Initializing Scroll Database")
					for (scroll: Scroll in DataApplication.defaultScrolls) {
						scrollDao.insert(scroll)
					}
				}
			}
		}
		return view
	}
}