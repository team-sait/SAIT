package sait.control.main;

import com.dmurph.mvc.MVCEvent;

public class QueryHintsDBEvent extends MVCEvent {

        public QueryHintsDBEvent() {
                super(MainController.DISPLAY_TEXT_CHANGE);
        }
}