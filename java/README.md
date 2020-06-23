# Java Example

This example presents usage of merchant-api using Java programming language.

## Production Use

The private key used is an example key that is disclosed widely in the documentation and examples for this service. 
You should use your own private key that you generate as part of the on-boarding process.

Please store your private key securely and retrieve it as needed.

## Required Java Version

This example uses Java 8 or newer.

## Building

Execute `./mvnw clean install` to build, run tests, and create jars.

There are no dependencies that need to be installed.

## Running

Payment:
`mvn exec:java -Dexec.mainClass="com.ownsolutions.merchant.main.Payment"`

Get Payment:
`mvn exec:java -Dexec.mainClass="com.ownsolutions.merchant.main.GetPayment"`

Healthcheck:
`mvn exec:java -Dexec.mainClass="com.ownsolutions.merchant.main.Healthcheck"`

## Tests

Payment tests:
`mvn -Dtest="com.ownsolutions.merchant.PaymentTest" test`

Get Payment tests:
`mvn -Dtest="com.ownsolutions.merchant.GetPaymentTest" test`

Get Payment tests:
`mvn -Dtest="com.ownsolutions.merchant.HealthcheckTest" test`

Running all tests:
`mvn test`

