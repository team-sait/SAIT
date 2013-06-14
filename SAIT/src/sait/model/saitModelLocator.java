package sait.model;

import sait.control.main.MainController;
import sait.view.main.MainWindow;

public class saitModelLocator {
        private static saitModelLocator instance = null;
        
        // controllers
        private MainController mainController = new MainController();
        
        private MainWindow mainWindow = null;
        
        private saitModelLocator(){}

        public void setMainWindow(MainWindow argWindow){
                mainWindow = argWindow;
        }
        
        public MainWindow getMainWindow(){
                return mainWindow;
        }
        
        public static saitModelLocator getInstance(){
                if(instance == null){
                        instance = new saitModelLocator();
                }
                return instance;
        }
}