package edu.quinnipiac.ser210.milestone2

import android.app.Application
import edu.quinnipiac.ser210.milestone2.data.CharacterRoomDatabase
import edu.quinnipiac.ser210.milestone2.data.ScrollRoomDatabase

class DataApplication : Application() {
	val characterDatabase: CharacterRoomDatabase by lazy { CharacterRoomDatabase.getDatabase(this) }
	val scrollDatabase: ScrollRoomDatabase by lazy { ScrollRoomDatabase.getDatabase(this) }
}