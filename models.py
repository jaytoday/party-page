from google.appengine.ext import db

class ChatUser(db.Model):
  user = db.UserProperty()
  joined = db.DateTimeProperty(auto_now_add=True)
  picture = db.StringProperty()
  website = db.StringProperty()
 
   
class ChatSession(db.Model):
  admin = db.UserProperty(required=False)
  url = db.StringProperty()
  hashtag = db.StringProperty(required=False)
  
  @classmethod
  def get(cls, url, author):
      do_save = False
      session = cls.all().filter('url', url).get()
      if not session:
          session = cls()
          session.url = url
          do_save = True
      if author and not session.admin:
          session.admin = author
          do_save = True
      if do_save:
          session.put()
      return session
      
  
  
class ChatEvent(db.Model):
  invites = db.ListProperty(str)
  start_date = db.DateTimeProperty(auto_now_add=True)
  # end_date - X minutes elapsed since last chat.
  # Once an hour check to see if any new chats. 
  # If new chats, schedule another check in an hour.     
  
  
class ChatMessage(db.Model):
  author = db.UserProperty()
  content = db.StringProperty(multiline=True)
  date = db.DateTimeProperty(auto_now_add=True)
  session = db.ReferenceProperty(ChatSession, required=False)