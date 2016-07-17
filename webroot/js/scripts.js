/**
 * Created by charlie on 7/17/16.
 */
var App;

App = {};


/*
 Init
 */

App.init = function() {
    App.canvas = document.createElement('canvas');
    App.canvas.height = document.documentElement.clientHeight;
    App.canvas.width = document.documentElement.clientWidth;
    document.getElementsByTagName('article')[0].appendChild(App.canvas);
    App.ctx = App.canvas.getContext("2d");
    App.ctx.fillStyle = "solid";
    App.ctx.strokeStyle = "#ECD018";
    App.ctx.lineWidth = 5;
    App.ctx.lineCap = "round";


    App.socket = io.connect('http://localhost:9092/');


    App.socket.on('connect', function () {
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
    });
    App.socket.on('drawframe', function (data) {
        j = JSON.parse(data);

        //clear:
        App.ctx.clearRect(0,0,App.canvas.width, App.canvas.height);
        App.canvas.height = document.documentElement.clientHeight;
        App.canvas.width = document.documentElement.clientWidth;

        //draw all objects:
        for(var i=0; i< j.length; i++) {
            App.ctx.save();
            if(j[i]['type']=="rectangle") {
                App.ctx.fillStyle = j[i].color;
                App.ctx.translate(j[i].x+j[i].width/2, j[i].y + j[i].height/2);
                App.ctx.rotate(j[i].rotation*Math.PI/180);
                App.ctx.fillRect(-j[i].width/2, -j[i].height/2, j[i].width, j[i].height);
            }
            else if(j[i]['type']=="svg") {
                App.ctx.fillStyle = j[i].color;
                App.ctx.fill(new Path2D(j[i]['svg']));
            }
            else {
                console.log("unknown type: " + j[i]);
            }
            App.ctx.restore(); //undo any transformation
        }
        //console.log(jobj = j);
        // App.ctx.fillRect(r.x, r.y, r.width, r.height);
    });
    App.socket.on('disconnect', function () {
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
        App.socket.disconnect();
    });
};


$(function() {
    return App.init();
});