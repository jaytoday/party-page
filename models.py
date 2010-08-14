from google.appengine.ext import db

class ChatUser(db.Model):
  user = db.UserProperty()
  joined = db.DateTimeProperty(auto_now_add=True)
  picture = db.StringProperty()
  seated = db.StringProperty()
  website = db.StringProperty()
 
   
class ChatEvent(db.Model):
  author = db.UserProperty()
  url = db.StringProperty()
  invites = db.ListProperty(str)
  start_date = db.DateTimeProperty(auto_now_add=True)
  # end_date - X minutes elapsed since last chat.
  # Once an hour check to see if any new chats. 
  # If new chats, schedule another check in an hour. 
  
  
class ChatMessage(db.Model):
  author = db.UserProperty()
  content = db.StringProperty(multiline=True)
  date = db.DateTimeProperty(auto_now_add=True)
  event = db.ReferenceProperty(ChatEvent, required=False)