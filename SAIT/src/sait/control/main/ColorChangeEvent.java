package sait.control.main;

import java.awt.Color;
import com.dmurph.mvc.MVCEvent;

import sait.model.main.MainModel;

public class ColorChangeEvent extends MVCEvent {

        public final Color color;
        public final MainModel model;

        public ColorChangeEvent(Color argColor, MainModel argModel) {
                super(MainController.COLOR_CHANGE);
                color = argColor;
                model = argModel;
        }
}