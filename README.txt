SETUP INFORMATION

Note... Our server requires a running instance of MySQL on port 80

Classes needed for the server: mysql.connector
This can be downloaded in the installation of MySQLWorkbench, or from the MySQL downloads page. Please see https://dev.mysql.com/doc/connector-python/en/connector-python-introduction.html for details on this class as well as some download instructions for your OS.

Once initialized, the required SQL statements to replicate the DB Schema are located in a file called 'SQL_Create_Statements.txt'. This can be found in the github repository.

The client is hardcoded to connect to the IP address of the device running the server LServer.py. Currently set to: 192.168.2.110 with a port of: 9000

These values are also declared and referenced in the server file for opening it's socket.

