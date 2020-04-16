function send(){
    colour =  $("#colour").val();
    $.post("/colour", {"newcolour": colour});
}

function recolour(){
    $.get("/colour", function(res){
        $("body").css("background-color", res);
    });
}

setInterval(recolour, 200)
