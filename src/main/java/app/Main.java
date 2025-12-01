package app;

import view.MainMenu;
import data_access.Util.OpenskyCacheWatcher;

public class Main {
    public static void main(String[] args) {
        OpenskyCacheWatcher watcher = new OpenskyCacheWatcher();
        watcher.start();
        MainMenu mainMenu = new MainMenu();
        mainMenu.display();
    }
}