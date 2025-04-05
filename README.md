# hello-countdown-numbers-game

I once saw a Trisha Gee [presentation](https://www.oreilly.com/library/view/java-and-mongodb/9780134070872/) where she suggested having a pet project that you try re-implementing every time you learn a new framework, like a richer form of 'Hello World'.
While it's not a full stack application, I'm going for a solver for the [Countdown Numbers Game](https://en.wikipedia.org/wiki/Countdown_(game_show)#Numbers_Round), since I once suggested a [Kata](https://en.wikipedia.org/wiki/Kata_(programming)) around the same. 

## Notes
The aim is not to clone or port the exact same solution into each language (where would be the fun in that?) but to try different things in each language.

Roughly the main two parts needed in each solution is:
* Create a [Reverse Polish Notation](https://en.wikipedia.org/wiki/Reverse_Polish_notation) evaluator that meets Countdown acceptability rules. In Countdown is it not acceptable to pass through a negative number or a fraction during a calculation. So, given inputs of 2 3 10...
  * a target of 15 could be met by (10 * 3) / 2, but not (3 / 2) * 10
  * a target of 9 could be met by (10 + 2) - 3, but not (2 - 3) + 10
* Given a target number and input numbers, brute force through all the premutations of inputs and arithmetic symbols, + - * /, until the first acceptable sequence is found that when evaluated has a result that matches the target
  * For example, given a target of 940, and inputs of 50 75 100 25 2 1 it could output: 50 75 25 * 1 + * 100 / 2 +
