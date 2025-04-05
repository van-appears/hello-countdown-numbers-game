const first = jest.mock('../lib/countdown-RPN-evaluator')
const Permutatron = require('../lib/Permutatron')

// recursion tests

test('no evaluation if there is a single number in the stack', () => {
  const evaluator = require('../lib/countdown-RPN-evaluator')
  evaluator.mockImplementation(() => {});

  const permutatron = new Permutatron([1], 3)
  const found = permutatron.find();
  expect(evaluator.mock.calls.length).toEqual(0)
});

test('check all patterns for 2 numbers', () => {
  const evaluator = require('../lib/countdown-RPN-evaluator')
  const patterns = []
  evaluator.mockImplementation(pattern => {
    // because Permutatron reuses the pattern to save on memory allocation, we need to clone the patterns sent to the
    // evaluator here, otherwise we won't know what got sent!
    patterns.push([].concat(pattern))
    return {}
  });

  const permutatron = new Permutatron([1,2], 10)
  const found = permutatron.find();
  expect(patterns).toContainEqual([1, 2, '+'])
  expect(patterns).toContainEqual([1, 2, '*'])
  expect(patterns).toContainEqual([1, 2, '-'])
  expect(patterns).toContainEqual([1, 2, '/'])
  expect(patterns).toContainEqual([2, 1, '+'])
  expect(patterns).toContainEqual([2, 1, '*'])
  expect(patterns).toContainEqual([2, 1, '-'])
  expect(patterns).toContainEqual([2, 1, '/'])
});
