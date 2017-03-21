package com.diplomat.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreferencePanel extends JPanel {
	private static final Logger log = LoggerFactory.getLogger(PreferencePanel.class);
	private static final long serialVersionUID = 1L;
	public static final String TEST_FILE = "testFile";
	public static final String DATABASE = "database";
	private File testFile;
	private File database;
	private boolean cart;
	private boolean errorsOnly;
	private final JFileChooser fc = new JFileChooser();

	public PreferencePanel(){
		super();
		fc.setAcceptAllFileFilterUsed(false);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		add(fileInput(TEST_FILE, "Test File", new FileNameExtensionFilter("Test File", "xml")), BorderLayout.NORTH);
		add(fileInput(DATABASE, "Database File", new FileNameExtensionFilter("Database", "db")), BorderLayout.CENTER);
		add(checkboxs(), BorderLayout.SOUTH);
	}
	private JPanel checkboxs(){
		JPanel returnPanel = new JPanel();
		returnPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		returnPanel.setLayout(new BorderLayout(0, 0));
		returnPanel.add(cartCheckbox(), BorderLayout.EAST);
		returnPanel.add(errorsOnlyCheckbox(), BorderLayout.WEST);
		return returnPanel;
	}
	private JCheckBox cartCheckbox() {
		JCheckBox checkbox = new JCheckBox("Cart Calculation");
		checkbox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                setCart(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
		return checkbox;
	}
	private JCheckBox errorsOnlyCheckbox() {
		JCheckBox checkbox = new JCheckBox("Errors Only");
		checkbox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                setErrorsOnly(e.getStateChange() == ItemEvent.SELECTED);
            }
        });
		return checkbox;
	}
	private JPanel fileInput(final String fileType, String buttonText, final FileNameExtensionFilter filter) {
		JPanel returnPanel = new JPanel();
		returnPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		returnPanel.setLayout(new BorderLayout(0, 0));
		final JTextField inputField = new JTextField();
		inputField.setEnabled(false);
		inputField.setDisabledTextColor(Color.BLACK);
		inputField.setColumns(30);
		JButton chooser = new JButton(buttonText);
		chooser.setPreferredSize(new Dimension(150, 0));
		chooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				fc.resetChoosableFileFilters();
				fc.setFileFilter(filter);
				int returnVal = fc.showOpenDialog(PreferencePanel.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					setFile(fc.getSelectedFile(), fileType);
					inputField.setText(getFile(fileType).getAbsolutePath());
		            log.info("Opening: " + getFile(fileType).getName());
		        } else {
		            log.info("Open command cancelled by user.");
		        }
			}
		});
		returnPanel.add(inputField, BorderLayout.CENTER);
		returnPanel.add(chooser, BorderLayout.EAST);
		return returnPanel;
	}
	private void setErrorsOnly(boolean selected){
		this.errorsOnly = selected;
	}
	protected boolean getErrorsOnly(){
		return this.errorsOnly;
	}
	private void setCart(boolean selected) {
		this.cart = selected;
	}
	protected boolean getCart(){
		return this.cart;
	}
	protected File getFile(String fileType) {
		switch(fileType){
		case TEST_FILE:
			return this.testFile;
		case DATABASE:
			return this.database;
		}
		return null;
	}
	protected void setFile(File selectedFile, String fileType) {
		switch(fileType){
		case TEST_FILE:
			this.testFile = selectedFile;
			break;
		case DATABASE:
			this.database = selectedFile;
			break;
		}
	}
}
