package com.diplomat.pages;

import static com.diplomat.main.Main.runTests;

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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.diplomat.log.TxtAreaAppender;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(MainWindow.class);
	private PreferencePanel preferences;
	private static JTextArea mainTextArea = new JTextArea();
	private static JScrollPane mainScroll;

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
		mainTextArea.setDisabledTextColor(Color.BLACK);
		mainTextArea.setFont(new Font("monospaced", Font.PLAIN, 14));
		mainScroll = new JScrollPane(mainTextArea);
		return mainScroll;
	}
	private JButton executeButton(){
		JButton execute = new JButton("Execute");
		execute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent action) {
				try {
					final String databasePath = preferences.getFile(PreferencePanel.DATABASE).getAbsolutePath();
					final String testFilePath = preferences.getFile(PreferencePanel.TEST_FILE).getAbsolutePath();
					final boolean isCartCalc = !preferences.getCart();
					log.info("Started Test with params: \r\nDatabase: " + databasePath + "\r\nTest File: " + testFilePath + "\r\nIs Cart Calculation: " + isCartCalc);
					new Thread() {
						public void run() {
							runTests(databasePath, testFilePath, isCartCalc);
							appendMainText("Final Results:\r\nNumber Of Tests: " + TxtAreaAppender.resultCounter + "\r\nErrors: " + TxtAreaAppender.errorCounter);
							TxtAreaAppender.resultCounter = 0;
							TxtAreaAppender.errorCounter = 0;
						}
					}.start();
				} catch (Exception e) {
					log.error("Something went wrong, please make sure files are correct", e);
				}
			}
		});
		return execute;
	}
	public static void setMainText(String text){
		mainTextArea.setText(text);
		JScrollBar vertical = mainScroll.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}
	public static void appendMainText(String text){
		mainTextArea.append(text);
		JScrollBar vertical = mainScroll.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}
	public static String getMainText(){
		return mainTextArea.getText();
	}
}
