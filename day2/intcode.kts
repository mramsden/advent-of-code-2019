#!/usr/bin/env kotlinc -script

import java.io.File

fun <T> useInputLines(block: (Sequence<String>) -> T) =
    File("input.txt").bufferedReader().useLines(block)

fun executeProgram(program: Array<Int>, noun: Int? = null, verb: Int? = null) : Int {
    var memory = program.copyOf()
    noun?.let { memory[1] = noun }
    verb?.let { memory[2] = verb }
    for (programPtr in 0 until program.size step 4) {
        if (memory[programPtr] == 99) return memory.first()
        val (opcode, leftPtr, rightPtr, resultPtr) = memory.slice((programPtr until programPtr + 4))
        when (opcode) {
            1 -> memory[resultPtr] = memory[leftPtr] + memory[rightPtr]
            2 -> memory[resultPtr] = memory[leftPtr] * memory[rightPtr]
        }
    }
    return memory.first()
}

fun discoverInputs(program: Array<Int>, targetValue: Int) : Array<Int> {
    (0..99).forEach { noun ->
        (0..99).forEach { verb ->
            if (executeProgram(program, noun, verb) == targetValue) {
                return arrayOf(noun, verb)
            }
        }
    }
    return arrayOf()
}

useInputLines { lines ->
    lines.forEach {
        val program = it.split(',').map(String::toInt).toTypedArray()
        when (args.size) {
            1 -> {
                val (noun, verb) = discoverInputs(program, args[0].toInt())
                println(100 * noun + verb)
            }
            2 -> {
                println(executeProgram(program, args[0].toInt(), args[1].toInt()))
            }
        }
    }
}
