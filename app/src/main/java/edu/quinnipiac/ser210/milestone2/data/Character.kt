package edu.quinnipiac.ser210.milestone2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class Character(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	@ColumnInfo(name = "name")
	val name: String,
	@ColumnInfo(name = "baseLevel")
	val baseLevel: Int,
	@ColumnInfo(name = "baseHP")
	val baseHP: Int,
	@ColumnInfo(name = "baseStr")
	val baseStr: Int,
	@ColumnInfo(name = "baseMag")
	val baseMag: Int,
	@ColumnInfo(name = "baseSkill")
	val baseSkill: Int,
	@ColumnInfo(name = "baseSpeed")
	val baseSpeed: Int,
	@ColumnInfo(name = "baseLuck")
	val baseLuck: Int,
	@ColumnInfo(name = "baseDef")
	val baseDef: Int,
	@ColumnInfo(name = "baseCon")
	val baseCon: Int,
	@ColumnInfo(name = "baseMove")
	val baseMove: Int,
	@ColumnInfo(name = "HPGrowth")
	val HPGrowth: Int,
	@ColumnInfo(name = "strGrowth")
	val strGrowth: Int,
	@ColumnInfo(name = "magGrowth")
	val magGrowth: Int,
	@ColumnInfo(name = "skillGrowth")
	val skillGrowth: Int,
	@ColumnInfo(name = "speedGrowth")
	val speedGrowth: Int,
	@ColumnInfo(name = "luckGrowth")
	val luckGrowth: Int,
	@ColumnInfo(name = "defGrowth")
	val defGrowth: Int,
	@ColumnInfo(name = "conGrowth")
	val conGrowth: Int,
	@ColumnInfo(name = "moveGrowth")
	val moveGrowth: Int,
	@ColumnInfo(name = "canPromote")
	val canPromote: Boolean,
	@ColumnInfo(name = "strGain")
	val strGain: Int = 0,
	@ColumnInfo(name = "magGain")
	val magGain: Int = 0,
	@ColumnInfo(name = "skillGain")
	val skillGain: Int = 0,
	@ColumnInfo(name = "speedGain")
	val speedGain: Int = 0,
	@ColumnInfo(name = "defGain")
	val defGain: Int = 0,
	@ColumnInfo(name = "conGain")
	val conGain: Int = 0,
	@ColumnInfo(name = "moveGain")
	val moveGain: Int = 0
)
