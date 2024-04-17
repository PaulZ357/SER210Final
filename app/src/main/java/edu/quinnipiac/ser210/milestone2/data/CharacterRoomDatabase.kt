package edu.quinnipiac.ser210.milestone2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class CharacterRoomDatabase : RoomDatabase() {

	abstract fun characterDao(): CharacterDao

	companion object {
		@Volatile
		private var INSTANCE: CharacterRoomDatabase? = null

		fun getDatabase(context: Context): CharacterRoomDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext.applicationContext,
					CharacterRoomDatabase::class.java,
					"character_database"
				).fallbackToDestructiveMigration().build()
				INSTANCE = instance
				return instance
			}
		}
	}
}