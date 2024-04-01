# Setup

## Installation of Docker

- [Click here to get Docker](https://www.docker.com/products/docker-desktop)

## Project Clone

- `git clone https://github.com/Try-Parser/OTA-Notes.git`
- `cd OTA-Notes`
- `docker-compose up` to stop press `CTR + C`

### Clean the setup and start the image files and run

- `docker volume prune` to delete hidden volumes
- `docker rm $(docker ps -a -q)` to delete all containers in the docker
- `docker rmi $(docker images -a -q)` to delete all images for the rebuild
- `docker-compose up` will rebuild all images that will be used to the container

## How to run the project

goto the `/OTA-Notes` root of the project folder then type `docker-compose up -d --remove-orphans` for running all containers containing each environment.

- `docker-compose up -d --remove-orphans` : removing orphans while running daemonize environment
    - `docker-compose down` : which stop the running daemonize environment it only works with daemonize environment

- `docker-compose up --remove-orphans` : running the environment without daemon which you can look the logs of each env.

### Host and Ports
- `api` : `localhost:80`

### Api Usage
- ``` 
   Desc : POST /notes: Create a new note
   Uri : localhost/notes
   Payload : {
      "title": "Knight of Empire",
      "body": "Holy AF!"
   }
   Result: {
        "success": true,
        "obj": {
            "id": "4826a936-7f39-4864-8af1-73a36266293b",
            "title": "Knight of Empire",
            "body": "Holy AF!",
            "created": "2024-04-01T06:19:04.416547005Z",
            "updated": null,
            "deleted": false
        }
   }
- ```
  Desc: GET /notes: Retrieve all notes.
  Uri : localhost/notes
  Result: {
    "success": true,
        "obj": [{
          "id": "4826a936-7f39-4864-8af1-73a36266293b",
          "title": "Knight of Empire",
          "body": "Holy AF!",
          "created": "2024-04-01T06:19:04.416547005Z",
          "updated": null,
          "deleted": false
      }]
  }
- ```
  Desc: GET /notes/:id: Retrieve a specific note by ID.
  Uri: localhost/notes/4826a936-7f39-4864-8af1-73a36266293b
  Result: {
    "success": true,
    "obj": {
        "id": "4826a936-7f39-4864-8af1-73a36266293b",
        "title": "Knight of Empire",
        "body": "Holy AF!",
        "created": "2024-04-01T06:19:04.416547005Z",
        "updated": null,
        "deleted": false
    }
  }
- ```
  Desc: PUT /notes/:id: Update a specific note.
  Uri: localhost/notes/4826a936-7f39-4864-8af1-73a36266293b
  Payload: {
    "title": "Knight of Empire",
    "body": "Holly AF!S"
  }
  Result: {
    "success": true,
    "obj": {
        "id": "4826a936-7f39-4864-8af1-73a36266293b",
        "title": "Knight of Empire",
        "body": "Holly AF!S",
        "created": "2024-04-01T06:19:04.416547005Z",
        "updated": "2024-04-01T06:26:16.611859096Z",
        "deleted": false
    }
  }
- ```
  Desc: DELETE /notes/:id: Delete a specific note.
  Uri: localhost/notes/4826a936-7f39-4864-8af1-73a36266293b
  Result: {
    "success": true,
    "obj": "Note : Knight of Empire has been deleted."
  }