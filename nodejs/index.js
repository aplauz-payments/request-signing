const crypto = require("crypto");
const fs = require("fs");
const { promisify } = require("util");
const readFile = promisify(fs.readFile);

// Settings
const privateKeyPath = "./example.private.key.pem";
const body = {
  pin: "8888888888888888",
  currencyCode: "EUR",
  amount: 8,
  channelId: "ecommerce",
  transactionReference: "ABC",
  deviceReference: "539e5805-b2eb-4ac5-9e5e-45ec0524172e",
  integratorReference: "456"
};

(async () => {
  const requestBody = JSON.stringify(body);

  // In a production deployment this needs to be stored securely and retrieved
  // as needed, for example from AWS SecretsManager
  const privateKey = await readFile(privateKeyPath, "utf8");

  const signer = crypto.createSign("RSA-SHA256");
  signer.write(requestBody);
  const base64EncodedSignature = signer.sign(privateKey).toString("base64");

  console.log("---------------");
  console.log("Node JS example");
  console.log("---------------");
  console.log("With the request body being:");
  console.log(requestBody);
  console.log();
  console.log(`And signing with the private key in ${privateKeyPath}`);
  console.log();
  console.log("Set the Shared-Key header to:");
  console.log(base64EncodedSignature);
})();
