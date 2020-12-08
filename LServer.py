import mysql.connector
import socket
import sys
import threading
import time
import string
import pickle

#Declare paramaters for DB connection
config = {
    'user':'myServer',
    'password':'Frk&311YrC0zY',
    'host':'127.0.0.1',
    'database':'Layoutdb',
    'raise_on_warnings':True
}
#Vicki IPV4 Address
host = "192.168.2.110"
port = 9000
message = " "

#Defining all the necessary statements in a dict... 
#Access: qu = queries["my_statement"]
queries = {
    "add_user"  :       ("INSERT INTO Users "
                        "(idStore, userType, userName, userEmail, userPW) "
                        "VALUES(%s, %s, %s, %s, %s)"),
    "add_store" :       ("INSERT INTO Stores "
                        "(storeName) "
                        "VALUES(%s)"),
    "add_blueprint" :   ("INSERT INTO Blueprints "
                        "(idStore) "
                        "VALUES(%s)"),
    "add_bsegment"  :   ("INSERT INTO bSegments "
                        "(idBlueprint, segType, sX1, sY1, sX2, sY2) "
                        "VALUES(%s, %s, %s, %s, %s, %s)"),
    "add_layout"    :   ("INSERT INTO Layouts "
                        "(idStore, isSet, madeBy) "
                        "VALUES(%s, %s, %s)"),
    "add_lelement"  :   ("INSERT INTO lElements "
                        "(idLayout, elementType, eX, eY, eV1, eV2) "
                        "VALUES(%s, %s, %s, %s, %s, %s)"),
    "update_layout" :   (""),
    "delete_layout" :   (""),
    "get_layout"    :   ("SELECT userName, userEmail FROM Users "
                        "WHERE userType='1'"),
    "verify_login"  :   ("SELECT COUNT(*) FROM Users "
                        "WHERE userName=%s AND userPW=%s AND idStore=%s")                    
}

#command = queries["get_layout"]

#cursor.execute(command)

#for (userName, userEmail) in cursor:
#    print(userName)
#    print(userEmail)
#cnx.commit()

####CLIENT THREAD FUNCTION####
def clientthread(conn, ip):

    #Establish DB connection as cnx
    cnx = mysql.connector.connect(**config)
    cursor = cnx.cursor()
    print("Connected to: ", ip)
    while True:
        #Receive the data from the client
        data = conn.recv(1024)
        #Convert bytes to string
        message = data.decode("utf-8")

        #Parse the password and username from a single string
        tokenStr = message.split() #split string into a list

        #Get the username and password
        clientRequest = tokenStr[0]

        #Determine which command is sent from the Client and execute that command

        #Register command from client
        if clientRequest == "register":
            #Save values received into array and to split them up
            uName = tokenStr[1]
            pswd = tokenStr[2]
            email = tokenStr[3]
            storeName = tokenStr[4]

            command = queries["add_store"]
            cursor.execute(command, (storeName,))
            storeID = cursor.lastrowid
            
            command = queries["add_user"]

            #only an owner can register
            newUserParams=(storeID, 1, uName, email, pswd)
            cursor.execute(command,newUserParams)
            cnx.commit()
            storeStr = str(storeID)
            idBytes = storeStr.encode()
            conn.sendall(idBytes)
            print("from connected user: " + uName)
            print("from connected user: " + pswd)
            print("from connected user: " + storeName)
            #call te database function in servermethods to add entries to database
            sendback = True

            #return to the client whether the query failed or went through
            if sendback == False:
                errormessage = "Denied2"
                sendback_byte = errormessage.encode()
                conn.sendall(sendback_byte)
                conn.close()
            if sendback == True:
                errormessage = "Verified"
                sendback_byte = errormessage.encode()
                conn.sendall(sendback_byte)
                conn.close()


        #Login command received from client
        if clientRequest == "login":
            uName = tokenStr[1]
            pswd = tokenStr[2]
            idStore = tokenStr[3]
            login_vals = (uName, pswd, idStore)

            command = queries["verify_login"]

            cursor.execute(command,login_vals)
            numRes = cursor.fetchone()
            if numRes==1:
                sendback=True
                #success! 

            print("from connected user: " + uName)
            print("from connected user: " + pswd)
            print("from connected user: " + idStore)

            #Call the database function in server methods to check if the password mataches the username registered
            sendback = "Verified"

            #If it doesnt match return denied to client
            if sendback == False:
                errormessage = "Denied2"
                sendback_byte = errormessage.encode()
                conn.sendall(sendback_byte)
                conn.close()


            sendback_byte = sendback.encode()
            conn.sendall(sendback_byte)


        #Close the connection
        conn.close()
        cursor.close()
        cnx.close()
        break

def main():
#Create a Socket
    serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print("Socket Created")

    try:
        serversocket.bind((host,port))
    except socket.error:
        print("Failed to Bind to Socket")
        sys.exit()

    print("Socket has been successfully binded...")

    #Listen to incoming connection
    serversocket.listen(10)

    #Accept Clients
    while True:
        conn, addr = serversocket.accept()

        #Start a new thread for each client
        threading._start_new_thread(clientthread, (conn,addr))

main()
