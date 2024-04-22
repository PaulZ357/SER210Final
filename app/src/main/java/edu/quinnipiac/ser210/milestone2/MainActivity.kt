package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import edu.quinnipiac.ser210.milestone2.data.Character
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		val application: DataApplication = application as DataApplication
		val characterDao = application.characterDatabase.characterDao()
		val scrollDao = application.scrollDatabase.scrollDao()
		lifecycleScope.launch {
			for (character: Character in application.defaultCharacters) {
				characterDao.insert(character)
				println(character)
			}
			characterDao.getCharacters().collect {char: Character -> println(char)}
		}

	}
}