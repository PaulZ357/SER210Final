package edu.quinnipiac.ser210.milestone2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.min

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
	val baseSkl: Int,
	@ColumnInfo(name = "baseSpeed")
	val baseSpd: Int,
	@ColumnInfo(name = "baseLuck")
	val baseLck: Int,
	@ColumnInfo(name = "baseDef")
	val baseDef: Int,
	@ColumnInfo(name = "baseCon")
	val baseCon: Int,
	@ColumnInfo(name = "baseMove")
	val baseMov: Int,
	@ColumnInfo(name = "HPGrowth")
	val HPGrowth: Int,
	@ColumnInfo(name = "strGrowth")
	val strGrowth: Int,
	@ColumnInfo(name = "magGrowth")
	val magGrowth: Int,
	@ColumnInfo(name = "skillGrowth")
	val sklGrowth: Int,
	@ColumnInfo(name = "speedGrowth")
	val spdGrowth: Int,
	@ColumnInfo(name = "luckGrowth")
	val lckGrowth: Int,
	@ColumnInfo(name = "defGrowth")
	val defGrowth: Int,
	@ColumnInfo(name = "conGrowth")
	val conGrowth: Int,
	@ColumnInfo(name = "moveGrowth")
	val movGrowth: Int,
	@ColumnInfo(name = "canPromote")
	val canPromote: Boolean,
	@ColumnInfo(name = "strGain")
	val strGain: Int = 0,
	@ColumnInfo(name = "magGain")
	val magGain: Int = 0,
	@ColumnInfo(name = "skillGain")
	val sklGain: Int = 0,
	@ColumnInfo(name = "speedGain")
	val spdGain: Int = 0,
	@ColumnInfo(name = "defGain")
	val defGain: Int = 0,
	@ColumnInfo(name = "conGain")
	val conGain: Int = 0,
	@ColumnInfo(name = "moveGain")
	val movGain: Int = 0
) {
	private fun getAverage(level: Int, base: Int, growth: Int, cap: Int = 20): Float {
		return min(base + growth.toFloat() / 100 * (level - baseLevel), cap.toFloat())
	}

	fun getAverageHP(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseHP, HPGrowth, 80)
	}

	fun getAverageStr(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseStr + if (promotes) strGain else 0, strGrowth)
	}

	fun getAverageMag(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseMag + if (promotes) magGain else 0, magGrowth)
	}

	fun getAverageSkl(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseSkl + if (promotes) sklGain else 0, sklGrowth)
	}

	fun getAverageSpd(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseSpd + if (promotes) spdGain else 0, spdGrowth)
	}

	fun getAverageLck(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseLck, strGrowth)
	}

	fun getAverageDef(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseDef + if (promotes) defGain else 0, defGrowth)
	}

	fun getAverageCon(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseCon + if (promotes) conGain else 0, conGrowth)
	}

	fun getAverageMov(level: Int, promotes: Boolean): Float {
		return getAverage(level, baseMov + if (promotes) movGain else 0, movGrowth)
	}
}
