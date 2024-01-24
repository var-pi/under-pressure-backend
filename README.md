# Backend server API

Request URL: `http://<url><endpoint>`

## Endpoints

### POST `/users/create`

If no such user exists then a new one is created. Otherwise the request results in a failure.

```
data = {
    string userId: <userId>
}
```

### GET `/subjects`

Returns all of the subjects available.

### POST `/personal/subjects`

Returns all of the subjects that this particular user is following.

```
data = {
    string userId: <userId>
}
```

### POST `/personal/subjects/follow`

If user hasn't ever followed the subjec then new subject instance is created. If the subject instance was unfollowed at some point, it starts to be followed again. If subject instance is already being followed, the request results in a failure.

```
data = {
    string userId: <userId>
    string subjectName: <subjectName>
}
```

### POST `/personal/subjects/unfollow`

If subject is currently followed, it is unfollowed. If the subject doesn't exist or is already unfollowed, the request results in an failure.

```
data = {
    string userId: <userId>
    string subjectName: <subjectName>
}
```

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

