#!/bin/bash
set -e
JAR=${JAR:="build/libs/serverless-java-micronaut-0.1-all.jar"}
$GRAALVM_HOME/bin/java -cp $JAR io.micronaut.graal.reflect.GraalClassLoadingAnalyzer
MAINCLASS=${MAINCLASS:=io.micronaut.function.aws.runtime.MicronautLambdaRuntime}
BINARY=${OUTPUT_BINARY:=server}
DELAYCLASSES=`cat build-native-delay-classes | tr -t '\\n' ',' | sed -re "s/,$//g"`
REFLECTFILES=`ls -1 src/main/resources/*reflect*.json | tr -t '\n' ',' | sed -re "s/,$//g"`
echo Delayed classes: ${DELAYCLASSES}
echo Reflection configuration files: ${REFLECTFILES}
$GRAALVM_HOME/bin/native-image --no-server \
             --class-path ${JAR} \
             -H:ReflectionConfigurationFiles=build/reflect.json,${REFLECTFILES} \
             -H:EnableURLProtocols=http,https \
             -H:IncludeResources="logback.xml|logback-debug.xml|application.yml|META-INF/services/*.*" \
             -H:Name=${BINARY} \
             -H:Class=${MAINCLASS} \
             -H:+ReportUnsupportedElementsAtRuntime \
             -H:-AllowVMInspection \
             -H:-UseServiceLoaderFeature \
             --allow-incomplete-classpath \
             --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder,javax.net.ssl.SSLContext' \
             --delay-class-initialization-to-runtime=${DELAYCLASSES}
             
