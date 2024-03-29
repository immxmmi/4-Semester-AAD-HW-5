Homework 5 – Local Database
Tasks
- Implement a Database backend for our News Reader application.
o Use the Android Room ORM.
o Use the Android Room LiveData integration.
o Integrate it with your ViewModel.
- Cache the news data (not the images!) on the database.
o Create a database schema.
o Make sure no duplicate entries can be stored.
o All UI data must be loaded solely from the database.
o The reload functionality downloads the news feed and updates the database
(and not the UI directly).
o The UI must update itself, if data is updated on the database.
o Make sure no news item duplicates are inserted into the database.
o If the news feed URL is changed, all items are deleted from the database.
- All user facing functionality must still work after the update.
Grading
1. Create a Room database and define your database schema. - 20
2. Cache the news data (not the images!) on the database. - 15
3. All UI data must be loaded solely from the database. - 10
4. The reload menu downloads the news feed and updates the database (and not the
UI directly). - 10
5. The UI must update itself, if data is updated on the database. - 10
6. Make sure no news item duplicates are inserted into the database. - 10
7. Use the Android Room LiveData integration & integrate it with your ViewModel. - 10
8. After changing the news feed URL, all data is deleted from the database before new
data is inserted. - 10
9. All user facing functionality must still work after the update. – 5
