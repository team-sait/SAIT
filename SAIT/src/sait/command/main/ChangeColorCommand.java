package sait.command.main;

import com.dmurph.mvc.MVCEvent;
import com.dmurph.mvc.control.ICommand;
import sait.control.main.ColorChangeEvent;

public class ChangeColorCommand implements ICommand {

        public void execute(MVCEvent argEvent) {
                ColorChangeEvent event = (ColorChangeEvent) argEvent;
                event.model.setColor(event.color);
                String type = "123";
        }
}