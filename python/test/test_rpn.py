import unittest

from countdown.rpn import rpn

class TestRPN(unittest.TestCase):

    def test_add(self):
        allowed, val = rpn([6, 2, '+'])
        self.assertEqual(True, allowed)
        self.assertEqual(8, val)

    def test_multiply(self):
        allowed, val = rpn([6, 2, '*'])
        self.assertEqual(True, allowed)
        self.assertEqual(12, val)

    def test_subtract_allowed(self):
        allowed, val = rpn([6, 2, '-'])
        self.assertEqual(True, allowed)
        self.assertEqual(4, val)

    def test_subtract_disallowed(self):
        allowed, val = rpn([2, 6, '-'])
        self.assertEqual(False, allowed)

    def test_divide_allowed(self):
        allowed, val = rpn([6, 2, '/'])
        self.assertEqual(True, allowed)
        self.assertEqual(3, val)

    def test_divide_allowed(self):
        allowed, val = rpn([2, 6, '/'])
        self.assertEqual(False, allowed)

    def test_end_pos(self):
        allowed, val = rpn([2, 6, '/'], 2)
        self.assertEqual(True, allowed)
        self.assertEqual(6, val)

if __name__ == '__main__':
    unittest.main()
