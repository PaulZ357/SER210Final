package edu.quinnipiac.ser210.milestone2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ScrollDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insert(scroll: Scroll)

	@Update
	suspend fun update(scroll: Scroll)

	@Delete
	suspend fun delete(scroll: Scroll)

	@Query("SELECT * from scroll WHERE id =:id")
	fun getScroll(id: Int): Flow<Scroll>

	@Query("SELECT * from scroll ORDER BY name ASC")
	fun getScrolls(): Flow<Scroll>
}