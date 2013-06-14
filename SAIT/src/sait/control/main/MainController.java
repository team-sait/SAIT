package sait.control.main;

import com.dmurph.mvc.control.FrontController;
import sait.command.main.*;

public class MainController extends FrontController {
        // event keys.  The all capital letters is just my preference for the actual keys
        public static final String COLOR_CHANGE = "MAIN_COLOR_CHANGE";
        public static final String DISPLAY_TEXT_CHANGE = "MAIN_DISPLAY_TEXT_CHANGE";
        public static final String HINTS_LIST_CHANGE = "MAIN_HINTS_LIST_CHANGE";
        public static final String MENU_HELP_CLICKED = "MENU_HELP_CHANGE";
       // public static final String UPDATE_HINTS_LIST = "UPDATE_HINTS_LIST_CHANGE";
        
        public MainController(){
                registerCommand(COLOR_CHANGE, ChangeColorCommand.class);
                registerCommand(DISPLAY_TEXT_CHANGE, DisplayTextChangeCommand.class);
                registerCommand(HINTS_LIST_CHANGE, DisplayHintsListChangeCommand.class);
                registerCommand(MENU_HELP_CLICKED, MenuHelpChangeCommand.class);
               // registerCommand(UPDATE_HINTS_LIST, QueryHintsDBEventCommand.class);
        }
}