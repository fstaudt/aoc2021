package com.github.fstaudt.aoc2021.day4

import com.github.fstaudt.aoc2021.day4.Day4.BingoBoard.Companion.toBingoBoard
import com.github.fstaudt.aoc2021.shared.Day
import com.github.fstaudt.aoc2021.shared.readInputLines

fun main() {
    Day4().run()
}

class Day4 : Day {
    override val input = readInputLines(4)

    private val numbers = input[0].split(",").map { it.toInt() }

    private val bingoBoards = input.drop(1).filter { it.isNotBlank() }.chunked(5).map { it.toBingoBoard() }

    override fun part1(): Int {
        var index = 0
        while (bingoBoards.none { it.isWinner() } && index < numbers.size) {
            bingoBoards.forEach { it.playNumber(numbers[index]) }
            index++
        }
        return numbers[index - 1] * bingoBoards.first { it.isWinner() }.score()
    }

    override fun part2(): Int {
        var remainingBingoBoards = bingoBoards
        var index = 0
        while (remainingBingoBoards.size > 1 && index < numbers.size) {
            remainingBingoBoards.forEach { it.playNumber(numbers[index]) }
            remainingBingoBoards = bingoBoards.filter { !it.isWinner() }
            index++
        }
        val lastBingoBoard = remainingBingoBoards[0]
        while (!lastBingoBoard.isWinner() && index < numbers.size) {
            lastBingoBoard.playNumber(numbers[index])
            index++
        }
        return numbers[index - 1] * lastBingoBoard.score()
    }

    data class BingoBoardNumber(val number: Int, var checked: Boolean = false)

    data class BingoBoard(val lines: List<List<BingoBoardNumber>>) {
        private val columns: List<List<BingoBoardNumber>> = lines[0].mapIndexed { i, _ -> lines.map { it[i] } }

        companion object {
            fun List<String>.toBingoBoard() = BingoBoard(map { it.toBingoBoardLine() })
            private fun String.clean() = trimStart().replace(Regex(" +"), " ")
            private fun String.toBingoBoardLine() = clean().split(" ").map { BingoBoardNumber(it.toInt()) }
        }

        fun isWinner() = lines.any { line -> line.all { it.checked } } || columns.any { column -> column.all { it.checked } }

        fun playNumber(number: Int) = lines.forEach { line -> line.forEach { if (number == it.number) it.checked = true } }

        fun score() = lines.sumOf { line -> line.filter { !it.checked }.sumOf { it.number } }
    }
}
