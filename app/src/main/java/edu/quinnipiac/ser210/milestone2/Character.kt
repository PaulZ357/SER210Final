package edu.quinnipiac.ser210.milestone2

data class Character (
    val baseLevel: Int,
    val baseStats: Array<Int>,
    val growthRates: Array<Int>,
    val promotionGains: Array<Int>
)