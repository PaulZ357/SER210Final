package edu.quinnipiac.ser210.milestone2

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import edu.quinnipiac.ser210.milestone2.data.Character
import edu.quinnipiac.ser210.milestone2.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
	lateinit var binding: ActivityMainBinding
	lateinit var toggle: ActionBarDrawerToggle
	private lateinit var navController: NavController
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		binding.apply {
			toggle= ActionBarDrawerToggle(this@MainActivity,drawerLayout,R.string.open,R.string.close)
			drawerLayout.addDrawerListener(toggle)
			toggle.syncState()

			supportActionBar?.setDisplayHomeAsUpEnabled(true)
			val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
			val navController = navHostFragment.navController
			navView.setNavigationItemSelectedListener {
				when(it.itemId) {
					R.id.addcharacter -> {
						navController.navigate(R.id.addCharacterFragment)					}
					R.id.addscroll -> {
						navController.navigate(R.id.addScrollFragment)					}
					R.id.editcharacter -> {
						navController.navigate(R.id.editCharacterFragment)					}
					R.id.editscroll -> {
						navController.navigate(R.id.editScrollFragment)					}
					R.id.compare -> {
						navController.navigate(R.id.compareFragment)					}
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
		val characterDao = (application as DataApplication).characterDatabase.characterDao()
		val scrollDao = (application as DataApplication).scrollDatabase.scrollDao()

	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (toggle.onOptionsItemSelected(item)) {
			return true
		}
		return super.onOptionsItemSelected(item)
	}
}