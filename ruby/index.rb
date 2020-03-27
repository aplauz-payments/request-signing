require 'base64'
require 'json'
require 'openssl'

# Settings
private_key_path = "../example.private.key.pem";
body = {
    :pin => "8888888888888888",
    :currencyCode => "EUR",
    :amount => 8,
    :channelId => "ecommerce",
    :transactionReference => "ABC",
    :deviceReference => "539e5805-b2eb-4ac5-9e5e-45ec0524172e",
    :integratorReference => "456"
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
puts "Set the Shared-Key header to:"
puts Base64.encode64 signature
