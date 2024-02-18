# java-filmorate
# Schema:
![схема бд](..%2F..%2FDownloads%2F%D1%81%D1%85%D0%B5%D0%BC%D0%B0%20%D0%B1%D0%B4)
# SQL DEFAULT QUERIES:

1. SELECT * FROM friendship f JOIN user u on f.first_user = u.id WHERE f.first_user = {user_id} OR f.second_user = {user_id}
2. SELECT * FROM friendship f where f.status = {friendship_status} and ( f.first_user = {user_id} OR f.second_user = {user_id} )
3. SELECT * FROM film_likes fl where fl.film_id = {film_id}
