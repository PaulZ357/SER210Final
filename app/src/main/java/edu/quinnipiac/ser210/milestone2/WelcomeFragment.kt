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
        val charactersData = characterDao.getCharacters().asLiveData()

        charactersData.observe(viewLifecycleOwner) {
            if (it.size < application.defaultCharacters.size) {
                lifecycleScope.launch {
                    Log.d("Database", "Initializing Database")
                    for (character: Character in application.defaultCharacters) {
                        characterDao.insert(character)
                    }
                }
            }
        }
        return view
    }
}