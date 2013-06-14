package sait.control.main;

import java.awt.Color;
import com.dmurph.mvc.MVCEvent;

import sait.model.main.MainModel;

public class DisplayHintsEvent extends MVCEvent {

        public final String[] Hints;
        public final MainModel model;

        public DisplayHintsEvent(String[] argHints, MainModel argModel) {
                super(MainController.COLOR_CHANGE);
                Hints = argHints;
                model = argModel;
        }
}