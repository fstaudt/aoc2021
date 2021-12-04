package com.github.fstaudt.aoc2021.day3

import com.github.fstaudt.aoc2021.day3.Day3.BinaryCounter.Companion.toBinaryCounter
import com.github.fstaudt.aoc2021.shared.Day
import com.github.fstaudt.aoc2021.shared.readInputLines

fun main() {
    Day3().run()
}

class Day3 : Day {
    override val input = readInputLines(3)

    override fun part1() = input.map { it.toBinaryCounter() }.fold(BinaryCounter()) { a, b -> a.plus(b) }.run { gamma() * epsilon() }

    override fun part2() = oxygen() * co2()

    private fun oxygen(): Int {
        var data = input
        var index = 0
        while (data.size > 1 && data[0].length > index) {
            val mostCommonBytes = data.map { it.toBinaryCounter() }.fold(BinaryCounter()) { a, b -> a.plus(b) }.mostCommonBytes()
            data = data.filter { it.substring(index, index + 1) == "${mostCommonBytes[index]}" }
            index++
        }
        return data.single().toInt(2)
    }

    private fun co2(): Int {
        var data = input
        var index = 0
        while (data.size > 1 && data[0].length > index) {
            val lessCommonBytes = data.map { it.toBinaryCounter() }.fold(BinaryCounter()) { a, b -> a.plus(b) }.lessCommonBytes()
            data = data.filter { it.substring(index, index + 1) == "${lessCommonBytes[index]}" }
            index++
        }
        return data.single().toInt(2)
    }

    data class BinaryCounter(val bytes: List<Int>? = null, val count: Int = 0) {
        companion object {
            fun String.toBinaryCounter() = BinaryCounter(toCharArray().map { it.digitToInt() })
        }

        fun plus(other: BinaryCounter): BinaryCounter {
            return BinaryCounter(
                bytes?.zip(other.bytes!!) { a, b -> a + b } ?: other.bytes,
                count + 1)
        }

        fun gamma() = bytes!!.map { it.toMostCommon() }.joinToString("") { "$it" }.toInt(2)
        fun epsilon() = bytes!!.map { it.toLessCommon() }.joinToString("") { "$it" }.toInt(2)

        fun mostCommonBytes() = bytes!!.map { it.toMostCommon() }
        fun lessCommonBytes() = bytes!!.map { it.toLessCommon() }

        private fun Int.toMostCommon() = if (2 * this >= count) 1 else 0
        private fun Int.toLessCommon() = 1 - toMostCommon()
    }
}
