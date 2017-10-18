wsUri =  "ws://localhost:8081/websocket";
var sock = new WebSocket(wsUri);

    sock.onopen = function (event) {
        startednoty = noty({
            text: 'Connected -- app has begun (click to terminate)<span style=\"color:blue;\" class=\"coords\"></span>',
            animation: {
                open: 'animated bounceInUp',  // Animate.css class names
                close: 'animated bounceOutDown' // Animate.css class names
            },
            closeWith: ['click'],
            callback: {
                onClose: function () {
                    sock.close();
                }
            },
            layout: 'bottom',
            type: 'alert'
        });
        $(window).resize();
    };
    sock.onmessage = function (event) {
        message = JSON.parse(event.data);

        if (message.type == "svgt") {
            //the svg gets updated using reactjs:
            var root = React.createElement("div", {
                className: "content",
                dangerouslySetInnerHTML: {__html: message.payload},
            });
            ReactDOM.render(root, document.getElementById('user-svg'));
            refreshhandler(); //for click handlers
            //consider adding keys if people get massive slowdown
        }
        else if (message.type == "cout") {
            consoleout(message.payload);
        }
        else if (message.type == "coin") {
            consolein();
        }
        else if (message.type == "cocl") {  //console clear
            $('div.console').empty();
        }
        else if (message.type == "cldi") {  //clear div
            $('div.content').empty();
        }
        else if (message.type == "html") {
            $('div.content').append(message.payload);
            refreshhandler();
        }
        else if (message.type == "logi") {
            $('#loginmodal').modal({
                escapeClose: false,
                clickClose: false,
                showClose: false,
                fadeDuration: 100
            });
            $("#loginbtn").on('click', function (e) {
                if ($('input#username').val() != "") {
                    $.modal.close();
                    sock.send("{\"type\": login-success}");
                }  //for now, any content will do.
                //todo: replace with actual auth.
            })
        }
        else if (messagetype == "titl") {
            document.title = message.payload;
        }
        else {
            console.log("message not handled: " + event.data);
        }
    };
    sock.onclose = function (event) {
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

        setTimeout(function () {
            startednoty.close();
        }, 1000);

        //reload the page when the server is back up:
        setInterval(function() {
            $.ajax({
                url  : "/resources/images/bunny.jpeg", /* or other resource */
                type : "HEAD"
            })
                .done(function() {
                    location.reload();
                });
        }, 1500); /* 1.5 seconds */

    };

//    function check(){
//        if(!ws || ws.readyState == 3) start();
//    }
//
//    start();
//
//    setInterval(check, 1000);
//}
$( window ).resize(function() {
    const json = JSON.stringify( {
        type: 'resize',
        width: document.documentElement.clientWidth,
        height: document.documentElement.clientHeight
    });
    sock.send(json);
});

$( function() {
        $("body").mousemove(function (e) {
            $('span.coords').empty().append("(" + e.pageX + ", " + e.pageY + ")");
        });
    });




function refreshhandler() {
    $('input').on('input', function () {
        const json = JSON.stringify({
            type: 'input',
            name: $(this).context.name,
            val: $(this).val()
        });
        sock.send(json);
    });
    $('.clickable').click(function() {
       sock.send("{\"type\":click, \"name\":" + $(this).attr('name') +"}");
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
    $('input.in').trigger('input'); //clear the text
    $('input.in').focus();
}

//leap controller:
var controller = new Leap.Controller();
controller.on("frame", function(frame){
    if(frame.pointables.length > 0)
    {
        //Get a pointable and normalize the tip position
        var pointable = frame.pointables[0];
        var interactionBox = frame.interactionBox;
        var normalizedPosition = interactionBox.normalizePoint(pointable.tipPosition, true);

        // Convert the normalized coordinates to span the canvas
        target = $('#leap-circle');
        cx = $(window).width() * normalizedPosition[0];
        cy = $(window).height() * (1 - normalizedPosition[1]);

        target.attr('cx', cx);
        target.attr('cy', cy);

        sock.send("{\"type\":leap-position, \"x\": \"" +cx +"\" , \"y\":\""+ cy+"\"}");
    }
});
controller.connect();
