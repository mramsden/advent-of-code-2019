#!/usr/bin/env kotlinc -script

import java.io.File

fun <T> useInputLines(block: (Sequence<String>) -> T) =
        File("input.txt").bufferedReader().useLines(block)

sealed class Direction {
    object Up: Direction()
    object Down: Direction()
    object Left: Direction()
    object Right: Direction()
}

data class Point(val x: Int, val y: Int) {

    fun moveIn(direction: Direction) = when (direction) {
        Direction.Up -> Point(x, y + 1)
        Direction.Down -> Point(x, y - 1)
        Direction.Left -> Point(x - 1, y)
        Direction.Right -> Point(x + 1, y)
    }

    fun distanceFrom(point: Point = Point(0, 0)) =
            (Math.abs(x) + Math.abs(y)) - (Math.abs(point.x) + Math.abs(point.y))
}

class Instruction(instruction: String) {
    val direction: Direction
    val distance: Int

    init {
        direction = when (instruction.take(1).first()) {
            'U' -> Direction.Up
            'D' -> Direction.Down
            'L' -> Direction.Left
            'R' -> Direction.Right
            else -> error("Unknown direction")
        }
        distance = instruction.substring(1).toInt()
    }
}

class Wire(routing: String) {

    val instructions: List<Instruction>

    init {
        instructions = routing.split(",").map(::Instruction)
    }

    val visitedPoints: Set<Point>
        get() = mutableSetOf<Point>().also { visitedPoints ->
            var currentPoint = Point(0, 0)
            instructions.forEach { instruction ->
                (1..instruction.distance).forEach {
                    currentPoint = currentPoint.moveIn(instruction.direction)
                    visitedPoints.add(currentPoint)
                }
            }
        }
}

useInputLines { lines ->
    val (wireA, wireB) = lines.map(::Wire).toList()
    val intersections = wireA.visitedPoints.intersect(wireB.visitedPoints)
    print(intersections.map { it.distanceFrom() }.sorted().first())
}
