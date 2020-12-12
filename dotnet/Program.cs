using System;
using System.IO;
using System.Security.Cryptography;
using System.Text;
using System.Text.Json;

namespace request_signer
{
    class Program
    {
        static void Main(string[] args)
        {
            // Settings
            const string privateKeyPath = "../example.private.key.pem";
            var body = new
            {
                codes = new[] { "8888888888888888" },
                currencyCode = "CHF",
                amount = 8,
                channelId = "9b05145f-c50d-4cd6-ac03-86f9f4e9fbbf",
                merchantUserReference = "john.doe@johndoe.com",
                merchantTransactionReference = "100000000010",
                integratorTransactionReference = "7fd113c",
                pspTransactionReference = "e5785e77e",
                deviceReference = "539e5805-b2eb-4ac5-9e5e-45ec0524172e"
            };

            string requestBody = JsonSerializer.Serialize(body);

            // In a production deployment this needs to be stored securely and retrieved
            // as needed, for example from AWS SecretsManager
            //
            // .NET doesn't have a built in understanding of .PEM files, but we can parse it
            // ourselves without too much trouble.
            var content = File
                .ReadAllText(privateKeyPath)
                .Replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .Replace("-----END RSA PRIVATE KEY-----", "")
                .Replace("\n", "");
            var rsa = RSA.Create();
            rsa.ImportRSAPrivateKey(Convert.FromBase64String(content), out int bytesRead);

            var signedData = rsa.SignData(
                Encoding.UTF8.GetBytes(requestBody),
                HashAlgorithmName.SHA256,
                RSASignaturePadding.Pkcs1
            );
            var base64EncodedSignature = Convert.ToBase64String(signedData);

            System.Console.WriteLine("----------");
            System.Console.WriteLine("C# example");
            System.Console.WriteLine("----------");
            System.Console.WriteLine("With the request body being:");
            System.Console.WriteLine(requestBody);
            System.Console.WriteLine();
            System.Console.WriteLine("And signing with the private key in " + privateKeyPath);
            System.Console.WriteLine();
            System.Console.WriteLine("Set the Request-Signature header to:");
            System.Console.WriteLine(base64EncodedSignature);
        }
    }
}
