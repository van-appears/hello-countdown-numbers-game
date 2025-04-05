const evaluator = require('../lib/countdown-RPN-evaluator')

test('plus adds', () => {
  expect(evaluator([1,2,'+'])).toEqual({allowed:true, result:3})
})

test('minus subtracts', () => {
  expect(evaluator([7,3,'-'])).toEqual({allowed:true, result:4})
})

test('slash divides', () => {
  expect(evaluator([10,2,'/'])).toEqual({allowed:true, result:5})
})

test('star multiplies', () => {
  expect(evaluator([2,3,'*'])).toEqual({allowed:true, result:6})
})

test('evaluator returns last number on the stack', () => {
  expect(evaluator([2,3,7])).toEqual({allowed:true, result:7})
})

test('evaluation pushes onto the stack', () => {
  expect(evaluator([1,3,'+',2,'*'])).toEqual({allowed:true, result:8})
})

test('fractional result from divide is not allowed', () => {
  expect(evaluator([6,4,'/'])).toEqual({allowed:false, result: undefined})
})

test('negative result from subtract is not allowed', () => {
  expect(evaluator([6,4,'/'])).toEqual({allowed:false, result: undefined})
})

test('evaluate subset', () => {
  expect(evaluator([1,3,'+',2,'*'],2)).toEqual({allowed:true, result:4})
})
