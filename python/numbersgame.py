import sys
from countdown.permutator import permutator
from countdown.rpn_to_infix import rpn_to_infix

numberargs = None
try:
    numberargs = list(map(int, sys.argv[1:]))
except:
    print("Could not parse args as numbers")

if numberargs != None:
    result = permutator(numberargs[:-1], numberargs[-1])
    if result != None:
        print(rpn_to_infix(result))
