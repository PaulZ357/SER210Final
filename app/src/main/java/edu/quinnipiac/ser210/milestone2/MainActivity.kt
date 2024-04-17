package edu.quinnipiac.ser210.milestone2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
            R.id.addcharacter -> {
                Toast.makeText(this,"Add Character", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.addscroll -> {
                Toast.makeText(this,"Add Scroll", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editcharacter -> {
                Toast.makeText(this,"Edit Character", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editscroll -> {
                Toast.makeText(this,"Edit Scroll",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.calculateAvgs -> {
                Toast.makeText(this,"Calculate Averages",Toast.LENGTH_SHORT).show()
                true
            }
            R.id.compare -> {
                Toast.makeText(this,"Compare Results",Toast.LENGTH_SHORT).show()
                true
            }
            else -> {false}
        }
    }
}