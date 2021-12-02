package com.github.fstaudt.aoc2021

import com.github.fstaudt.aoc2021.day1.Day1

fun main(args: Array<String>) {
    when (val day = args[0].toInt()) {
        1 -> Day1().run()
        else -> error("Day $day not implemented yet")
    }
}
