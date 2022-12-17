import { Controller } from '@hotwired/stimulus';
import 'select2'

/*
 * This is an example Stimulus controller!
 *
 * Any element with a data-controller="hello" attribute will cause
 * this controller to be executed. The name "hello" comes from the filename:
 * hello_controller.js -> "hello"
 *
 * Delete this file or adapt it for your use!
 */
export default class extends Controller {
    static targets = ["select2"]

    connect() {

    }

    select2TargetConnected(element) {

        $(element).select2(
        //     {
        //     ajax: {
        //         url : 'api/airports',
        //         dataType: 'json',
        //         processResults: function (data){
        //             console.log("coucou je suis un select 1")
        //             const airports = data["_embedded"]['airports']
        //             console.log(airports)
        //             return {
        //                 results: airports
        //             };
        //         }
        //     }
        // }
        )
    }
}