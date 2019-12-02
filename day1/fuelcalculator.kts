#!/usr/bin/env kotlinc -script

import java.io.File
import kotlin.math.floor

fun <T> useInputLines(block: (Sequence<String>) -> T) =
    File("input.txt").bufferedReader().useLines(block)

fun fuelRequirement(moduleWeight: Int) : Int {
    val fuel = floor(moduleWeight.toDouble() / 3).toInt() - 2
    return if (fuel > 0) fuel + fuelRequirement(fuel) else 0
}

val fuelRequirement: Int = useInputLines {
    it.map(String::toInt).map(::fuelRequirement).sum()
}
println("Fuel requirement: ${fuelRequirement}")
