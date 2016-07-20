var exampleSocket = new WebSocket("ws://localhost:8080");
exampleSocket.onopen = function (event) {
    noty({
        text: 'Connected -- app has begun',
        animation: {
            open: {height: 'toggle'}, // jQuery animate function property object
            close: {height: 'toggle'}, // jQuery animate function property object
            easing: 'swing', // easing
            speed: 500 // opening & closing animation speed
        },
        killer: true,
        closeWith: ['click', 'backdrop'],
        layout: 'bottom',
        type: 'alert'
    });
};
exampleSocket.onmessage = function (event) {
    $('svg').replaceWith(event.data);
    exampleSocket.send("keep alive"); //this keeps the socket alive, otherwise it closes.  why?!!!
}
exampleSocket.onclose = function(event) {
    noty({
        text: 'Disconnected -- app has terminated',
        animation: {
            open: {height: 'toggle'}, // jQuery animate function property object
            close: {height: 'toggle'}, // jQuery animate function property object
            easing: 'swing', // easing
            speed: 500 // opening & closing animation speed
        },
        killer: true,
        closeWith: ['click', 'backdrop'],
        layout: 'bottom',
        type: 'alert'
    });
}