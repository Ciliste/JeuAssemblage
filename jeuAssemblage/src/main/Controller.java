package main;

public class Controller {
    
    private static Controller instance;
    public static Controller getInstance() {

        if (instance == null) {

            instance = new Controller();
        }

        return instance;
    }
}
