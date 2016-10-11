from firebase import firebase
firebase = firebase.FirebaseApplication('https://trolley-managementsystem.firebaseio.com/ ', None) #Connects to the URL of our cloud database
result = firebase.get('/', None) #Fetches data from the JSON tree with  path 'Trolley'
print result

