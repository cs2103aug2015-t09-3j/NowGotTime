//@@author A0126509E

package ui;

public class Main {
    public static String mode;

    public static void main(String[] args) {
        
        if (args.length == 0) {
            mode = "CLI";
            CLI ui = new CLI();
            ui.start();
        } else {
            mode = "GUI";
            GUI.main(args);
        }
    }

}
