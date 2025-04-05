# Javascript Notes

There is currently no optimsation for duplicates. Given a target of 5 and inputs of [2,2] the process would try \
2(first) 2(second) + \
2(first) 2(second) * \
2(first) 2(second) - \
2(first) 2(second) / \
2(second) 2(first) + \
2(second) 2(first) * \
2(second) 2(first) - \
2(second) 2(first) /

Also n * 1 and n / 1 are unnecessary evaluations

The process returns the first match it finds, not necessarily the simplest. Given a target of 105 and inputs of [100,25,5] it will return [100,25,+,5,-] rather than [100,5,+]

The Permutatron uses the setting of a 'found' field as a means of exiting loops and recursion early; might change that to something more Functional, but that would also mean bubbling results all the way back up through recursion tree.

Permutatron tries to do minimal memory allocations; only creating a new 'remaining' array as it recurses through the tree and overwriting the 'pattern' array. This is to improve performance.
