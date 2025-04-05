function wrap(block, symbol) {
  return block.infix && (['-','/'].includes(symbol) || block.symbol !== symbol)
}

function rpnToInfix(pattern) {
  const stack = []

  pattern.forEach((s, i) => {
    if (typeof s === 'number') {
      stack.push(s)
    } else {
      const second = stack.pop()
      const first = stack.pop()
      const firstString = wrap(first, s)
        ? `(${first.infix})`
        : `${first.infix || first}`
      const secondString = wrap(second, s)
        ? `(${second.infix})`
        : `${second.infix || second}`
      const block = {
        symbol: s,
        infix: `${firstString} ${s} ${secondString}`
      }

      stack.push(block)
    }
  })
  return stack.pop().infix
}

module.exports = rpnToInfix
