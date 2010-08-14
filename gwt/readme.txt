How to create keys to make the Chrome CRX installable app:
# use openssl to generate keypair
     openssl genrsa -out keypair.pem 1024
# change format to pkcs8
     openssl pkcs8 -topk8 -in keypair.pem -inform pem -out key.pem -outform pem -nocrypt
# extract the public key from an RSA private key, try:
     openssl rsa -in key.pem -pubout > cert.pem
