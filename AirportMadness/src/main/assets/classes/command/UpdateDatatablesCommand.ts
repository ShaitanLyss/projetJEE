import {Controller} from "@hotwired/stimulus";
import CrudController from "../../controllers/crud_controller";

export default class UpdateDatatablesCommand implements Command {
    private readonly url: string;
    private readonly profile: string;
    private readonly controller: CrudController;

    constructor(url: string, profile: string, controller: CrudController) {
        this.url = url
        this.profile = profile;
        this.controller = controller
    }

    execute(): void {
        this.controller.updateDatatables(this.url, this.profile)
    }

}