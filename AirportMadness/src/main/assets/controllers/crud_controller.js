import {Controller} from '@hotwired/stimulus';
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

import capitalize from 'underscore.string/capitalize';


export default class extends Controller {
    static targets = ["datatable", "entitySelection"]

    initialize() {
        super.initialize();

    }

    connect() {
        $.get("/api/", null, response => {
            const links = Object.entries(response['_links']).filter(([k, v]) => k !== "profile")

            for (const e of this.entitySelectionTargets) {
                links.forEach(([k, v]) => {
                    $(e).append($(
                        "<li><button class='dt-button' data-action='click->crud#selectEntity' value='" + v.href + "' >" + capitalize(k) + "</button></li>"
                    ))
                })
            }
        })
    }


    selectEntity(event) {
        const url = event.currentTarget.value

        for (const datatableTarget of this.datatableTargets) {
            if ($.fn.DataTable.isDataTable(datatableTarget)) {
                $(datatableTarget).DataTable().destroy();
                $(datatableTarget).empty()
            }

            this.initializeDatatable(datatableTarget, url)

        }
    }


    // datatableTargetConnected(target) {
    //     console.log(this.datatableTarget)
    //     this.initializeDatatable(target);
    // }

    initializeDatatable(target, url) {
        const re = new RegExp('.*/([A-Za-z]+)$')
        const entitiesName = url.match(re)[1]

        $.get("/api/profile/" + entitiesName, null, (response) => {
            const entityDescription = response.alps.descriptor[0].descriptor
            const columns = entityDescription.map(e => {
                return {data: e.name, title: capitalize(e.name)}
            })

            const linkNames = entityDescription.filter(e => e.type === 'SAFE').map(e => {
                return e.name
            })

            if (columns.length) {
                $(target).DataTable({
                    processing: true,
                    serverSide: false,

                    columns: columns,
                    ajax: {
                        url: url,
                        dataSrc: function (data) {
                            let res = data._embedded[entitiesName]

                            res = res.map((e) => {
                                for (const linkName of linkNames) {
                                    e[linkName] = `<button class="dt-button" value="${e._links[linkName].href}">Voir</button>`
                                }
                                return e
                            })

                            return res
                        }
                    },
                    dom: 'B<"clear">lfrtip',
                    buttons: [
                        {
                            text: 'Supprimer',
                            action: (e, dt, node, config) =>
                                $.ajax({
                                    url: '/api',
                                    type: 'GET',
                                    success: function (result) {
                                        console.log(result)
                                        // Do something with the result
                                    }
                                })
                        }
                    ],
                    deferRender: true,
                    select: true

                });
            }
        })


    }
}