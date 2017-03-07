package com.diplomat.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.diplomat.main.Main.*;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MainWindow.class);
	private PreferencePanel preferences;
	private static JTextArea mainTextArea = new JTextArea();

	public MainWindow() {
		super();
		setBounds(new Rectangle(0, 0, 600, 400));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Diplomat Standalone");
		preferences = new PreferencePanel();
		getContentPane().add(preferences, BorderLayout.NORTH);
		getContentPane().add(mainScroll(), BorderLayout.CENTER);
		getContentPane().add(executeButton(),BorderLayout.SOUTH);
	}
	private JScrollPane mainScroll(){
		mainTextArea.setEnabled(false);
		mainTextArea.setDisabledTextColor(Color.BLACK);
		mainTextArea.setFont(new Font("monospaced", Font.PLAIN, 14));
		return new JScrollPane(mainTextArea);
	}
	private JButton executeButton(){
		JButton execute = new JButton("Execute");
		execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				try {
					String databasePath = preferences.getFile(PreferencePanel.DATABASE).getAbsolutePath();
					String testFilePath = preferences.getFile(PreferencePanel.TEST_FILE).getAbsolutePath();
					boolean isCartCalc = !preferences.getCart();
					log.info("Started Test with params: \r\nDatabase: " + databasePath + "\r\nTest File: " + testFilePath + "\r\nIs Cart Calculation: " + isCartCalc);
					runTests(databasePath, testFilePath, isCartCalc);
				} catch (Exception e) {
					log.error("Something went wrong, please make sure files are correct", e);
				}
			}
		});
		return execute;
	}
	public static void setMainText(String text){
		mainTextArea.setText(text);
	}
	public static String getMainText(){
		return mainTextArea.getText();
	}
}
