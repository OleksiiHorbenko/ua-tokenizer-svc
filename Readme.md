## To run Application locally via Docker
- [x] Make sure that [Docker Engine](https://docs.docker.com/engine/install/) is installed
- [x] Make sure that port 8080 is not occupied by other applications or services
- [x] In your terminal:
  - go to that project root directory ```cd {that repository location}```
  - Navigate to docker directory (execute  ```cd docker``` )
  - Execute docker image creation and application deploy (execute  ```docker-compose -f ./ua-tokenizer.yml up -d``` )
  - Wait for image creation and spin-up (shoud be something like 'Container docker-ua-tokenizer-1  Started')
- [x] Available REST endpoints can be found locally here [Local Swagger UI](http://localhost:8080/swagger-ui/index.html)