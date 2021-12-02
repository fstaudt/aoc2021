package com.github.fstaudt.aoc2021.day2

import com.github.fstaudt.aoc2021.day2.Day2.Step.Companion.toCommand
import com.github.fstaudt.aoc2021.shared.Day
import com.github.fstaudt.aoc2021.shared.readInputLines

fun main() {
    Day2().run()
}

class Day2 : Day {
    override val input: List<String> = readInputLines(2)

    override fun part1() = input.map { it.toCommand() }.fold(Position()) { p, c -> p.applyPart1(c) }.run { horizontal * depth }

    override fun part2() = input.map { it.toCommand() }.fold(Position()) { p, c -> p.applyPart2(c) }.run { horizontal * depth }

    data class Position(var horizontal: Int = 0, var depth: Int = 0, var aim: Int = 0) {
        fun applyPart1(step: Step): Position {
            return when (step.action) {
                "forward" -> Position(horizontal + step.units, depth)
                "down" -> Position(horizontal, depth + step.units)
                "up" -> Position(horizontal, depth - step.units)
                else -> error(step.action)
            }
        }

        fun applyPart2(step: Step): Position {
            return when (step.action) {
                "forward" -> Position(horizontal + step.units, depth + (aim * step.units), aim)
                "down" -> Position(horizontal, depth, aim + step.units)
                "up" -> Position(horizontal, depth, aim - step.units)
                else -> error(step.action)
            }
        }
    }

    data class Step(val action: String, val units: Int) {
        companion object {
            fun String.toCommand() = Step(split(" ")[0], split(" ")[1].toInt())
        }
    }
}
