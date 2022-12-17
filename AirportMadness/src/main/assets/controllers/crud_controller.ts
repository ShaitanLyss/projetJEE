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
import UpdateDatatablesCommand from "../classes/command/UpdateDatatablesCommand";
import Invoker from "../classes/command/invoker";


export default class CrudController extends Controller {
    static targets = ["datatable", "entitySelection"]

    declare readonly entitySelectionTargets
    declare readonly datatableTargets

    invoker = new Invoker()


    connect() {
        $.get("/api/", null, response => {
            const links = Object.entries(response['_links']).filter(([k, v]) => k !== "profile")

            for (const e of this.entitySelectionTargets) {
                links.forEach(([k, v]) => {
                    $(e).append($(
                        "<li><button class='dt-button' data-action='click->crud#selectEntity' value='" + v["href"] + "' >" + capitalize(k) + "</button></li>"
                    ))
                })
            }
        })
    }


    selectEntity(event) {
        const url = event.currentTarget.value
        const re = new RegExp('.*/([A-Za-z]+)$')
        const entitiesName = url.match(re)[1]

        this.invoker.storeCommand(new UpdateDatatablesCommand(url, "/api/profile/" + entitiesName, this))
    }

    updateDatatables(url, profile) {
        for (const datatableTarget of this.datatableTargets) {
            if ($.fn.DataTable.isDataTable(datatableTarget)) {
                $(datatableTarget).DataTable().destroy();
                $(datatableTarget).empty()
            }

            this.initializeDatatable(datatableTarget, url, profile)

        }
    }

// datatableTargetConnected(target) {
    //     console.log(this.datatableTarget)
    //     this.initializeDatatable(target);
    // }
    selectLinkedEntity(e) {
        const btn = $(e.currentTarget)
        this.updateDatatables(btn.val(), btn.data().profile)
    }

    initializeDatatable(target, url, profile) {
        $.get(profile, null, (response) => {
            const entityDescription = response.alps.descriptor[0].descriptor
            const columns = entityDescription.map(e => {
                return {data: e.name, title: capitalize(e.name)}
            })

            const linkNamesAndProfile = entityDescription.filter(e => e.type === 'SAFE').map(e => {
                return [e.name, e.rt]
            })

            if (columns.length) {
                $(target).DataTable({
                    processing: true,
                    serverSide: false,

                    columns: columns,
                    ajax: {
                        url: url,
                        dataSrc: function (data) {
                            // merge all groups of entities into one array (needed in case of inheritance)
                            let res = Object.entries(data._embedded).reduce(
                                (accu, [k, v]) => {
                                    console.log(v)
                                    return accu.concat(v)
                                },
                                []
                            )

                            // add linked entities columns
                            res = res.map((e) => {
                                for (const [linkName, profile] of linkNamesAndProfile) {
                                    e[linkName] = `<button class="dt-button" data-profile="${profile}" data-action="click->crud#selectLinkedEntity" value="${e._links[linkName].href}">Voir</button>`
                                }
                                return e
                            })

                            return res
                        }
                    },
                    dom: 'B<"clear">lfrtip',
                    buttons: [
                        {
                            text: 'Retour',
                        },
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
