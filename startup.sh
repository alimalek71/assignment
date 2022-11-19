#!/usr/bin/env sh

echo "The application will start..." && \
      java ${JAVA_OPTS} \
          -Djava.io.tmpdir=/var/tmp \
          -Djava.security.egd=file:/dev/./urandom \
          -jar /app.jar
