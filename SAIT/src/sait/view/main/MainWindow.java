package sait.view.main;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.IOException;

import javax.swing.*;

import sait.control.main.*;
import sait.model.main.*;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;

import java.net.URLEncoder;

public class MainWindow extends JFrame {

	private JLabel text;
	private JButton redButton;
	private JButton greenButton;
	private JButton blueButton;
	private JButton queryHintsDB;

	private MainModel model;
	private JPanel panel;

	// Kategorienliste
	private JComboBox comboBox_HintsCategories;
	// Hinweisliste

	// Save Hint list to array for usage in combo box
	private String[] hintsListCategories = sait.model.database.QueryDB
			.queryDB("hinweise_kategorien");
	private String[] hintsListTitles = sait.model.database.QueryDB.queryDBByID(
			"hinweise", 1);

	private JMenuBar menuBar;
	private JMenu mnHilfe;
	private JMenu mnDatei;
	private JMenuItem mntmBeenden;
	private JEditorPane dtrpnBrowserPane;
	private JComboBox<String> comboBox_HintsTitles;

	public MainWindow(MainModel argModel) {

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnHilfe = new JMenu("Hilfe");
		mnHilfe.addMenuListener(new MenuListener() {

			public void menuCanceled(MenuEvent arg0) {

			}

			public void menuDeselected(MenuEvent arg0) {

			}

			public void menuSelected(MenuEvent arg0) {
				JOptionPane
						.showMessageDialog(
								mnHilfe,
								"SAIT wurde geschrieben von David Seifried, Marcel Maier, Martin Marinov und Philipp Nägele.");

			}

		});

		mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		mntmBeenden = new JMenuItem("Beenden");

		mntmBeenden.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});

		mnDatei.add(mntmBeenden);

		menuBar.add(mnHilfe);
		model = argModel;
		initComponents();
		linkModel();
		addListeners();
		populateLocale();

	}

	private void initComponents() {

		getContentPane().setLayout(new GridLayout(2, 1));

		comboBox_HintsCategories = new JComboBox(hintsListCategories);
		// comboBox_HintsTitles = new
		// JComboBox(sait.control.main.Utility.cutString(hintsListTitles,
		// ".htm", ""));
		comboBox_HintsTitles = new JComboBox();

		

		comboBox_HintsTitles.setEnabled(false);
		getContentPane().add(comboBox_HintsCategories);
		getContentPane().add(comboBox_HintsTitles);

		dtrpnBrowserPane = new JEditorPane();
		dtrpnBrowserPane.setText("test");
		getContentPane().add(dtrpnBrowserPane);

		comboBox_HintsCategories.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {

					Object selectedItem = e.getItem();
					// System.out.println(selectedItem.toString()+ " " +
					// sait.model.database.QueryDB.GetCategoryIDByCategory(selectedItem.toString()));
					// If we don't do this, adding elements to the list will
					// result in the Listener firing a select action.
					comboBox_HintsTitles.setEnabled(false);

					// Clear list.
					comboBox_HintsTitles.removeAllItems();

					hintsListTitles = sait.model.database.QueryDB.queryDBByID(
							"hinweise", sait.model.database.QueryDB
									.GetCategoryIDByCategory(selectedItem
											.toString()));
					// hintsListTitles =
					// sait.model.database.QueryDB.queryDB("hinweise", 3);

					comboBox_HintsTitles.addItem("Hinweis auswählen...");
					for (String s : sait.control.main.Utility.cutString(
							hintsListTitles, ".htm", "")) {
						comboBox_HintsTitles.addItem(s);
					}
					comboBox_HintsTitles.setEnabled(true);

				}

			}
		});

		comboBox_HintsTitles.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Object selectedItem = e.getItem();
					
					// Ugly as sin workaround to "listener fires when categories are changed" problem.
					if ( selectedItem.toString() != "Hinweis auswählen...") {
						

						try {
							// This encodes the selected Item to html (äöü,
							// " ",...)
							// replace("+", "%20") is needed because .encode
							// uses application/x-www-form-urlencoded for
							// html-forms (eg.: &q=Hello+World)
							// instead of %20 for urls
							String selectedItemString = URLEncoder.encode(
									selectedItem.toString(), "ISO-8859-1")
									.replace("+", "%20");

							String URL = "http://chat.juckt-mich.net/SAIT/"
									+ selectedItemString + "/"
									+ selectedItemString + ".htm";

							// This opens the requested URL in a local browser
							java.awt.Desktop.getDesktop().browse(
									java.net.URI.create(URL));

							// Currently disabled: render HTML in Swing. Doesn't
							// work at all.
							// dtrpnBrowserPane.setPage(URL);
						} catch (IOException e1) {

							e1.printStackTrace();
						}
					}
				}
			}
		});

		panel = new JPanel();
		getContentPane().add(panel);
		text = new JLabel("", JLabel.CENTER);
		panel.add(text);
		redButton = new JButton();
		greenButton = new JButton();
		blueButton = new JButton();
		queryHintsDB = new JButton();

		Box box = Box.createHorizontalBox();
		panel.add(box);
		// box.add(redButton);
		// box.add(greenButton);
		// box.add(blueButton);
		// box.add(Box.createRigidArea(new Dimension(30, 1 )));
		// box.add(Box.createHorizontalGlue());
		// box.add(queryHintsDB);
	}

	private void linkModel() {
		text.setText(model.getText());
		text.setForeground(model.getColor());

		model.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();

				if (name.equals("text")) {
					String value = (String) evt.getNewValue();
					text.setText(value);
				} else if (name.equals("color")) {
					Color value = (Color) evt.getNewValue();
					text.setForeground(value);
				} else if (name.equals("comboBox_HintsCategories")) {
					// System.out.println("HintsBox changed");

				}
			}
		});
	}

	// we're only updating this method
	private void addListeners() {
		redButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorChangeEvent event = new ColorChangeEvent(Color.red, model);
				event.dispatch();
			}
		});

		greenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorChangeEvent event = new ColorChangeEvent(Color.green,
						model);
				event.dispatch();
			}
		});

		blueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ColorChangeEvent event = new ColorChangeEvent(Color.blue, model);
				event.dispatch();
			}
		});

		queryHintsDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryHintsDBEvent event = new QueryHintsDBEvent();
				event.dispatch();
			}
		});

		comboBox_HintsCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryHintsDBEvent event = new QueryHintsDBEvent();
				event.dispatch();
			}
		});

	}

	private void populateLocale() {
		// this is where you would normally get the
		// locale strings from your model locator, but
		// that's out of the scope of this example
		redButton.setText("Red");
		greenButton.setText("Green");
		blueButton.setText("Blue");

		queryHintsDB.setText("Hinweise anzeigen");
	}
}