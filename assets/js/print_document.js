document.writeln("html javascript inovke java");

alert("window alert");
window.confirm("window confirm");
window.prompt("window prompt");


var callJs = function() {
    document.getElementById("content").innerHTML += "<br /> java invoke js";
}

var callJsWithParam = function(param) {
    document.getElementById("content").innerHTML += "<br /> java invoke js with " + param;
}