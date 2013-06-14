package sait.model.main;

import java.awt.Color;

import com.dmurph.mvc.model.AbstractModel;

public class MainModel extends AbstractModel {
        private String text = "SAIT Auto Tool";
        private Color color = Color.black;
        private String[] hints = { "Ebullient", "Obtuse", "Recalcitrant",
      	      "Brilliant", "Somnescent", "Timorous", "Florid", "Putrescent" };; 
        
        public void setText(String argText) {
                String old = text;
                text = argText;
                firePropertyChange("text", old, text);
        }
        public void setColor(Color argColor) {
                Color old = color;
                color = argColor;
                firePropertyChange("color", old, color);
        }
        public void setHints(String[] hints) {
        		
        }
        public Color getColor() {
                return color;
        }
        public String getText() {
                return text;
        }
        
        public String[] getHints() {
        	return hints;
        }
}