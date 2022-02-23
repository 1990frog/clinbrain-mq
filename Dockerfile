FROM rabbitmq:3.8-management
MAINTAINER "clinbrain"

RUN chmod 777 /tmp

ENV TZ=Asia/Shanghai \
    DEBIAN_FRONTEND=noninteractive

RUN set -x && apt-get update && apt-get install -y locales \
    && localedef -i en_US -c -f UTF-8 -A /usr/share/locale/locale.alias en_US.UTF-8 \
    && apt-get install -y tzdata \
    && apt-get install -y vim \
    && ln -fs /usr/share/zoneinfo/${TZ} /etc/localtime \
    && echo ${TZ} > /etc/timezone \
    && dpkg-reconfigure --frontend noninteractive tzdata \
    && rm -rf /var/lib/apt/lists/*

ENV LANG en_US.utf8

ADD jdk-8u144-linux-x64.tar.gz /usr/local/java
RUN ln -s /usr/local/java/jdk1.8.0_144 /usr/local/java/jdk 
ENV JAVA_HOME /usr/local/java/jdk 
ENV JRE_HOME ${JAVA_HOME}/jre 
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib 
ENV PATH ${JAVA_HOME}/bin:$PATH

COPY app /app/
ADD entrypoint.sh start.sh
EXPOSE 8801
ENTRYPOINT [ "./start.sh" ]
CMD [""]
