package dev.lunisity.islandTop.api.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

object MathUtil {

    fun round(number: Double, places: Int): Double {
        if (places < 0) return 0.0

        val bd = BigDecimal(number)
        return bd.setScale(places, RoundingMode.HALF_UP).toDouble()
    }

    fun round(number: Long, places: Int): Long {
        if (places < 0) return 0

        val bd = BigDecimal(number)
        return bd.setScale(places, RoundingMode.HALF_UP).toLong()
    }

    private val decimalFormat = DecimalFormat("##.##")

    fun formatNumber(number: Double): String {
        return format(number)
    }

    fun formatNumber(number: Long): String {
        return format(number.toDouble())
    }

    private fun format(number: Double): String {
        return if (Math.abs(number) < 1000.0) {
            decimalFormat.format(number)
        } else {
            val units = arrayOf("K", "M", "B", "T", "Q", "QT", "S", "SP", "O", "N", "D")
            var unitIndex = -1
            var absNumber = Math.abs(number)

            while (absNumber >= 1000.0) {
                absNumber /= 1000.0
                unitIndex++
            }

            val formattedNumber = decimalFormat.format(absNumber) + units[unitIndex]
            if (number < 0.0) "-$formattedNumber" else formattedNumber
        }
    }

}