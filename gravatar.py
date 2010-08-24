import urllib, hashlib
from google.appengine.ext import webapp
from django import template as django_template

register = webapp.template.create_template_register()
# http://snipt.net/w00kie/gravatar-for-appengines-django-templates/
# Adapted and updated from http://www.djangosnippets.org/snippets/772/
# Usage: {% gravatar foo@bar.com %} or {% gravatar foo@bar.com 40 R http://foo.com/bar.jpg %}
def gravatar(email, size=32, rating='g', default_image=''):
    gravatar_url = "http://www.gravatar.com/avatar/"
    gravatar_url += hashlib.md5(email).hexdigest()
    gravatar_url += '?' + urllib.urlencode({'s':str(size),
        'r':rating,
        'd':default_image})
    return """<img src="%s" alt="gravatar" />""" % gravatar_url

register.simple_tag(gravatar)