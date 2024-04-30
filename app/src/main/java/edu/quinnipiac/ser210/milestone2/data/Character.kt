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
	private fun getAverage(
		level: Int,
		base: Int,
		growth: Int,
		scrolls: Map<Scroll, Int> = HashMap(),
		getScrollStat: (Scroll) -> Int = { 0 },
		cap: Int = 20
	): Double {
		var scrollSum = 0.0
		for (scroll: Scroll in scrolls.keys) {
			scrollSum += (getScrollStat(scroll).toDouble() / 100) * scrolls[scroll]!!
		}
		return roundPlaces(
			min(
				base + ((growth.toDouble() / 100) * (level - baseLevel)) + scrollSum,
				cap.toDouble()
			), 2
		)
	}

	private fun roundPlaces(value: Double, places: Int): Double {
		return round(value * 10.0.pow(places)) / 10.0.pow(places)
	}

	fun getAverageHP(level: Int, scrolls: Map<Scroll, Int>): Double {
		val HPGet: (Scroll) -> Int = {
			it.HPGrowth
		}
		return getAverage(level, baseHP, HPGrowth, scrolls, HPGet, 80)
	}

	fun getAverageStr(level: Int, promotes: Boolean, scrolls: Map<Scroll, Int>): Double {
		val strGet: (Scroll) -> Int = {
			it.strGrowth
		}
		return getAverage(level, baseStr + if (promotes) strGain else 0, strGrowth, scrolls, strGet)
	}

	fun getAverageMag(level: Int, promotes: Boolean, scrolls: Map<Scroll, Int>): Double {
		val magGet: (Scroll) -> Int = {
			it.magGrowth
		}
		return getAverage(level, baseMag + if (promotes) magGain else 0, magGrowth, scrolls, magGet)
	}

	fun getAverageSkl(level: Int, promotes: Boolean, scrolls: Map<Scroll, Int>): Double {
		val sklGet: (Scroll) -> Int = {
			it.skillGrowth
		}
		return getAverage(level, baseSkl + if (promotes) sklGain else 0, sklGrowth, scrolls, sklGet)
	}

	fun getAverageSpd(level: Int, promotes: Boolean, scrolls: Map<Scroll, Int>): Double {
		val spdGet: (Scroll) -> Int = {
			it.speedGrowth
		}
		return getAverage(level, baseSpd + if (promotes) spdGain else 0, spdGrowth, scrolls, spdGet)
	}

	fun getAverageLck(level: Int, scrolls: Map<Scroll, Int>): Double {
		val lckGet: (Scroll) -> Int = {
			it.luckGrowth
		}
		return getAverage(level, baseLck, lckGrowth, scrolls, lckGet)
	}

	fun getAverageDef(level: Int, promotes: Boolean, scrolls: Map<Scroll, Int>): Double {
		val defGet: (Scroll) -> Int = {
			it.defGrowth
		}
		return getAverage(level, baseDef + if (promotes) defGain else 0, defGrowth, scrolls, defGet)
	}

	fun getAverageCon(level: Int, promotes: Boolean, scrolls: Map<Scroll, Int>): Double {
		val conGet: (Scroll) -> Int = {
			it.conGrowth
		}
		return getAverage(level, baseCon + if (promotes) conGain else 0, conGrowth, scrolls, conGet)
	}

	fun getAverageMov(level: Int, promotes: Boolean, scrolls: Map<Scroll, Int>): Double {
		val movGet: (Scroll) -> Int = {
			it.moveGrowth
		}
		return getAverage(level, baseMov + if (promotes) movGain else 0, movGrowth, scrolls, movGet)
	}
}
