# Dockerizing Microservice
1. Install Docker
2. Start Docker Deamon\
`sudo systemctl start docker (on systemd)`
3. Add your user to the docker group\
`sudo usermod -aG docker $USER`
4. Run `stage` in the sbt shell (intellij)
5. Dockerize our application\
`docker:publishLocal`
6. Run Docker Application\
`docker run -p 8070:8070 microservice:0.2`
