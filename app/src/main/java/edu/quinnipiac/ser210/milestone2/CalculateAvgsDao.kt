package edu.quinnipiac.ser210.milestone2

import androidx.room.*

@Dao
interface CalculateAvgsDao {
    @Query("SELECT * FROM character")
    fun getAll(): List<Character>
}