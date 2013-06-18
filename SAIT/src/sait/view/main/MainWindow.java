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
		setTitle("SAIT");
		
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

		comboBox_HintsCategories = new JComboBox(hintsListCategories);
		comboBox_HintsCategories.setBounds(433, 113, 172, 54);
		// comboBox_HintsTitles = new
		// JComboBox(sait.control.main.Utility.cutString(hintsListTitles,
		// ".htm", ""));
		comboBox_HintsTitles = new JComboBox();
		comboBox_HintsTitles.setBounds(434, 198, 259, 75);

		

		comboBox_HintsTitles.setEnabled(false);
		getContentPane().setLayout(null);
		getContentPane().add(comboBox_HintsCategories);
		getContentPane().add(comboBox_HintsTitles);

		dtrpnBrowserPane = new JEditorPane();
		dtrpnBrowserPane.setBounds(111, 412, 172, 104);
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
							// This encodes the selected Item to html 
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
		panel.setBounds(327, 310, 239, 206);
		getContentPane().add(panel);
		text = new JLabel("", JLabel.CENTER);
		panel.add(text);
		redButton = new JButton();
		greenButton = new JButton();
		blueButton = new JButton();
		queryHintsDB = new JButton();

		Box box = Box.createHorizontalBox();
		panel.add(box);
		
		JLabel lblHerzlichWillkommenZu = new JLabel("Herzlich Willkommen zu SAIT - Praktische Infos, Tipps und Tricks zu Ihrem Auto");
		lblHerzlichWillkommenZu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHerzlichWillkommenZu.setBounds(10, 11, 643, 30);
		getContentPane().add(lblHerzlichWillkommenZu);
		
		JLabel lblHerstellerWhlen = new JLabel("Hersteller w\u00E4hlen");
		lblHerstellerWhlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHerstellerWhlen.setBounds(40, 133, 120, 23);
		getContentPane().add(lblHerstellerWhlen);
		
		JLabel lblModellWhlen = new JLabel("Modell w\u00E4hlen");
		lblModellWhlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModellWhlen.setBounds(40, 201, 120, 23);
		getContentPane().add(lblModellWhlen);
		
		JLabel lblBaugruppe = new JLabel("Baugruppe");
		lblBaugruppe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBaugruppe.setBounds(40, 277, 100, 23);
		getContentPane().add(lblBaugruppe);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Volkswagen", "Audi", "Mercedes"}));
		comboBox.setBounds(40, 167, 81, 20);
		getContentPane().add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"Golf 3", "A3", "C-Klasse"}));
		comboBox_1.setBounds(40, 235, 100, 20);
		getContentPane().add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"_ausw\u00E4hlen_"}));
		comboBox_2.setBounds(40, 317, 100, 20);
		getContentPane().add(comboBox_2);
		
		JLabel lblHinweiskategorie = new JLabel("Hinweiskategorie");
		lblHinweiskategorie.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHinweiskategorie.setBounds(275, 133, 111, 17);
		getContentPane().add(lblHinweiskategorie);
		
		JLabel lblHinweis = new JLabel("Hinweis");
		lblHinweis.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHinweis.setBounds(275, 228, 111, 17);
		getContentPane().add(lblHinweis);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/schluessel_klein.png")));
		lblNewLabel.setBounds(490, 48, 63, 54);
		getContentPane().add(lblNewLabel);
		
		JLabel lblAutogruen = new JLabel("auto_gruen");
		lblAutogruen.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/auto_gruen_klein.png")));
		lblAutogruen.setBounds(10, 52, 111, 70);
		getContentPane().add(lblAutogruen);
		
		JLabel lblAutoblau = new JLabel("auto_blau");
		lblAutoblau.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/auto_blau_klein.png")));
		lblAutoblau.setBounds(193, 52, 155, 70);
		getContentPane().add(lblAutoblau);
		
		JLabel lblAutoviolett = new JLabel("auto_violett");
		lblAutoviolett.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/auto_violett_klein.png")));
		lblAutoviolett.setBounds(607, 52, 105, 70);
		getContentPane().add(lblAutoviolett);
		
		JLabel lblAutorot = new JLabel("auto_rot");
		lblAutorot.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/auto_rot_klein.png")));
		lblAutorot.setBounds(607, 407, 86, 70);
		getContentPane().add(lblAutorot);
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