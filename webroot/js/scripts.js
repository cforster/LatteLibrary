var sock = new WebSocket("ws://localhost:8080");

setInterval(function() {
    sock.send("keep alive");
}, 2000); //heartbeat keeps the socket alive, otherwise it closes.  why?!!!


sock.onopen = function (event) {
    noty({
        text: 'Connected -- app has begun',
        animation: {
            open: 'animated bounceInUp',  // Animate.css class names
            close: 'animated bounceOutUp' // Animate.css class names
        },
        killer: true,
        closeWith: ['click'],
        layout: 'bottom',
        type: 'alert'
    });
};
sock.onmessage = function (event) {
    if(event.data.substring(0,4)=="<svg") {
        $('svg').replaceWith(event.data);   //this is ugly, maybe use underscore?
    }
    else if(event.data == "cleardiv") {
        $('div').empty();
    }
    else {
        $('div').append(event.data);
        refreshhandler();
    }
};
sock.onclose = function(event) {
    noty({
        text: 'Disconnected -- app has terminated',
        animation: {
            open: 'animated bounceInUp', // Animate.css class names
            close: 'animated bounceOutUp' // Animate.css class names
        },
        killer: true,
        closeWith: ['click'],
        layout: 'bottom',
        type: 'alert'
    });
};

function refreshhandler() {
    $('input').on('input', function () {
        sock.send("{\"type\":input, \"name\":" +$(this).context.name +", \"val\":"+ $(this).val()+"}");
    });
    $('button').click(function() {
       sock.send("{\"type\":click, \"name\":" + $(this).context.name +"}");
    });
}