#python2.7
import os
from socket import *
from pymongo import *
from pymongo import MongoClient
import datetime


uri = "mongodb://greg:greg@ds019746.mlab.com:19746/trolleysystem"
client = MongoClient(uri)
#client = MongoClient()
db = client.trolleysystem
collections = db.collection_names()

for x in collections:
    cursor = db[str(x)].find({"out": "x"})
    if (len(str(x)) < 6):
        print("bay "+ str(x) +" has "+ str(cursor.count()) +" trolley(s)")
    cursor.close()
