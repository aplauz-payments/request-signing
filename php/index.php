#!/usr/local/bin/php
<?php

// Settings
$private_key_path = "../example.private.key.pem";
$body = [
    "codes" => ["8888888888888888"],
    "currencyCode" => "EUR",
    "amount" => 8,
    "channelId" => "ecommerce",
    "transactionReference" => "ABC",
    "deviceReference" => "539e5805-b2eb-4ac5-9e5e-45ec0524172e",
    "integratorReference" => "456",
    "consumerPaymentMethod" => "card"
];

// In a production deployment this needs to be stored securely and retrieved
// as needed, for example from AWS SecretsManager
$private_key = file_get_contents($private_key_path);

$request_body = json_encode($body);
$binary_signature = "";
openssl_sign($request_body, $binary_signature, $private_key, "SHA256");

print("-----------\n");
print("PHP example\n");
print("-----------\n");
print("With the request body being:\n");
print($request_body . "\n");
print("\n");
print("And signing with the private key in " . $private_key_path . "\n");
print("\n");
print("Set the Shared-Key header to:\n");
print(base64_encode($binary_signature) . "\n");
?>