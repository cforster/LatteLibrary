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
    messagetype = event.data.substring(0,4)
    content = event.data.substring(4);

    if(messagetype =="svgt") {
        $('svg').replaceWith(content);   //this is ugly, maybe use underscore?
    }
    else if (messagetype == "cout") {
        consoleout(content);
    }
    else if(messagetype == "coin") {
        consolein();
    }
    else if(messagetype == "cocl") {  //console clear
        $('div.console').empty();
    }
    else if(messagetype == "cldi") {  //clear div
        $('div.content').empty();
    }
    else if(messagetype == "html") {
        $('div.content').append(content);
        refreshhandler();
    }
    else {
        console.log("message not handled: " + event.data);
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
        sock.send("{\"type\":input, \"name\":" +$(this).context.name +", \"val\":\""+ $(this).val()+"\"}");
    });
    $('.clickable').click(function() {
       sock.send("{\"type\":click, \"name\":" + $(this).context.name +"}");
    });
    $('input.in').keypress(function(e) {
        if(e.which == 13) {
            sock.send("{\"type\":in-enter}");
            $('input.in').replaceWith($('input.in').val());
        }
    });
}

function consoleout(txt) {
    $('div.console').append("<div class=\"console-element\"><span class=\"console-symbol\">&gt;&nbsp;</span>" + txt + "</div>");
}
function consolein(txt) {
    $('div.console').append("<div class=\"console-element\">" +
        "<span class=\"console-symbol\">&lt;&nbsp;</span>" +
        "<input name=\"in\" class=\"in\" on></div>");
    refreshhandler();
    $('input.in').focus();
}