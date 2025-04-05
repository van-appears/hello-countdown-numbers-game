const rpnToInfix = require('../lib/rpn-to-infix')

const tests = [
  {input: [3, 2, '-'], expected: '3 - 2'},
  {input: [3, 2, '+', 1, '+'], expected: '3 + 2 + 1'},
  {input: [3, 2, 1, '+', '+'], expected: '3 + 2 + 1'},
  {input: [3, 2, 1, '*', '*'], expected: '3 * 2 * 1'},
  {input: [3, 2, 1, '+', '/'], expected: '3 / (2 + 1)'},
  {input: [3, 2, '-', 1, '-'], expected: '(3 - 2) - 1'},
  {input: [3, 2, 1, '-', '-'], expected: '3 - (2 - 1)'},
  {input: [3, 2, '/', 1, '/'], expected: '(3 / 2) / 1'},
  {input: [4, 3, '/', 2, '*', 1, '/'], expected: '((4 / 3) * 2) / 1'},
  {input: [3, 2, '+', 5, 1, '+', '*'], expected: '(3 + 2) * (5 + 1)'},
  {input: [3, 2, '+', 5, 1, '+', 6, '+', '*'], expected: '(3 + 2) * (5 + 1 + 6)'},
  {input: [3, 2, '+', 5, 1, '/', 6, '+', '*'], expected: '(3 + 2) * ((5 / 1) + 6)'},
  {input: [3, 2, '+', 5, 1, '/', 6, '/', '*'], expected: '(3 + 2) * ((5 / 1) / 6)'},
  {input: [3, 2, '+', 5, 1, '/', '*', 6, '/'], expected: '((3 + 2) * (5 / 1)) / 6'},
  {input: [3, 2, '+', 5, 1, '/', '*', 6, '-', 1, '+'], expected: '(((3 + 2) * (5 / 1)) - 6) + 1'},
  {input: [3, 2, '+', 5, 1, '/', '*', 6, 1, '/', '+'], expected: '((3 + 2) * (5 / 1)) + (6 / 1)'},
  {input: [3, 2, '+', 5, 1, '+', '+', 6, 1, '+', '+'], expected: '3 + 2 + 5 + 1 + 6 + 1'}
]

tests.forEach(({input, expected}) => {
  test(`[${input.join(',')}] should be '${expected}'`, () => {
    const output = rpnToInfix(input)
    expect(output).toEqual(expected)
  })
})
