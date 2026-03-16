import unittest

from countdown.permutator import permutator

class TestPermutator(unittest.TestCase):

    def test_add(self):
        result = permutator([1, 2], 3)
        self.assertEqual([1, 2, "+"], result)

if __name__ == '__main__':
    unittest.main()
