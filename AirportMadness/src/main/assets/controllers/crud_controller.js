import { Controller } from '@hotwired/stimulus';
import $ from  'jquery';

// console.log($.fn.dataTable)
var dt = require("datatables.net")
var dt_extras = [

    require("datatables.net-dt")(window, $),
    // require("datatables.net-buttons-dt"),
    // require("datatables.net-responsive"),
    // require("datatables.net-responsive-bs"),
    require("datatables.net-select-dt"),
    // require("datatables.net-datetime")
];

// dt_extras.forEach(function(e) {e(window, $);});



export default class extends Controller {
    static targets = ["table"]

    b = false
    connect() {
        const e = this.element
        if (!this.b) {
            const table = $(e).DataTable({
                processing: true,
                serverSide: false,

                columns: [
                    {data: "firstName", title: "Prénom"},
                    {data:  "lastName", title: "Nom de famille"},
                    {data: "birthdate", title: "Naissance"},
                    {data: "gender", title: "Sexe" },
                    {data: "nationality", title: "Nationalité"}
                ],
                ajax: {
                    url : "/api/employees",
                    dataSrc: '_embedded.employees'
                },
                // dom: '',
                buttons: [{
                    text: 'Reload'
                }
                ],
                // deferRender: true,
                select: true

            });

            this.b = true
        }

    }
}