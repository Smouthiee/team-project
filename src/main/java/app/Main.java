package app;

import view.MainMenu;

public class Main {
    public static void main(String[] args) {

        // Main Menu
        MainMenu mainMenu = new MainMenu();
        mainMenu.display();


        // Use case 5: View recent flights
        //AppBuilder builder = new AppBuilder();
        //ViewRecentFlightsView view = builder.buildViewRecentFlightsFeature();
        //view.display();
    }
}