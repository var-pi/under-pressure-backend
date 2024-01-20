# Backend server API

Request URL: `http://<address>:<port>/<endpoint>`

## Server

Server port: `9090`

## Endpoints

### GET `/subjects`

Returns all of the subjects available.

### POST `/personal/subjects`

Returns all of the subjects that a user with userId has chosen.

```
data = {
    string userId: <userId>
}
```

### POST `/personal/subjects/add`

A new subject instance is created. If this subject instance already exists the request ends results in a failure.

```
data = {
    string userId: <userId>
    string subjectName: <subjectName>
}
```

### POST `/personal/entries/add`

A new entry is created.

```
data = {
    string userId: <userId>,
    string subjectName: <subjectName>,
    integer stressLevel: <stressLevel>
}
```

<!-- #### GET

#### POST -->

