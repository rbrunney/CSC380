<div class="mainTitle" align="center">
    
# RetroGameExchange-API

</div>
<br>
<div class="mainDescription" align="center">
    A Retro Game Exchange API used to learn about Distributed Systems
</div>
<br>
<br>

---

<div align="center">

## Table of Contents

</div>

---

<br>
<br>

- [Description](#description)
- [What-To-Install](#what-to-install)
- [Start-Project](#start-project)
- [Contributors](#contributors)

<br>
<br>

<div class="header" align="center">

---

## Description
---
</div>
<br>

&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
This basic RetroGameExchangeAPI project was made in order to learn Distributed Systems. Also during tis project we got a glimpse into understanding how to implement HATEOS (Hypermedia as the Engine of Application State) within our REST API. Some of the technologies used in the project include Spring Boot, MySQL, Docker, and RabbitMQ. 

<br>
<div class="header" align="center">

---

## What to Install

---

</div>
<br>

<u>

### Items to Download

</u>

-   Docker
-   Postman
-   Code Source

<br>
<u>

### How to Install
</u>

#### Docker
1.  Go to the following site to download Docker Desktop and follow instructions about installation.

    ``` 
    https://docs.docker.com/desktop/install/windows-install/ 
    ```

2. After completing download for Docker Desktop move on to Postman

<br>

#### Postman
1.  Go to the following site to download Postman and follow instructions about installation.

    ```
    https://www.postman.com/downloads/
    ```

2. After completeing download for Postman move on to Code Source

<br>

#### Code Source
1. If you don't have Git Bash, go download from the link below otherwise move to Step 2.

    ```
    https://git-scm.com/downloads
    ```

2. Open up the git bash terminal.
3. Once the Git Bash Terminal is open run the following command to clone the repository to get source code

    ```
    git clone https://github.com/rbrunney/RetroGameExchange-API
    ```
4. Once download is complete you are ready to test out the project!

<br>
<br>

<div class="header" align="center">

---

## Start Project

---

</div>
<br>
<u>

### How to Start
</u>

1. Once downloads have been complete lets start of straightaway into building our docker containers.
2. Go into the cloned repository folder.
3. Once in the folder navigate into this path

    ```
    ./DockerFiles/Stage Folder
    ```

4. Once in the correct folder location open up your preferred ternimal within that folder path.
5. Before building our containers lets go ahead and make a network enviroment for our docker ecosystem. Use the command below to create a docker enivorment

    ```
    docker network create retroGameNet
    ```
6. To confirm that your network is in your dockerized enivroment, use the following command below.
    ```
    docker network ls
    ```

    If done properly you will get an output as shown below.

7. Once the network has been created lets start building our database with the following command

    ```
    docker run --name RetroGameDB -e MYSQL_ROOT_PASSWORD=abcABC123!! -e MYSQL_DATABASE=RetroExchange --net retroGameNet -p 9000:3306 mysql:latest
    ```
    If done properly you will get an output in docker as shown below

8. After making our database lets go ahead and set up the Message Queue Service next. Use the following command below

    ```
    docker run -d --name rabbitMQ --net retroGameNet -p 9001:5672 -p 9002:15672 rabbitmq:3-management
    ```

    If done properly you can now go to any browser and type the following 
    
    ```
    http://localhost:9002
    ```
    You will get an output like below

    It will ask you to log in and the default username and password are below

    ```
    Username: guest
    Password: guest
    ```
    Once logged in you will be brought to a page as shown below.

9. Once RabbitMQ is up and running it is now time to build and run the RetroGameAPI Image. Use the command below to get started.
    ```
    docker build -f RetroApi.dockerfile -t retrogameapi:1.0 .
    ```
    If done properly underneath the Images tab on Docker Desktop you will get an output as shown below.

    Once the image is made lets build the container for the API. Use the ommand below to get started.
    ```
    docker run --name retrogameapi1 --net retroGameNet -e DB_HOST=RetroGameDB -e DB_PORT=3306 -e DB_USER=root -e DB_PASSWORD=abcABC123!! -p 9003:8080 retrogameapi:1.0
    ```
    If done properly you will get an output as shown below.
10. Now that we have built our container for our API we need to follow similar steps to build our Consumer for RabbitMQ. To begin use the command as shown below.
    ```
    docker build -f RetroConsumer.dockerfile -t retroconsumer:1.0 .
    ```
    If done properly underneath the Images tab on Docker Desktop you will get an output as shown below.

    Net we will need to containerize our program. Use the command as shown below.
    ```
    docker run --name retroconsumer --net retroGameNet retroconsumer:1.0
    ```
    If done properly you will get an output as shown below.

11. Last step to get this project fully running is to get NGINX running to show load balancing. But before we build our image and container lets run another instance of our RetroGameAPI. Use the command as shown below.

    ```
    docker run --name retrogameapi2 --net retroGameNet -e DB_HOST=RetroGameDB -e DB_PORT=3306 -e DB_USER=root -e DB_PASSWORD=abcABC123!! -p 9004:8080 retrogameapi:1.0
    ```

    If done properly you should get an output as shown below.

    With the two instances of our RetroGameExcange-API lets build and containerize our instance of NGINX. Use the command as shown below to build the Image.
    ```
    docker build -f Nginx.dockerfile -t nginx:1.0 .
    ```
    If done properly underneath the Images tab on Docker Desktop you will get an output as shown below.
    
    Lastly we need to build our container for NGINX. Use the command as shown below.
    ```
    docker run --name nginx --net retroGameNet -p 80:80 nginx:1.0
    ```
    If done properly you should get an output as shown below.
12. Now we have everything completed and its ready to interact with. Start up Postman and begin tetsing different end-points! API Documentation and Example Request can be found using the below link.

    (Documentation and Sample Request Coming Soon)

13. Enjoy :)

<br>
<br>

<div class="header" align="center">

---

## Contributors

---

</div>

## Robert Brunney

<u>

### Github

https://github.com/rbrunney
</u>
<u>

### LinkedIn

https://www.linkedin.com/in/robert-brunney/
</u>