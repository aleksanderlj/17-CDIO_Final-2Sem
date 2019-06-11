$(function() {
    ajaxGetUserlist();

    // Add user button
    $('#brugerFrom').submit(function(e) {
        e.preventDefault();
        ajaxCreate()});

    // Gets the ID's for all users currently in the database
    function ajaxGetUserlist() {
        $.ajax({
            url : 'rest/useradmin/userlist',
            type : 'GET',
            success : function(data){
                if (data != null) {
                    addUserlist(data);
                }
            },
            error : function(data){
                alert("An unexpected error has occured: USERLIST_ERROR");
            }
        });
        return false;
    }

    // Adds all the DB users into a table on the webpage
    function addUserlist(data) {
        var idArray = data.split(",");

        var i;
        for (i = 0 ; i < idArray.length ; i++){
            ajaxGet(idArray[i]);
        }
    }

    // Creates a button for deleting a row on the webpage
    function makeDeleteButton(id){
        var btn = document.createElement('input');
        btn.type = "button";
        btn.name = "deletebutton";
        btn.value = "Gør inaktiv";

        btn.onclick = (function() {deleteRow(this, id)});

        return btn;
    }

    // Creates a button for updating a row on the webpage
    function makeUpdateButton(id){
        var btn = document.createElement('input');
        btn.type = "button";
        btn.name = "updatebutton";
        btn.value = "Update";
        btn.onclick = (function() {ajaxUpdate(id)});
        return btn;
    }

    // Adds a row to the webpage and sorts the table
    function addRow(data) {
        var table = document.getElementById("brugerFrom");

        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);
        row.id = "row" + data.id;

        row.insertCell(0).innerHTML = data.id;
        row.insertCell(1).innerHTML = data.username;
        row.insertCell(2).innerHTML = data.ini;
        row.insertCell(3).innerHTML = data.cpr;
        row.insertCell(4).appendChild(makeDeleteButton(data.id));
        row.insertCell(5).appendChild(makeUpdateButton(data.id));

        sortTable();

    }

    // Deletes a row from the webpage
    function deleteRow(obj, id) {
        var index = obj.parentNode.parentNode.rowIndex;
        var table = document.getElementById("brugerFrom");
        table.deleteRow(index);
        ajaxDelete(id);
    }

    // Sorts the table (update this to be a merge-sort for epic speed)
    function sortTable() {
        var table, rows, hasSwitched, x, y;
        table = document.getElementById("brugerFrom");
        hasSwitched = true;

        while (hasSwitched) {
            hasSwitched = false;
            rows = table.rows;

            for (var i = 1; i < (rows.length - 1); i++) {
                x = rows[i].getElementsByTagName("TD")[1];
                y = rows[i + 1].getElementsByTagName("TD")[1];

                if (parseInt(x.innerHTML) > parseInt(y.innerHTML)) {
                    rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                    hasSwitched = true;
                    break;
                }
            }
        }
    }

    // Old method for deleting all users
    function deleteAllUsers() {
        var buttons = document.getElementsByName("deletebutton");

        for (var i = 0; i < buttons.length; i++) {
            buttons[i].click();
        }
    }
});