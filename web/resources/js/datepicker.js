function loadDatePicker() {
    var inputs = document.getElementsByClassName("date-control");
    for (var i = 0; i < inputs.length; i++) {
        var id = "date" + i;
        inputs[i].id = id;
        window["date" + i] = new AMIB.persianCalendar(id);
    }
}

function loadDatePickerRows() {
    var inputs = document.getElementsByClassName("date-control-rows");
    for (var i = 0; i < inputs.length; i++) {
        var id = "dateRows" + i;
        inputs[i].id = id;
        window["dateRows" + i] = new AMIB.persianCalendar(id);
    }
}
function loadDatePickerDialog() {
    var inputs = document.getElementsByClassName("date-control-dialog");
    for (var i = 0; i < inputs.length; i++) {
        var id = "dateDialog" + i;
        inputs[i].id = id;
        window["dateDialog" + i] = new AMIB.persianCalendar(id);
    }

}

function getID(Object) {
    console.log("i am here.");
    var id = Object.id;
    $('#' + id).datepicker({
        dateFormat: 'yy/mm/dd',
        showButtonPanel: true,
        changeMonth: true,
        changeYear: true
    });
    console.log("i am here 2.");
}