import { Controller } from '@hotwired/stimulus';
import $ from 'jquery';

export default class extends Controller {
    static targets = ["source", "dest"]

    declare readonly sourceTarget;
    declare readonly destTarget;

    connect() {
    }

    switchInputs() {
        const source = this.sourceTarget;
        const dest = this.destTarget;
        $(source).find(`[data-switch-input]`).each((i, s) => {
            const d = $(dest).find(`[data-switch-input='${s.dataset.switchInput}']`)
            const temp = d.val()
            d.val(s.value)
            s.value = temp

        })
    }
}