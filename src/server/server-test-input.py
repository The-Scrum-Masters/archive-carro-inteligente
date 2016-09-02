#python2.7
import os
from socket import *
from uuid import getnode

#get host ip
meHost = open('ip.txt','r')
host =  meHost.read().split('\n')[0]
meHost.close()

port = int(raw_input("Enter port: "))
addr = (host, port)
UDPSock = socket(AF_INET, SOCK_DGRAM)

while True:

    data = raw_input("enter input: ")
    if data == "exit":
        break
    UDPSock.sendto(data, addr)
UDPSock.close()
os._exit(0)
