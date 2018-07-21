# https://github.com/docker-library/openjdk

FROM openjdk:8

RUN apt-get update
RUN apt-get install -y vim unzip

RUN mkdir /usr/src/app
WORKDIR /usr/src/app

# Installing Gradle
RUN \
    mkdir /opt/gradle && \
    curl -L https://services.gradle.org/distributions/gradle-4.9-bin.zip -o gradle.zip && \
    unzip -d /opt/gradle gradle.zip && \
    rm gradle.zip

ENV GRADLE_HOME=/opt/gradle/gradle-4.9
ENV PATH=$PATH:$GRADLE_HOME/bin

CMD ["bash"]