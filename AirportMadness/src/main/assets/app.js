/*
 * Welcome to your app's main JavaScript file!
 *
 * We recommend including the built version of this JavaScript file
 * (and its CSS file) in your base layout (base.html.twig).
 */

// any CSS you import will output into a single css file (app.css in this case)
// require('datatables.net-responsive')(window);
// require('datatables.net-buttons')(window);

import './styles/app.css';
// import  'tw-elements';
import './styles/global.scss'
import 'select2'
import 'select2/dist/css/select2.css'
import 'bootstrap'
import '@fortawesome/fontawesome-free/css/all.css'
import '@fortawesome/fontawesome-free/js/all'
import loadGoogleMapsApi from 'load-google-maps-api'





// window.initAutocomplete = function () {
//     const event = new Event('google-maps-callback', {
//         bubbles: true,
//         cancelable: true,
//     })
//     window.dispatchEvent(event)
// }

loadGoogleMapsApi({
    key: "AIzaSyBLg9omiv8MglTQ9Y8Q1UFfuzBhUQpj-Ag",
    libraries: ["places"],
    // callback: window.initAutocomplete
}).then(function() {
    const event = new Event('google-maps-callback', {
        bubbles: true,
        cancelable: true,
    })
    window.dispatchEvent(event)
})

// start the Stimulus application
import './bootstrap.js';
