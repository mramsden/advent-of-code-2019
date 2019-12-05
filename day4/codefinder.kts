#!/usr/bin/env kotlinc -script

import java.io.File

fun <T> useInputLines(block: (Sequence<String>) -> T) =
    File("input.txt").bufferedReader().useLines(block)

fun String.hasMatchingDigits(): Boolean =
    """(\d)\1+""".toRegex().findAll(this).filter { it.value.length >= 2 }.count() == 0

fun String.hasSortedCharacters(): Boolean =
    this.toCharArray().asSequence()
        .zipWithNext { a, b -> a <= b }
        .all { it }

println("Password candidates")
useInputLines { lines ->
    println(lines.map {
        val (lower, upper) = it.split("-").map { it.toInt() }
        (lower..upper)
            .filter { it.toString().hasMatchingDigits() }
            .filter { it.toString().hasSortedCharacters() }
            .count()
    }.sum())
}
