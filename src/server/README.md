###Server

So far the server is able to interpret two different socket messages
- INIT>bay
- TRIO>bay:trolley

It then saves these to a mongodb called "trolleysystem"

####Commands for mongodb shell
- show dbs
	- lists all current databases
- use <db>
	- creates or opens database
- db.dropDatabase()
	- deletes database
- db.<collection>.find()
	- prints all elements in a collection
