## Carro Inteligente ![shield](https://img.shields.io/github/issues/The-Scrum-Masters/carro-inteligente.svg) [![GitHub stars](https://img.shields.io/github/stars/The-Scrum-Masters/carro-inteligente.svg)](https://github.com/The-Scrum-Masters/carro-inteligente/stargazers)
> an *Internet of Things* innovation int the way we shop. It provides *trolley tracking* information and big data analytics. In our first release we produced a prototype model that can track trolley bay quantities and store this data offsite or locally in a mongoDB.

### Downloads
####Android APK

>[Download Link](https://drive.google.com/file/d/0BxTFdcq6C-VZMHVLQW5VVHZwTEE/view?usp=sharing)

>[Virus check](https://www.virustotal.com/en/file/dd549091ed47f84ed913c35e164f5309b65448eb52374cf62885e0cbb69525bd/analysis/1473392560/)


###Other Information

MongoDB Testing Database Login Information (only use this db for testing)
>mongodb://greg:greg@ds019746.mlab.com:19746/trolleysystem

## How to setup

### Step 1. Requirements
- Python 2.7
- PIP (python package manager)
- PyMongo
- MongoDB Shell or access to port 19746
- Android w/NFC running api 21 (Lolipop) or above

### Step 2. Configuration of  Server

###### Option 1
*For offsite database*

Make sure that this code block isn't commented out and the one after is (by default, this is enabled)

uri = "mongodb://greg:greg@ds019746.mlab.com:19746/trolleysystem"
client = MongoClient(uri)						

(Do this in /src/server/server.py, /src/server/init-bay.py, /src/web-app/viewer.py)

###### Option 2
*For local network database* (requires mongodb shell)

Make sure that this code block isn't commented out and the one before it is

client = MongoClient()						

(Do this in /src/server/server.py, /src/server/init-bay.py, /src/web-app/viewer.py)

###### IP setup for bay initialisation

in /src/server/ip.txt, change the value to the local value of your workstations ip.

Basic commands to find local ip in cmd/terminal:
- ipconfig (Windows)
- ifconfig (MacOSX/Linux)

### Step 2. Configuration of Android Scanner/Client
*at this stage in the project the nfc tag scanner only works with an Android phone with its api above 21 (Lollipop). Prior to 21, nfc scanners were handled differently to modern versions. For this reason, we have opted to support newer phones over older ones. If you do not have an android phone that is above api 21, please use /src/server/server-test-input.py however this is not fully supported*

Download the latest build of the apk at:
>[Download Link](https://drive.google.com/file/d/0BxTFdcq6C-VZMHVLQW5VVHZwTEE/view?usp=sharing)

>[Virus check](https://www.virustotal.com/en/file/dd549091ed47f84ed913c35e164f5309b65448eb52374cf62885e0cbb69525bd/analysis/1473392560/)

After installing open the application and go to the "Read Tag" button
<!--Image of main menu-->

Enter in the servers IP address in the ip field and decide on a port for the port field (you will be prompted for a port when excecuting the server, use the same value)

Nex step is to scan in any nfc tag (e.g. opal card, self written, etc)

After the tag has been successfully read, press send. A toast message will appear explaining that a message attempted to send. It will not however be caught as the server has not been started and the trolley bay has not been created in the mongodb.

### Step 3. Start the Server and Initialise *x* number of Bays



## Next Features to implement
- Python GUI
- Add troley bay config capabilities to android
- Web app interface
