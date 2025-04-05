const evaluator = require('./countdown-RPN-evaluator')
const OPERANDS = ['+', '*', '-', '/']

class Permutatron {
  constructor(numbers, target) {
    this.numbers = numbers
    this.target = target
  }

  evaluate(pattern, writePos) {
    const {allowed, result} = evaluator(pattern, writePos)
    if (result === this.target) {
      this.found = pattern
    }
    return allowed
  }

  removeEntryFromArray(array, skipIndex) {
    const subset = new Array(array.length - 1)
    for (let i=0; i<array.length; i++) {
      if (i === skipIndex) continue;
      subset[i > skipIndex ? i - 1 : i] = array[i]
    }
    return subset
  }

  iterate(pattern, remaining, writePos, unresolvedNumbers) {
    if (unresolvedNumbers > 1) {
      for (let i=0; (i<OPERANDS.length && !this.found); i++) {
        pattern[writePos] = OPERANDS[i]
        const allowed = this.evaluate(pattern, writePos)
        if (!this.found && allowed) {
          this.iterate(pattern, remaining, writePos + 1, unresolvedNumbers - 1)
        }
      }
    }

    for (let i=0; i<remaining.length && !this.found; i++) {
      const remainingSubset = this.removeEntryFromArray(remaining, i)
      pattern[writePos] = remaining[i]
      this.iterate(pattern, remainingSubset, writePos + 1, unresolvedNumbers + 1)
    }
  }

  find() {
    const pattern = new Array(2 * this.numbers.length - 1)
    this.iterate(pattern, this.numbers, 0, 0)
    return this.found
  }
}

module.exports = Permutatron
