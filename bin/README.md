# SpringBoot - Term Project - Assignment 1

This term project is to build a web-app using spring/springboot. 
All 3 objectives for assignment 1 should be complete. Currently the ec2 server hosts an NGinX webserver with a hello world page on 80, and a spring server with a hello world page on 8080. Spring server should auto-start as a service when instance boots.


## Links:
### Link to GitHub Repo:
https://github.com/ualbany-software-engineering/term-project-kplumme1

### Link to NGinX web server listening on ec2:80:
http://ec2-18-218-69-121.us-east-2.compute.amazonaws.com:80/

Dynamic DNS Link: http://kplumme1-ec2.ddns.net:80/ (DNS updater client not working properly...)

### Link to SpringBoot server listening on ec2:8080: 
http://ec2-18-218-69-121.us-east-2.compute.amazonaws.com:8080/

Dynamic DNS Link: http://kplumme1-ec2.ddns.net:8080/



## Notes:
A dynamic DNS redirect from noip.com can access the ec2 instance, but the auto-update client is erroring out.
VSCode running remotely on ec2 instance causes the instance to crash after a few minutes. For now develop on desktop and transmit via git. 

location of webserver default page:
/var/www/html/index.nginx-debian.html

location of project directory:(git root directory)
/home/kplu/SpringBoot/SpringHW/

location of src files: 
//OLD - /home/kplu/SpringBoot/SpringHW/demo/
/home/ubuntu/termproject/

User name for spring boot service:
springserver

Location of service script:
/etc/systemd/system/springserver.service

launch the nginx server with: (this is the 8080 software engineering one?)
mvn sprint-boot:run
