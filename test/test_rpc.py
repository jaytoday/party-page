import unittest
import models
import datetime
from google.appengine.api import users
import main

class TestRpc(unittest.TestCase):
  """ test rpc json dictionaries"""
  def setUp(self):
    self.dateTime = datetime.datetime.now()
    self.context = main.RPCMethods()
  def test_create_chatuser(self):
    self.assertTrue(self.context.chat("Hello World!"))