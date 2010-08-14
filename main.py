import cgi
import os
import urllib
import logging
import pickle

from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app
from google.appengine.ext import db
from google.appengine.ext.webapp import template
from google.appengine.api import memcache

import models

# Set the debug level
_DEBUG = True

class BaseRequestHandler(webapp.RequestHandler):
  """Base request handler extends webapp.Request handler

     It defines the generate method, which renders a Django template
     in response to a web request
  """

  def generate(self, template_name, template_values={}):
    """Generate takes renders and HTML template along with values
       passed to that template

       Args:
         template_name: A string that represents the name of the HTML template
         template_values: A dictionary that associates objects with a string
           assigned to that object to call in the HTML template.  The defualt
           is an empty dictionary.
    """
    # We check if there is a current user and generate a login or logout URL
    user = users.get_current_user()

    if user:
      log_in_out_url = users.create_logout_url('/')
    else:
      log_in_out_url = users.create_login_url(self.request.path)

    # We'll display the user name if available and the URL on all pages
    values = {'user': user, 'log_in_out_url': log_in_out_url}
    values.update(template_values)

    # Construct the path to the template
    directory = os.path.dirname(__file__)
    path = os.path.join(directory, 'templates', template_name)

    # Respond to the request by rendering the template
    return template.render(path, values, debug=_DEBUG)
    
class MainRequestHandler(BaseRequestHandler):

  def get(self):
    if users.get_current_user():
      url = users.create_logout_url(self.request.uri)
      url_linktext = 'Logout'
    else:
      url = users.create_login_url(self.request.uri)
      url_linktext = 'Login'

    template_values = {
      'url': url,
      'url_linktext': url_linktext,
      }

    self.response.out.write(self.generate('index.html', template_values))
    
class EmbedRequestHandler(MainRequestHandler):

  def get(self):
    import config
    template_values = {
        "SERVER_HOST": config.SERVER_HOST
    }
    self.response.out.write(self.generate('embed.html', template_values))
    

class WidgetJSHandler(MainRequestHandler):

  def get(self):
    import config
    template_values = {
        "SERVER_HOST": config.SERVER_HOST
    }
    if not users.get_current_user():
      template_values['login_url'] = users.create_logout_url(self.request.uri).split('continue=')[0]
    self.response.headers['Content-Type'] = "application/javascript"
    self.response.out.write(self.generate('party_page_widget.js', template_values))
    
        

class ChatsRequestHandler(BaseRequestHandler):
  MEMCACHE_KEY = 'chats'
  MEMCACHE_TEMPLATE = 'chats_template'
  
  def get(self):
    template = memcache.get(self.MEMCACHE_TEMPLATE)
    self.response.out.write(template)
    
  def post(self):
    chat = models.ChatMessage()

    if users.get_current_user():
      chat.author = users.get_current_user()
    
    chat.content = self.request.get('content')
    chat.put()
    
    chatsString = memcache.get(self.MEMCACHE_KEY)
    if chatsString is None:
      chatsList = []
    else:
      chatsList = pickle.loads(chatsString)
      if len(chatsList) >= 40:
        chatsList.pop(0)
    chatsList.append(chat)
    
    if not memcache.set(self.MEMCACHE_KEY, pickle.dumps(chatsList)):
        logging.debug("Memcache set failed:")  

    template_values = {
      'chats': chatsList,
    }
    
    template = self.generate('chats.html', template_values)
    logging.info(template)
    if not memcache.set(self.MEMCACHE_TEMPLATE, template):
        logging.debug("Memcache set failed:")          
    
    self.response.out.write(template)

    
class EditUserProfileHandler(BaseRequestHandler):
  """This allows a user to edit his or her wiki profile.  The user can upload
     a picture and set a feed URL for personal data
  """
  def get(self, user):
    # Get the user information
    unescaped_user = urllib.unquote(user)
    user_object = users.User(unescaped_user)
    # Only that user can edit his or her profile
    if users.get_current_user() != user_object:
      self.redirect('/view/StartPage')

    user = models.ChatUser.gql('WHERE user = :1', user_object).get()
    if not user:
      user = models.ChatUser(user=user_object)
      user.put()

    self.response.out.write(self.generate('edit_user.html', template_values={'queried_user': user}))

  def post(self, user):
    # Get the user information
    unescaped_user = urllib.unquote(user)
    user_object = users.User(unescaped_user)
    # Only that user can edit his or her profile
    if users.get_current_user() != user_object:
      self.redirect('/')

    user = models.ChatUser.gql('WHERE user = :1', user_object).get()

    user.picture = self.request.get('user_picture')
    user.website = self.request.get('user_website')
    user.put()


    self.redirect('/user/%s' % user)
    
class UserProfileHandler(BaseRequestHandler):
  """Allows a user to view another user's profile.  All users are able to
     view this information by requesting http://wikiapp.appspot.com/user/*
  """

  def get(self, user):
    """When requesting the URL, we find out that user's WikiUser information.
       We also retrieve articles written by the user
    """
    # Webob over quotes the request URI, so we have to unquote twice
    unescaped_user = urllib.unquote(urllib.unquote(user))

    # Query for the user information
    user_object = users.User(unescaped_user)
    user = models.ChatUser.gql('WHERE user = :1', user_object).get()

    # Generate the user profile
    self.response.out.write(self.generate('user.html', template_values={'queried_user': user}))

                                                
application = webapp.WSGIApplication(
                                     [('/', MainRequestHandler),
                                      ('/iframe', MainRequestHandler),
                                      ('/embed', EmbedRequestHandler),
                                      ('/party-page-js', WidgetJSHandler),
                                      ('/getchats', ChatsRequestHandler),
                                      ('/user/([^/]+)', UserProfileHandler),
                                      ('/edituser/([^/]+)', EditUserProfileHandler)],
                                     debug=True)

def main():
  run_wsgi_app(application)

if __name__ == "__main__":
  main()