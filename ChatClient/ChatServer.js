var app  = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

var numberUsers = 0;

io.on('connection', function(socket){

	socket.on('new message', function(data){
        console.log('New Message[Username:' + data.username
            + ' Message: ' + data.message + ']');
        // Emit to devices
        socket.emit('new message', data);
    });

    socket.on('login', function(data){
        console.log('Logging in as: ' + data.username);
        // Emit new user to devices
        socket.emit('user joined', data.username)
    })

    socket.on('user joined', function(name){
        console.log('User Joined as: ' + data.username);
        // Add to the number of active users
        numberUsers++;
        // Emit to devices
        socket.emit('user joined', {
            username: name,
            numUsers: numberUsers
        })

    });

    socket.on('user left', function(name){
        console.log('User Left as: ' + data.username);
        // Subtract to number of active users
        numberUsers--;
        // Emit to devices
        socket.emit('user left',{
            username: name,
            numUsers: numberUsers
        })
    })

});

http.listen(3000, function(){
	console.log("listening on *:3000");
});
