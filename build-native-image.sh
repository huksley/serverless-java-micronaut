#!/bin/bash
set -e
JAR=${JAR:="build/libs/serverless-java-micronaut-0.1-all.jar"}
#./gradlew clean compileJava assemble build
$GRAALVM_HOME/bin/java -cp $JAR io.micronaut.graal.reflect.GraalClassLoadingAnalyzer
MAINCLASS=io.micronaut.function.aws.runtime.MicronautLambdaRuntime
BINARY=server
$GRAALVM_HOME/bin/native-image --no-server \
             --class-path $JAR \
             -H:ReflectionConfigurationFiles=src/main/resources/micronaut-aws-reflect.json,build/reflect.json \
             -H:EnableURLProtocols=http \
             -H:IncludeResources="logback.xml|application.yml|META-INF/services/*.*" \
             -H:Name=$BINARY \
             -H:Class=$MAINCLASS \
             -H:+ReportUnsupportedElementsAtRuntime \
             -H:-AllowVMInspection \
             -H:-UseServiceLoaderFeature \
             --allow-incomplete-classpath \
             --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder,javax.net.ssl.SSLContext' \
             --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder,io.netty.handler.ssl.util.ThreadLocalInsecureRandom,com.sun.jndi.dns.DnsClient,io.micronaut.function.aws.proxy.MicronautLambdaContainerHandler,com.amazonaws.serverless.proxy.internal.LambdaContainerHandler,io.netty.handler.ssl.JdkNpnApplicationProtocolNegotiator,io.netty.handler.ssl.ReferenceCountedOpenSslEngine
