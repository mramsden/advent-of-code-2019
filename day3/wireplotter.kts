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

    sealed class StepsToPointResult {
        data class Success(val steps: Int): StepsToPointResult()
        object PointNotVisited: StepsToPointResult()
    }

    fun stepsToPoint(point: Point): StepsToPointResult {
        var currentStep = 0
        var currentPoint = Point(0, 0)
        if (currentPoint == point) {
            return StepsToPointResult.Success(currentStep)
        }
        for (instruction in instructions) {
            for (instructionStep in 1..instruction.distance) {
                currentPoint = currentPoint.moveIn(instruction.direction)
                currentStep += 1
                if (currentPoint == point) {
                    return StepsToPointResult.Success(currentStep)
                }
            }
        }
        return StepsToPointResult.PointNotVisited
    }
}

useInputLines { lines ->
    val (wireA, wireB) = lines.map(::Wire).toList()
    val intersections = wireA.visitedPoints.intersect(wireB.visitedPoints)
    println(intersections.map { it.distanceFrom() }.sorted().first())
    println(intersections.map {
        val a = wireA.stepsToPoint(it)
        val b = wireB.stepsToPoint(it)
        when {
            a is Wire.StepsToPointResult.Success && b is Wire.StepsToPointResult.Success ->
                a.steps + b.steps
            else -> error("Point not visited by both wires")
        }
    }.sorted().first())
}
