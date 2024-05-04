package edu.quinnipiac.ser210.milestone2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scroll")
data class Scroll(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	@ColumnInfo(name = "name")
	val name: String,
	@ColumnInfo(name = "HPGrowth")
	val HPGrowth: Int = 0,
	@ColumnInfo(name = "strGrowth")
	val strGrowth: Int = 0,
	@ColumnInfo(name = "magGrowth")
	val magGrowth: Int = 0,
	@ColumnInfo(name = "skillGrowth")
	val skillGrowth: Int = 0,
	@ColumnInfo(name = "speedGrowth")
	val speedGrowth: Int = 0,
	@ColumnInfo(name = "luckGrowth")
	val luckGrowth: Int = 0,
	@ColumnInfo(name = "defGrowth")
	val defGrowth: Int = 0,
	@ColumnInfo(name = "conGrowth")
	val conGrowth: Int = 0,
	@ColumnInfo(name = "moveGrowth")
	val moveGrowth: Int = 0
) {
	override fun toString(): String {
		return name
	}
}
