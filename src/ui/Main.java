//@@author A0126509E

package ui;

public class Main {

    public static void main(String[] args) {
        
        if (args.length == 0) {
            CLI ui = new CLI();
            ui.start();
        } else {
            GUI.main(args);
        }
    }

}
