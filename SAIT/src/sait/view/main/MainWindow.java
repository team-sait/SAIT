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
	private String [] partsListCartypes;
	private String [] partsListPartgroups;
	private String [] partsListPartGroup;
	private JMenuBar menuBar;
	private JMenu mnHilfe;
	private JMenu mnDatei;
	private JMenuItem mntmBeenden;
	private JEditorPane dtrpnBrowserPane;
	//private ScrollPane dtrpnBrowserPane_Scrollpane;
	private JComboBox<String> comboBox_HintsTitles;
	private JComboBox<String> comboBox_Manufacturer;
	private JComboBox<String> comboBox_Cartypes;
	private JComboBox<String> comboBox_PartGroup;



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
								"SAIT wurde geschrieben von David Seifried, Marcel Maier, Martin Marinov und Philipp N�gele.");

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
		//addListeners();
		populateLocale();

	}

	private void initComponents() {

		comboBox_HintsCategories = new JComboBox(hintsListCategories);
		comboBox_HintsCategories.setBounds(658, 146, 162, 23);
		// comboBox_HintsTitles = new
		// JComboBox(sait.control.main.Utility.cutString(hintsListTitles,
		// ".htm", ""));
		comboBox_HintsTitles = new JComboBox();
		comboBox_HintsTitles.setBounds(658, 196, 255, 30);



		comboBox_HintsTitles.setEnabled(false);
		getContentPane().setLayout(null);
		getContentPane().add(comboBox_HintsCategories);
		getContentPane().add(comboBox_HintsTitles);

		dtrpnBrowserPane = new JEditorPane();
		dtrpnBrowserPane.setEditable(false);
		dtrpnBrowserPane.setContentType("text/html");
		dtrpnBrowserPane.setBounds(27, 328, 963, 368);

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
									.GetCategoryIDByCategory("hinweise_kategorien" ,selectedItem
											.toString()));
					// hintsListTitles =
					// sait.model.database.QueryDB.queryDB("hinweise", 3);

					comboBox_HintsTitles.addItem("Hinweis ausw�hlen...");
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
					if ( selectedItem.toString() != "Hinweis ausw�hlen...") {


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
		panel.setBounds(411, 278, 208, 23);
		getContentPane().add(panel);
		text = new JLabel("", JLabel.CENTER);
		panel.add(text);
		redButton = new JButton();
		greenButton = new JButton();
		blueButton = new JButton();
		queryHintsDB = new JButton();

		Box box = Box.createHorizontalBox();
		panel.add(box);

		JLabel lblHerzlichWillkommenZu = new JLabel("Herzlich Willkommen zu SAIT - Infos, Tipps und Tricks zu ihrem Auto");
		lblHerzlichWillkommenZu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHerzlichWillkommenZu.setBounds(10, 11, 643, 30);
		getContentPane().add(lblHerzlichWillkommenZu);

		JLabel lblHerstellerWhlen = new JLabel("Hersteller w\u00E4hlen");
		lblHerstellerWhlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHerstellerWhlen.setBounds(40, 144, 120, 23);
		getContentPane().add(lblHerstellerWhlen);

		JLabel lblModellWhlen = new JLabel("Modell w\u00E4hlen");
		lblModellWhlen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblModellWhlen.setBounds(40, 198, 120, 23);
		getContentPane().add(lblModellWhlen);

		JLabel lblBaugruppen = new JLabel("Baugruppen");
		lblBaugruppen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBaugruppen.setBounds(40, 256, 100, 23);
		getContentPane().add(lblBaugruppen);

		comboBox_Manufacturer = new JComboBox();
		comboBox_Manufacturer.setModel(new DefaultComboBoxModel(sait.model.database.QueryDB.queryDB("Hersteller")));
		comboBox_Manufacturer.setBounds(40, 167, 81, 20);
		getContentPane().add(comboBox_Manufacturer);



		comboBox_Cartypes = new JComboBox();
		//comboBox_Cartypes.setModel(new DefaultComboBoxModel(sait.model.database.QueryDB.queryDBByID("PKW", 14)));
		comboBox_Cartypes.setBounds(40, 225, 475, 20);
		getContentPane().add(comboBox_Cartypes);

		comboBox_PartGroup = new JComboBox();
		//comboBox_PartGroup.setModel(new DefaultComboBoxModel(new String[] {"_ausw\u00E4hlen_"}));
		comboBox_PartGroup.setBounds(40, 278, 225, 20);
		getContentPane().add(comboBox_PartGroup);

		JLabel lblHinweiskategorie = new JLabel("Hinweiskategorie");
		lblHinweiskategorie.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHinweiskategorie.setBounds(547, 147, 111, 17);
		getContentPane().add(lblHinweiskategorie);

		JLabel lblHinweis = new JLabel("Hinweis");
		lblHinweis.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHinweis.setBounds(596, 201, 52, 17);
		getContentPane().add(lblHinweis);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/schluessel_klein.png")));
		lblNewLabel.setBounds(710, 52, 63, 54);
		getContentPane().add(lblNewLabel);

		JLabel lblAutogruen = new JLabel("auto_gruen");
		lblAutogruen.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/auto_gruen_klein.png")));
		lblAutogruen.setBounds(40, 52, 111, 70);
		getContentPane().add(lblAutogruen);

		JLabel lblAutoblau = new JLabel("auto_blau");
		lblAutoblau.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/auto_blau_klein.png")));
		lblAutoblau.setBounds(239, 52, 155, 70);
		getContentPane().add(lblAutoblau);

		JLabel lblAutoviolett = new JLabel("auto_violett");
		lblAutoviolett.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/auto_violett_klein.png")));
		lblAutoviolett.setBounds(827, 52, 105, 70);
		getContentPane().add(lblAutoviolett);

		JLabel lblAutorot = new JLabel("auto_rot");
		lblAutorot.setIcon(new ImageIcon(MainWindow.class.getResource("/sait/view/main/bilder/auto_rot_klein.png")));
		lblAutorot.setBounds(888, 256, 86, 70);
		getContentPane().add(lblAutorot);

		JLabel lblNewLabel_1 = new JLabel("Hinweisliste");
		lblNewLabel_1.setBounds(93, 310, 94, 23);
		getContentPane().add(lblNewLabel_1);
		// box.add(redButton);
		// box.add(greenButton);
		// box.add(blueButton);
		// box.add(Box.createRigidArea(new Dimension(30, 1 )));
		// box.add(Box.createHorizontalGlue());
		// box.add(queryHintsDB);

		// No Input possible.
		comboBox_Cartypes.setEnabled(false);


		//If Manufacturer is selected, Update Model list
		comboBox_Manufacturer.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Cartypes");
					Object selectedItem = e.getItem();
					// If we don't do this, adding elements to the list will
					// result in the Listener firing a select action.
					comboBox_Cartypes.setEnabled(false);

					// Clear list.
					comboBox_Cartypes.removeAllItems();

					partsListCartypes = sait.model.database.QueryDB.queryDBByID(
							"PKW", sait.model.database.QueryDB
									.GetCategoryIDByCategory("PKW",selectedItem
											.toString()));
					// hintsListTitles =
					// sait.model.database.QueryDB.queryDB("hinweise", 3);

					comboBox_Cartypes.addItem("Hersteller auswaehlen...");
					for (String s : partsListCartypes) {
						comboBox_Cartypes.addItem(s);

					}
					comboBox_Cartypes.setMaximumSize( comboBox_Cartypes.getPreferredSize() );
					comboBox_Cartypes.setEnabled(true);

				}

			}
		});

		//Initially disabled untill change by Cartypes
		comboBox_PartGroup.setEnabled(false);

		comboBox_Cartypes.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					System.out.println("Cartypes");
					Object selectedItem = e.getItem();
					// System.out.println(selectedItem.toString()+ " " +
					// sait.model.database.QueryDB.GetCategoryIDByCategory(selectedItem.toString()));
					// If we don't do this, adding elements to the list will
					// result in the Listener firing a select action.
					comboBox_PartGroup.setEnabled(false);

					// Clear list.
					comboBox_PartGroup.removeAllItems();

					//Reserved
					//partsListPartgroups = sait.model.database.QueryDB.queryDBByID(
						//	"PKW", sait.model.database.QueryDB
							//		.GetCategoryIDByCategory("PKW",selectedItem
								//			.toString()));



					comboBox_PartGroup.addItem("Bauteilgruppe auswaehlen...");
					partsListPartGroup = sait.model.database.QueryDB.queryDB("Baugruppen");
					for (String s : partsListPartGroup) {
						comboBox_PartGroup.addItem(s);
					}
					comboBox_PartGroup.setEnabled(true);

				}

			}
		});

		//Update Textbox with text from DB, dito picture.
		comboBox_PartGroup.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
