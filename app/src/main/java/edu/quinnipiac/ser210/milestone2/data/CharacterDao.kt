package edu.quinnipiac.ser210.milestone2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insert(character: Character)

	@Update
	suspend fun update(character: Character)

	@Delete
	suspend fun delete(character: Character)

	@Query("SELECT * from character WHERE id =:id")
	fun getCharacter(id: Int): Flow<Character>

	@Query("SELECT * from character")
	fun getCharacters(): Flow<List<Character>>
}