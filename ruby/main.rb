require 'base64'
require 'json'
require 'openssl'

# Settings
private_key_path = "../example.private.key.pem";
body = {
    :codes => ["8888888888888888"],
    :currencyCode => "CHF",
    :amount => 8,
    :channelId => "9b05145f-c50d-4cd6-ac03-86f9f4e9fbbf",
    :merchantUserReference => "john.doe@johndoe.com",
    :merchantTransactionReference => "100000000010",
    :integratorTransactionReference => "7fd113c",
    :pspTransactionReference => "e5785e77e",
    :deviceReference => "539e5805-b2eb-4ac5-9e5e-45ec0524172e",
};

# In a production deployment this needs to be stored securely and retrieved
# as needed, for example from AWS SecretsManager
private_key = OpenSSL::PKey::RSA.new File.read private_key_path

request_body = body.to_json
signature = private_key.sign OpenSSL::Digest::SHA256.new, request_body

puts "------------"
puts "Ruby example"
puts "------------"
puts "With the request body being:"
puts request_body
puts 
puts "And signing with the private key in " + private_key_path
puts 
puts "Set the Request-Signature header to:"
puts Base64.encode64 signature
