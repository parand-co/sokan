/**
 * Created by ici on 7/16/2017.
 */
function getIDALLSS(Object) {
    var id = Object.id;
    $('#' + id).miladPicker({
        dateFormat: 'yy/mm/dd',
        showButtonPanel: true,
        changeMonth: true,
        changeYear: true
    });
}