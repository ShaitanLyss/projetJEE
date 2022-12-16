import { Controller } from '@hotwired/stimulus';
import $ from 'jquery';
import 'jszip';
import 'datatables.net-dt';
import 'datatables.net-buttons-dt';
import 'datatables.net-responsive-dt';
import 'datatables.net-select-dt';
import 'datatables.net-dt/css/jquery.dataTables.css';
import 'datatables.net-buttons-dt/css/buttons.dataTables.css';
import 'datatables.net-responsive-dt/css/responsive.dataTables.css';
import 'datatables.net-select-dt/css/select.dataTables.css';



export default class extends Controller {
    static targets = ["table"]

    b = false
    connect() {
        console.log(this)

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
                dom: 'Blfrtip',
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