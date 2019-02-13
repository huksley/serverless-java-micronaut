# Micronaut AWS Lambda

based on 
https://guides.micronaut.io/micronaut-function-aws-lambda/guide/index.html
https://github.com/micronaut-guides/micronaut-function-aws-lambda/tree/master/complete/vies-vat-validator
https://github.com/micronaut-projects/micronaut-aws/tree/master/examples/api-proxy-example

https://github.com/micronaut-projects/micronaut-core/issues/540

Added Netty configuration and substitutions from 
https://github.com/cstancu/netty-native-demo/


MICRONAUT_SERVER_PORT=8086

Example validate vat:

```bash

ab -n 50 -c 5 -T application/json -p example-validate-vat.json -m POST https://deadbeef.execute-api.eu-west-1.amazonaws.com/dev/vat/validate 
```