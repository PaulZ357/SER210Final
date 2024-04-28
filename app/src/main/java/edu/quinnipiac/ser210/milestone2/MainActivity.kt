package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import edu.quinnipiac.ser210.milestone2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	lateinit var binding: ActivityMainBinding
	lateinit var toggle: ActionBarDrawerToggle
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		binding.apply {
			toggle = ActionBarDrawerToggle(
				this@MainActivity,
				drawerLayout,
				R.string.open,
				R.string.close
			)
			drawerLayout.addDrawerListener(toggle)
			toggle.syncState()

			supportActionBar?.setDisplayHomeAsUpEnabled(true)
			val navHostFragment =
				supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
			val navController = navHostFragment.navController
			navView.setNavigationItemSelectedListener {
				when (it.itemId) {
					R.id.addcharacter -> {
						navController.navigate(R.id.addCharacterFragment)
					}

					R.id.addscroll -> {
						navController.navigate(R.id.addScrollFragment)
					}

					R.id.editcharacter -> {
						navController.navigate(R.id.editCharacterFragment)
					}

					R.id.editscroll -> {
						navController.navigate(R.id.editScrollFragment)
					}

					R.id.compare -> {
						navController.navigate(R.id.compareFragment)
					}

					R.id.calculateAvgs -> {
						navController.navigate(R.id.calculateAveragesFragment)
					}
				}
				// Close the drawer
				drawerLayout.closeDrawer(GravityCompat.START)
				true
			}
		}
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (toggle.onOptionsItemSelected(item)) {
			return true
		}
		return super.onOptionsItemSelected(item)
	}
}