#!/usr/local/bin/php
<?php

// Settings
$private_key_path = "../example.private.key.pem";
$body = [
    "codes" => ["8888888888888888"],
    "currencyCode" => "CHF",
    "amount" => 8,
    "channelId" => "9b05145f-c50d-4cd6-ac03-86f9f4e9fbbf",
    "merchantUserReference" => "john.doe@johndoe.com",
    "merchantTransactionReference" => "100000000010",
    "integratorTransactionReference" => "7fd113c",
    "pspTransactionReference" => "e5785e77e",
    "deviceReference" => "539e5805-b2eb-4ac5-9e5e-45ec0524172e",
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
print("Set the Request-Signature header to:\n");
print(base64_encode($binary_signature) . "\n");
?>