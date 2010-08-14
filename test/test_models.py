import unittest
import models
import datetime
from google.appengine.api import users

class TestModels(unittest.TestCase):
	""" test crud on models"""
	def setUp(self):
		self.dateTime = datetime.datetime.now()

	def test_create_chatuser(self):
		cu = models.ChatUser()
		cu.user = users.get_current_user()
		cu.joined = self.dateTime
		cu.picture = "http://www.threadbombing.com/data/media/2/mr-t-gold-chains-sparkling.gif"
		cu.website = "http://twitter.com/80s_mr_t"
		cu.put()
		activeKey = cu.key()
		self.assertNotEqual(cu, None, "Created User is None. Did not put")
	
	def test_get_chatuser(self):
		cu = models.ChatUser.all()
		for c in cu:
			self.assertEqual(c.user, get_currrent_user(), "The user stored is not the current user.")
			self.assertEqual(c.picture, "http://www.threadbombing.com/data/media/2/mr-t-gold-chains-sparkling.gif", "The picture url does not match the default.")
			self.assertEqual(c.website, "http://twitter.com/80s_mr_t", "The user url doesn't match the test case")

	def test_del_chatuser(self):
		cu = models.ChatUser.all()
		for c in cu:
			c.delete()
			self.assertEqual(c, None, "Model not deleted")
