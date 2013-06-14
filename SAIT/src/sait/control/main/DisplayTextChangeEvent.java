package sait.control.main;

import com.dmurph.mvc.MVCEvent;

public class DisplayTextChangeEvent extends MVCEvent {

        public DisplayTextChangeEvent() {
                super(MainController.DISPLAY_TEXT_CHANGE);
        }
}