# the programs we need for our image, in the left side(openjdk) is the company name and in the right side is the name of the java app we take from docker.hub, here we take a slim version of java only for run java with no option to edit java here.
#FROM openjdk:17-slim-buster // this is relatively old than - openjdk:17-slim
FROM openjdk:17-slim
# how the image will called in the folder he will save it (it will be call "app")
WORKDIR /app
# after defined the folder the image will be save at, we ask him to COPY the JAR and paste it in "/app"
#COPY ./target/CouponsProject3-0.0.1-SNAPSHOT.jar /app // original..
#for local docker running:
COPY ./target/CouponsProject3-0.0.1-SNAPSHOT.jar /app/app.jar
#For Server running:
#COPY CouponsProject3-0.0.1-SNAPSHOT.jar /app/app.jar
#tell on witch port the connection will run
EXPOSE 8080
# when we will run the app CMD will run this command as one command, java: type of program, -jar: type of file. and name of the jar.
#CMD ["java", "-jar", "CouponsProject3-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "/app/app.jar"]

