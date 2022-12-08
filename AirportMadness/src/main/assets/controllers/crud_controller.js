import { Controller } from '@hotwired/stimulus';
import $ from  'jquery';
import 'datatables.net';


export default class extends Controller {
    b = false
    connect() {
        const e = this.element
        if (!this.b) {
            $(e).DataTable({
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
                deferRender: true
            });

            this.b = true
        }

    }
}