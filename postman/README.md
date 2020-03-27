# Postman Example

This example provides a Postman collection that has a prerequest script to handle request signing.

## Setting Your Key

The key used comes from a variable called `privateKey`, which needs to be set in your environment variables for the environment you're testing against.

Set the contents of the `privateKey` variable to the PEM encoded private key, which starts with

```
-----BEGIN RSA PRIVATE KEY-----
...etc...
```

[Postman environments and variables are documented here.](https://learning.postman.com/docs/postman/variables-and-environments/variables/)
