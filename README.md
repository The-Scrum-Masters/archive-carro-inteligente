## Carro Inteligente  ![issues](https://img.shields.io/github/issues/The-Scrum-Masters/carro-inteligente.svg) [![GitHub stars](https://img.shields.io/github/stars/The-Scrum-Masters/carro-inteligente.svg)](https://github.com/The-Scrum-Masters/carro-inteligente/stargazers)
> an *Internet of Things* innovation int the way we shop. It provides *trolley tracking* information and big data analytics. In our first release we produced a prototype model that can track trolley bay quantities and store this data offsite or locally in a mongoDB.

### Downloads
####Android APK

>[Download Link](https://drive.google.com/file/d/0BxTFdcq6C-VZMHVLQW5VVHZwTEE/view?usp=sharing)

>[Virus check](https://www.virustotal.com/en/file/dd549091ed47f84ed913c35e164f5309b65448eb52374cf62885e0cbb69525bd/analysis/1473392560/) (*check the sha256 against this to ensure validity*)


###Other Information

MongoDB Testing Database Login Information (only use this db for testing)
>[mongodb://greg:greg@ds019746.mlab.com:19746/trolleysystem](http://greg:greg@ds019746.mlab.com:19746/trolleysystem)

## How to setup

### Step 0. Requirements
- Python 2.7
- PIP (python package manager)
- PyMongo
- MongoDB Shell or access to port 19746
- Android w/NFC running api 21 (Lolipop) or above

### Step 1. Configuration of  Server

##### Option 1
*For offsite database*

Make sure that this code block isn't commented out and the one after is (by default, this is enabled)

uri = "mongodb://greg:greg@ds019746.mlab.com:19746/trolleysystem"
client = MongoClient(uri)						

(Do this in /src/server/server.py, /src/server/init-bay.py, /src/web-app/viewer.py)

##### Option 2
*For local network database* (requires mongodb shell)

Make sure that this code block isn't commented out and the one before it is

client = MongoClient()						

(Do this in /src/server/server.py, /src/server/init-bay.py, /src/web-app/viewer.py)

##### IP setup for bay initialisation

in /src/server/ip.txt, change the value to the local value of your workstations ip.

Basic commands to find local ip in cmd/terminal:
- ipconfig (Windows)
- ifconfig (MacOSX/Linux)

### Step 2. Configuration of Android Scanner/Client
*at this stage in the project the nfc tag scanner only works with an Android phone with its api above 21 (Lollipop). Prior to 21, nfc scanners were handled differently to modern versions. For this reason, we have opted to support newer phones over older ones. If you do not have an android phone that is above api 21, please use /src/server/server-test-input.py however this is not fully supported*

Download the latest build of the apk at:
>[Download Link](https://drive.google.com/file/d/0BxTFdcq6C-VZMHVLQW5VVHZwTEE/view?usp=sharing)

>[Virus check](https://www.virustotal.com/en/file/dd549091ed47f84ed913c35e164f5309b65448eb52374cf62885e0cbb69525bd/analysis/1473392560/)

After installing open the application and go to the "Read a Tag" button

**Main Menu of Bay (fig. 2.1)**             
![Imgur](http://i.imgur.com/K7dUdtJ.png)

Enter in the servers IP address in the ip field and decide on a port for the port field (you will be prompted for a port when excecuting the server, use the same value)

**Read a Tag Menu of Bay (fig. 2.2)**             
![Imgur](http://i.imgur.com/8BKiogu.png)

Nex step is to scan in any nfc tag (e.g. opal card, self written, etc)

After the tag has been successfully read, press send. A toast message will appear explaining that a message attempted to send. It will not however be caught as the server has not been started and the trolley bay has not been created in the mongodb.

### Step 3. Start the Server and Initialise *x* number of Bays

Navigate to the server folder (/src/server/...) and open a cmd/terminal that supports python. Run the python script and check for any errors (if errors occur, check that you have met all of the requirements).
>It will prompt you to enter a port number, use the same port as the one configured on your android (bay)

Here is an image of the python scripts output in action

**Server Script (fig. 3.3)**             
![Imgur](http://i.imgur.com/L2tUZBm.png)

Next you will need to initiate bays (this step is the same for local and offsite). To initate the bays open a second cmd/terminal window in the same folder and run /src/server/init-bays.py. Once open it will prompt you for a port (same as previous). It will then ask you to enter in bay names, it won't end until the user quits the script with ctrl + C (Windows/Linux) or cmd + C (MacOSX).

>![alert](http://media.uninen.net/admin/img/icon-alert.svg) if this step fails ensure that the ip is configured in /src/server/ip.txt and you are using a valid local port on your network

>![alert](http://media.uninen.net/admin/img/icon-alert.svg)![alert](http://media.uninen.net/admin/img/icon-alert.svg) Make sure that you do not include a '#' in the bay name otherwise it will cause problems with the prototype and the bay name length must be shorter than 10 characters

###Step 4. Testing the system

>Ensure that all devices have been setup with the correct IPs and ports and they are all on the same network using a valid port address

On the Android (bay) scan a tag and press send, on the python server script it should say "Recieved(...)" and the line after should read "Update trolley ... in bay ...".

Navigate to the folder /src/web-app/viewer.py and run the script (ensure that you have configured it to be either offsite or local)

a windows like this should pop up

**Trolley Bay Count (fig. 4.1)**             
![Imgur](http://i.imgur.com/foDIebQ.png)


*Optional* Step. If you are running the mongodb locally, you can view the changes to the local db as you go. Below are a few relevant command for viewing the data in the database
commands for mongo

| **Command**       | **Description**                                       |
|-------------------|-------------------------------------------------------|
| mongod            | Runs mongodb daemon                                   |
| mongo             | Opens mongodb shell                                   |
| show dbs          | lists accessible dbs                                  |
| use ###           | Sets the open db to ###                               |
| show collections  | Lists the names of the collections in your  active db |
| db.###.find()     | Lists all record in collection ### of the active db   |
| db.dropDatabase() | Deletes the existing database                         |

**Example MongoDB Output (fig opt.1)**                          
![Imgur](http://i.imgur.com/lPmiJ58.png)


## Next Features to implement
- Python GUI
- Add troley bay config capabilities to android
- Web app interface
