#!/usr/bin/env kotlinc -script

import java.io.File

fun <T> useInputLines(block: (Sequence<String>) -> T) =
    File("input.txt").bufferedReader().useLines(block)

fun String.hasMatchingDigits(): Boolean =
    """(\d)\1{1}+""".toRegex().findAll(this).count() > 0

fun String.hasSortedCharacters(): Boolean =
    this.toCharArray().asSequence()
        .zipWithNext { a, b -> a <= b }
        .all { it }

fun String.hasNoOddPairs(): Boolean {
    val characterCounts = mutableMapOf<Char, Int>()
    this.toCharArray().asSequence().forEach {
        characterCounts[it] = characterCounts.getOrDefault(it, 0) + 1
    }
    return characterCounts.filterValues {
        it == 2
    }.count() > 0
}

println("Password candidates")
useInputLines { lines ->
    println(lines.map {
        val (lower, upper) = it.split("-").map { it.toInt() }
        (lower..upper)
                .filter { it.toString().hasMatchingDigits() }
                .filter { it.toString().hasNoOddPairs() }
                .filter { it.toString().hasSortedCharacters() }
                .count()
    }.sum())
}
