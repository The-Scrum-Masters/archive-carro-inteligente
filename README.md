## Smart Trolley System

> an *Internet of Things* innovation int the way we shop. It provides *trolley tracking* information and big data analytics. In our first release we produced a prototype model that can track trolley bay quantities and store this data offsite or locally in a mongoDB.


MongoDB Testing Database Login Information
>mongodb://greg:greg@ds019746.mlab.com:19746/trolleysystem

## How to setup

### Step 1. Requirements
- Python 2.7
- PIP (python package manager)
- PyMongo
- MongoDB Shell or access to port 19746

### Step 2. Configuration

###### Option 1
For offsite database

Make sure that this code block isn't commented out and the one after is

uri = "mongodb://greg:greg@ds019746.mlab.com:19746/trolleysystem"
client = MongoClient(uri)						
.  

###### Option 2
>For local network database (requires mongodb shell)
