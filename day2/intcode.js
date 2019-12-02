const resolveIntcode = (program) => {
    let result = [...program];
    let programPtr = 0;
    while (program[programPtr] !== 99) {
        let [opcode, leftPtr, rightPtr, resultPtr] = result.slice(programPtr, programPtr + 4);
        switch (opcode) {
            case 1:
                result[resultPtr] = result[leftPtr] + result[rightPtr];
                break;
            case 2:
                result[resultPtr] = result[leftPtr] * result[rightPtr];
                break;
        }
        programPtr += 4;
    }
    return result[0];
};

const input = process.argv.slice(2);
if (input.length > 0) {
    const [program, target] = input;
    const memory = program.split(',').map(value => parseInt(value));
    if (target) {
        for (let noun = 0; noun <= 99; noun++) {
            for (let verb = 0; verb <= 99; verb++) {
                memory[1] = noun;
                memory[2] = verb;
                if (resolveIntcode(memory) === parseInt(target)) {
                    console.log(100 * noun + verb);
                    process.exit(0);
                }
            }
        }
    } else {
        console.log(resolveIntcode(memory));
    }
} else {
    process.stdin.resume();
    process.stdin.setEncoding('utf8');

    let data = '';
    process.stdin.on('data', function (chunk) {
        data += chunk;
    });
    process.stdin.on('end', function () {
        const program = data.split(',').map(value => parseInt(value));

        console.log(resolveIntcode(program));
    });
}
