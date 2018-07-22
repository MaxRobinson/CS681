/*
 *  Copyright (c) 2018.
 *  Max Robinson
 *  All Rights reserved.
 */

$( function() {

    var picker = $("#datepicker");
    picker.datepicker({
        yearRange: "2018:2020"
    });
    picker.datepicker("setDate", new Date())

    changeDuration();
});

function changeDuration(){
    var hike = document.getElementById("Hike").value;
    var durations = document.getElementById("Duration");
    console.log(hike);
    switch(hike) {
        case "Gardiner Lake":
            // set durations
            durations.innerHTML = setHtmlForDurations([3, 5]);
            break;
        case "Hellroaring Plateau":
            durations.innerHTML = setHtmlForDurations([2, 3, 4]);
            break;
        case "The Beaten Path":
            durations.innerHTML = setHtmlForDurations([5,7]);
            break;
    }

}

function setHtmlForDurations(durations){
    var result = "";
    for(var i = 0; i < durations.length; i++){
        result += "<option>"+durations[i]+"</option>"
    }
    return result;
}