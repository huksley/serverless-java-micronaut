FROM amazonlinux:2017.03.1.20170812 as graalvm

ENV LANG=en_US.UTF-8
ENV GRAAL_VERSION 1.0.0-rc12
ENV GRAAL_FILENAME graalvm-ce-${GRAAL_VERSION}-linux-amd64.tar.gz
ENV GRAALVM_HOME=/usr/lib/graalvm

RUN yum install -y gcc gcc-c++ libc6-dev zlib1g-dev curl bash zlib zlib-devel zip  \
    && rm -rf /var/cache/yum

RUN curl -4 -L https://github.com/oracle/graal/releases/download/vm-${GRAAL_VERSION}/${GRAAL_FILENAME} -o /tmp/${GRAAL_FILENAME}
RUN tar -zxvf /tmp/${GRAAL_FILENAME} -C /tmp \
    && mv /tmp/graalvm-ce-${GRAAL_VERSION} /usr/lib/graalvm

RUN rm -rf /tmp/*
RUN mkdir -p /app && mkdir -p /app/build
ADD ./bootstrap /app/bootstrap
ADD ./build/libs/serverless-java-micronaut-0.1-all.jar /app/server.jar
ADD src/main/resources/*reflect*.json /app/src/main/resources/
COPY ./build-native* /app/
WORKDIR /app
RUN JAR=/app/server.jar /app/build-native-image.sh
RUN cp $GRAALVM_HOME/jre/lib/amd64/libsunec* /app/
RUN chmod 755 /app/bootstrap
RUN chmod 755 /app/server
RUN zip -j graal-compiled.zip bootstrap server libsunec*

ENTRYPOINT ["/bin/cat"]

