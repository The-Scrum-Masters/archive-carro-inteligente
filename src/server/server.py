#python2.7
import os
from socket import *
from pymongo import *
from pymongo import MongoClient
import datetime

host = ""
port = int(raw_input("Enter port address: "))
buf = 1024
addr = (host, port)
UDPSock = socket(AF_INET, SOCK_DGRAM)
UDPSock.bind(addr)

uri = "mongodb://greg:greg@ds019746.mlab.com:19746/trolleysystem"
client = MongoClient(uri)
#client = MongoClient()
db = client.trolleysystem

def addElement(bayId, trolleyId):
    print("update trolley " + trolleyId+" in "+bayId)
    if not bayId in db.collection_names(): #check if bayId exists
        print("not a valid bay")
        return
    collection = db[bayId]
    cursor = db[bayId].find({"trolley": trolleyId, "out": "x"})
    toIn = (cursor.count() == 0) #decides if it needs to create a new element or add an out to an existing one
    cursor.close()
    if toIn: #if the trolley doesn't exist or has an end date
        result = collection.insert_one(
        {
            "trolley": trolleyId,
            "in": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
            "out": "x"
        }
        )
        result.inserted_id

    if toIn == False : #if the trolley exist and it  doesn't have an end date
        result = collection.update_one(
            {"trolley": trolleyId, "out": "x"},
            {"$set": {"out": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")}}
        )

def makeCollection(bayId):
    print("adding bay " + bayId)
    collection = db[bayId]
    result = collection.insert_one(
    {
        "trolley": "null",
        "in": "null",
        "out": "null"
    }
    )
    result.inserted_id


print "Server started..."
while True:
    inputType = ""
    bayId = ""
    trolleyId = ""
    (data, addr) = UDPSock.recvfrom(buf)
    print "Received(" + data +")\n"
    try:
        inputType = data.split(">")[0]
        data = (data.split(">")[1])
        if inputType == "TRIO":
            bayId = data.split("#")[0]
            trolleyId = data.split("#")[1]
            #print "inputType="+inputType+", bayId="+bayId+", trolleyId="+trolleyId+"\n"
            addElement(bayId,trolleyId)


        if inputType == "INIT":
            bayId = data
            #print "inputType="+inputType+", bayId="+bayId+"\n"
            makeCollection(bayId)

    except :
        print "invalid message recieved"
UDPSock.close()
os._exit(0)
