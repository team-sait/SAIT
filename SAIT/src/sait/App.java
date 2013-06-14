package sait;

import javax.swing.JFrame;

import com.dmurph.mvc.MVC;

import sait.model.saitModelLocator;
import sait.model.main.MainModel;
import sait.view.main.MainWindow;

public class App {
        
        public static void main(String[] args) {
        		//Debugging-Monitor
        		MVC.showEventMonitor();  
        		saitModelLocator locator = saitModelLocator.getInstance();
                MainModel model = new MainModel();
                MainWindow window = new MainWindow(model);
                locator.setMainWindow(window);
                window.pack();
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
        }
}