//					System.out.println("Cartypes");
					String selectedItem = e.getItem().toString();

//					// System.out.println(selectedItem.toString()+ " " +
//					// sait.model.database.QueryDB.GetCategoryIDByCategory(selectedItem.toString()));
//					// If we don't do this, adding elements to the list will
//					// result in the Listener firing a select action.
//					comboBox_PartGroup.setEnabled(false);
//
//					// Clear list.
//					comboBox_PartGroup.removeAllItems();
//
//					//Reserved
//					//partsListPartgroups = sait.model.database.QueryDB.queryDBByID(
//						//	"PKW", sait.model.database.QueryDB
//							//		.GetCategoryIDByCategory("PKW",selectedItem
//								//			.toString()));
//
//
//
//					comboBox_PartGroup.addItem("Bauteilgruppe auswaehlen...");
//					partsListPartGroup = sait.model.database.QueryDB.queryDB("Baugruppen");
//					for (String s : partsListPartGroup) {
//						comboBox_PartGroup.addItem(s);
//					}
//					comboBox_PartGroup.setEnabled(true);
					if (selectedItem != "Bauteilgruppe auswaehlen..."){
						dtrpnBrowserPane.setText(sait.model.database.QueryDB.GetPartListHTML(selectedItem));
					}

				}

			}
		});
		//
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

//	// we're only updating this method
//	private void addListeners() {
//		redButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ColorChangeEvent event = new ColorChangeEvent(Color.red, model);
//				event.dispatch();
//			}
//		});
//
//		greenButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ColorChangeEvent event = new ColorChangeEvent(Color.green,
//						model);
//				event.dispatch();
//			}
//		});
//
//		blueButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ColorChangeEvent event = new ColorChangeEvent(Color.blue, model);
//				event.dispatch();
//			}
//		});
//
//		queryHintsDB.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				QueryHintsDBEvent event = new QueryHintsDBEvent();
//				event.dispatch();
//			}
//		});
//
//		comboBox_HintsCategories.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				QueryHintsDBEvent event = new QueryHintsDBEvent();
//				event.dispatch();
//			}
//		});
//
//	}

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