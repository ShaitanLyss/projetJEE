export default class Invoker {
    private commands : Array<Command> = [];
    private currentIndex = - 1

    storeCommand(command: Command): void {
        this.commands = this.commands.slice(0, this.currentIndex + 1)
        this.currentIndex = this.commands.push(command) - 1
        command.execute()
    }


    canGoBack(): boolean {
        return this.currentIndex > 0
    }

    canGoForth(): boolean {
        return this.currentIndex < this.commands.length - 1
    }

    goForth(): void {
        this.currentIndex++
        this.commands[this.currentIndex].execute()
    }

    goBack(): void {
        this.currentIndex--
        this.commands[this.currentIndex].execute()
    }
}