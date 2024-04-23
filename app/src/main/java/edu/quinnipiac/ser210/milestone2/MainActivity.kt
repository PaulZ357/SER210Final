package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import edu.quinnipiac.ser210.milestone2.data.Character
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
	lateinit var toggle: ActionBarDrawerToggle
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		// NAVIGATION

		val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
		toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
		drawerLayout.addDrawerListener(toggle)
		toggle.syncState()

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val navView = findViewById<NavigationView>(R.id.navView)
		// END NAVIGATION

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

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		// this will create the actionbar
		super.onCreateOptionsMenu(menu)
		menuInflater.inflate(R.menu.main_menu,menu)
		return true
	}
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val id = item.itemId
		return when (id) {
			R.id.addscroll -> {
				Toast.makeText(this, "Will take you to add scroll fragment", Toast.LENGTH_SHORT).show()
				true
			}
			R.id.addcharacter -> {
				Toast.makeText(this, "Will take you to add character fragment", Toast.LENGTH_SHORT).show()
				true
			}
			R.id.editcharacter -> {
				Toast.makeText(this, "Will take you to edit character fragment", Toast.LENGTH_SHORT).show()
				true
			}
			R.id.editscroll -> {
				Toast.makeText(this, "Will take you to edit scroll fragment", Toast.LENGTH_SHORT).show()
				true
			}
			R.id.compare -> {
				Toast.makeText(this, "Will take you to compare fragment", Toast.LENGTH_SHORT).show()
				true
			}
			R.id.calculateAvgs -> {
				Toast.makeText(this, "Will take you to calculate averages fragment", Toast.LENGTH_SHORT).show()
				true
			}

			else -> {false}
		}
	}
}