package com.diplomat.log;

import com.diplomat.pages.MainWindow;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class TxtAreaAppender extends AppenderBase<ILoggingEvent> {

	@Override
	protected void append(ILoggingEvent event) {
		String currentString = MainWindow.getMainText();
		String newString = currentString.length() > 0 ? currentString + "\r\n" + event : event.toString();
		MainWindow.setMainText(newString);
	}
	
}
