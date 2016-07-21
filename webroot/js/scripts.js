var sock = new WebSocket("ws://localhost:8080");

var heartbeat = setInterval(function() {
    sock.send("keep alive");
}, 2000); //heartbeat keeps the socket alive, otherwise it closes.  why?!!!

$( function() {
    $("body").mousemove(function (e) {
        $('span.coords').empty().append("(" + e.pageX + ", " + e.pageY + ")");
    });
});

sock.onopen = function (event) {
    startednoty = noty({
        text: 'Connected -- app has begun  <span style=\"color:blue;\" class=\"coords\"></span>',
        animation: {
            open: 'animated bounceInUp',  // Animate.css class names
            close: 'animated bounceOutDown' // Animate.css class names
        },
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
    endnoty = noty({
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

    clearInterval(heartbeat);

    setTimeout(function() { startednoty.close(); console.log("startednoty") }, 1000);
};

function refreshhandler() {
    $('input').on('input', function () {
        sock.send("{\"type\":input, \"name\":" +$(this).context.name +", \"val\":"+ $(this).val()+"}");
    });
    $('button').click(function() {
       sock.send("{\"type\":click, \"name\":" + $(this).context.name +"}");
    });
}