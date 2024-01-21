# Backend server API

Request URL: `http://<url><endpoint>`

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

### POST `/personal/subjects/follow`

A new subject instance is created. If this subject instance already exists the request ends results in a failure.

```
data = {
    string userId: <userId>
    string subjectName: <subjectName>
}
```

<!-- ### POST `/personal/subjects/unfollow` -->

### POST `/personal/entries`

Returns all the entries made by given user on the particular subject.

```
data = {
    string userId: <userId>,
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

