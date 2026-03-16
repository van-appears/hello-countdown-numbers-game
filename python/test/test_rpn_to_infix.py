import unittest

from countdown.rpn_to_infix import rpn_to_infix

class TestRPNToInfix(unittest.TestCase):

    def test_number(self):
        result = rpn_to_infix([6])
        self.assertEqual('6', result)

    def test_number_pop(self):
        result = rpn_to_infix([6, 3])
        self.assertEqual('3', result)

    def test_non_number_pop(self):
        result = rpn_to_infix([6, 2, '/'])
        self.assertEqual('(6 / 2)', result)

    def test_non_number_stack(self):
        result = rpn_to_infix([6, 2, '/', 4, '*'])
        self.assertEqual('((6 / 2) * 4)', result)

if __name__ == '__main__':
    unittest.main()
