package edu.quinnipiac.ser210.milestone2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round

@Entity(tableName = "character")
data class Character(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	@ColumnInfo(name = "name")
	val name: String,
	@ColumnInfo(name = "baseLevel")
	val baseLevel: Int,
	@ColumnInfo(name = "baseHP")
	var baseHP: Int,
	@ColumnInfo(name = "baseStr")
	var baseStr: Int,
	@ColumnInfo(name = "baseMag")
	var baseMag: Int,
	@ColumnInfo(name = "baseSkill")
	var baseSkl: Int,
	@ColumnInfo(name = "baseSpeed")
	var baseSpd: Int,
	@ColumnInfo(name = "baseLuck")
	var baseLck: Int,
	@ColumnInfo(name = "baseDef")
	var baseDef: Int,
	@ColumnInfo(name = "baseCon")
	var baseCon: Int,
	@ColumnInfo(name = "baseMove")
	val baseMov: Int,
	@ColumnInfo(name = "HPGrowth")
	var HPGrowth: Int,
	@ColumnInfo(name = "strGrowth")
	var strGrowth: Int,
	@ColumnInfo(name = "magGrowth")
	var magGrowth: Int,
	@ColumnInfo(name = "skillGrowth")
	var sklGrowth: Int,
	@ColumnInfo(name = "speedGrowth")
	var spdGrowth: Int,
	@ColumnInfo(name = "luckGrowth")
	var lckGrowth: Int,
	@ColumnInfo(name = "defGrowth")
	var defGrowth: Int,
	@ColumnInfo(name = "conGrowth")
	var conGrowth: Int,
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
	private fun getAverage(level: Int, base: Int, growth: Int, cap: Int = 20): Double {
		return roundPlaces(min(base + growth.toDouble() / 100 * (level - baseLevel), cap.toDouble()), 2)
	}

	private fun roundPlaces(value: Double, places: Int): Double {
		return round(value * 10.0.pow(places)) / 10.0.pow(places)
	}

	fun getAverageHP(level: Int): Double {
		return getAverage(level, baseHP, HPGrowth, 80)
	}

	fun getAverageStr(level: Int, promotes: Boolean): Double {
		return getAverage(level, baseStr + if (promotes) strGain else 0, strGrowth)
	}

	fun getAverageMag(level: Int, promotes: Boolean): Double {
		return getAverage(level, baseMag + if (promotes) magGain else 0, magGrowth)
	}

	fun getAverageSkl(level: Int, promotes: Boolean): Double {
		return getAverage(level, baseSkl + if (promotes) sklGain else 0, sklGrowth)
	}

	fun getAverageSpd(level: Int, promotes: Boolean): Double {
		return getAverage(level, baseSpd + if (promotes) spdGain else 0, spdGrowth)
	}

	fun getAverageLck(level: Int): Double {
		return getAverage(level, baseLck, lckGrowth)
	}

	fun getAverageDef(level: Int, promotes: Boolean): Double {
		return getAverage(level, baseDef + if (promotes) defGain else 0, defGrowth)
	}

	fun getAverageCon(level: Int, promotes: Boolean): Double {
		return getAverage(level, baseCon + if (promotes) conGain else 0, conGrowth)
	}

	fun getAverageMov(level: Int, promotes: Boolean): Double {
		return getAverage(level, baseMov + if (promotes) movGain else 0, movGrowth)
	}
}
