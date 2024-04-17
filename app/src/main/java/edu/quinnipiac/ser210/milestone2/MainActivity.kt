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

		val characterDao = (application as DataApplication).characterDatabase.characterDao()
		val scrollDao = (application as DataApplication).scrollDatabase.scrollDao()
		lifecycleScope.launch {
			characterDao.insert(Character(1, "Osian", 1, 27, 6,
				0, 7, 9, 3, 4, 11, 6,
				85, 30, 5, 25, 35, 55,
				25, 25, 2, true, 2,	1,
				3, 3,	3,	0,	0))
			characterDao.getCharacter(1).collect {char: Character -> println(char)}
		}

	}
}