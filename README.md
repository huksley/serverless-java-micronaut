# Micronaut Java Graal AWS Lambda VAT number checker

Performs calls to European Union [SOAP APIs](http://ec.europa.eu/taxation_customs/vies/services/checkVatService?WSDL) to check VAT number validity. Deployed as AWS Lambda using [custom/provided runtime](https://docs.aws.amazon.com/lambda/latest/dg/runtimes-custom.html) with GraalVM [AOT compilation](https://www.graalvm.org/docs/reference-manual/aot-compilation/). Also works locally with [SAM]((https://github.com/awslabs/aws-sam-cli)).

## Building

Local Java build can be done using `./gradlew clean compileJava assemble build`

To generate native image use `./build-native-image.sh` to do it locally or `./rebuild-native-image` to do it in AWS Linux Docker container.


https://docs.micronaut.io/latest/guide/index.html
https://agorapulse.github.io/micronaut-libraries/docs/javadoc/micronaut-aws-sdk/com/agorapulse/micronaut/aws/dynamodb/DynamoDBService.html


## Running locally

Run as [Micronaut](http://micronaut.io) app locally using `java -DMICRONAUT_SERVER_PORT=3000 -jar build/libs/serverless-java-micronaut-0.1-all.jar`

To run using [SAM](https://github.com/awslabs/aws-sam-cli) execute `./sam-local` to run Java version and `./sam-local-graal` to run Graal version.

## Deploying to AWS Lambda

Use single call `./deploy-to-aws` or `./deploy-to-aws-graal` to deploy Java and Graal native image versions. You can deploy at the same time to different API endpoints.

## Links

  * Apache bench tested 50 calls 5 concurrency level 1.5s cold boot time - https://gist.github.com/huksley/5bf190143d3175c672ee97f41941ffbe
  * Summary of changes and hardship to achieve that - https://github.com/micronaut-projects/micronaut-aws/issues/6#issuecomment-463391780
  * Based on Micronaut guide - https://guides.micronaut.io/micronaut-function-aws-lambda/guide/index.html
  * https://github.com/micronaut-guides/micronaut-function-aws-lambda/tree/master/complete/vies-vat-validator
  * https://github.com/micronaut-projects/micronaut-aws/tree/master/examples/api-proxy-example
  * https://github.com/micronaut-projects/micronaut-core/issues/540
  * Added Netty configuration and substitutions from https://github.com/cstancu/netty-native-demo/

## Examples

Example validate vat:

```bash
ab -n 50 -c 5 -T application/json -p example-validate-vat.json -m POST https://deadbeef.execute-api.eu-west-1.amazonaws.com/dev/vat/validate 
```

More examples in [examples.md](./examples.md)
