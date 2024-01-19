# Backend server API

Request URL: `http://<address>:<port>/<endpoint>`

## Server

Server port: `9090`

## Endpoints

### `/subjects`

#### GET

Returns all of the subjects available.

<br>

### `{userId}/subjects`

#### GET

Returns all of the subjects that a user with userId has chosen.

#### POST

```
data = {
    userId: <userId>
    subjectName: <subjectName>
}
```

### `/{userId}/subjects/`

#### GET

#### POST

<br>

