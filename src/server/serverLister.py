#!/usr/bin/python3
import socket

UDP_IP = "192.168.1.100"
UDP_PORT = 5005

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind((UDP_IP, UDP_PORT))

print("IP: " + UDP_IP + ", port: " + str(UDP_PORT))


while True:
	data, addr = sock.recvfrom(1024)
	print("recived message: " + str(data.decode('ascii')) + ", from: " + addr[0] + ":" + str(addr[1]))
