const start = new Date().getTime()
const Permutatron = require('./lib/Permutatron')
const rpnToInfix = require('./lib/rpn-to-infix')

const target = parseInt(process.argv[2])
const numbers = process.argv.slice(3).map(x => parseInt(x))
const invalid = !target || numbers.length === 0 || numbers.some(x => !x)
if (invalid) {
  console.log('Invalid input')
  process.exit(1)
}

const permutatron = new Permutatron(numbers, target)
const found = permutatron.find()
if (found) {
  console.log(found)
  const infixString = rpnToInfix(found)
  console.log(infixString)
} else {
  console.log('Nope')
}

const end = new Date().getTime()
console.log(`${(end - start)/1000}s`)
