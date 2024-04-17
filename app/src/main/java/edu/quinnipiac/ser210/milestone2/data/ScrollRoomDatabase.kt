package edu.quinnipiac.ser210.milestone2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Scroll::class], version = 1, exportSchema = false)
abstract class ScrollRoomDatabase : RoomDatabase() {

	abstract fun scrollDao(): ScrollDao

	companion object {
		@Volatile
		private var INSTANCE: ScrollRoomDatabase? = null

		fun getDatabase(context: Context): ScrollRoomDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext.applicationContext,
					ScrollRoomDatabase::class.java,
					"scroll_database"
				).fallbackToDestructiveMigration().build()
				INSTANCE = instance
				return instance
			}
		}
	}
}