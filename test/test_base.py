import unittest

class TestBase(unittest.TestCase):
	def setUp(self):
		self.context = None

	def test_base(self):
		self.assertTrue(self.context == None)

	def tearDown(self):
		self.context = None

if __name__=="__main__":
	unittest.main()
