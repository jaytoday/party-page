import os
DEBUG = os.environ['SERVER_SOFTWARE'].startswith('Dev')

if DEBUG:
    SERVER_HOST = 'http://localhost:8080' #  where widget script is being called from
else:
    SERVER_HOST = 'http://party-page.appspot.com' #  where widget script is being called from 