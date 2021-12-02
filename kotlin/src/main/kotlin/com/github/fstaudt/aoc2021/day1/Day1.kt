package com.github.fstaudt.aoc2021.day1

import com.github.fstaudt.aoc2021.shared.Day
import com.github.fstaudt.aoc2021.shared.readInputNumbers

fun main() {
    Day1().run()
}

class Day1 : Day {
    override val input: List<Int> = readInputNumbers(1)

    override fun part1() = input.countIncreasingMeasurements()

    override fun part2() = input.countIncreasingSlidingMeasurements()

    private fun List<Int>.countIncreasingMeasurements(): Int {
        return takeLast(size - 1).mapIndexedNotNull { idx, m -> m.takeIf { m > get(idx) } }.count()
    }

    private fun List<Int>.countIncreasingSlidingMeasurements(): Int {
        return takeLast(size - 2).mapIndexed { idx, m -> m + get(idx) + get(idx + 1) }.countIncreasingMeasurements()
    }
}
