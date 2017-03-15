package com.diplomat.log;

import com.diplomat.pages.MainWindow;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class TxtAreaAppender extends AppenderBase<ILoggingEvent> {
	private static final String ERROR_STRING = "****ERROR****";
	private static final String RESULT = "Results:";
	public static String lastResult;
	public static int resultCounter = 0;
	public static int errorCounter = 0;

	@Override
	protected void append(ILoggingEvent event) {
		if (event.toString().contains(RESULT)){
			resultCounter++;
			lastResult = MainWindow.isCart() ? lastResult + "\r\n" + event.toString() : event.toString();
		}
		else if (event.toString().contains(ERROR_STRING)) {
			errorCounter++;
			MainWindow.appendMainText("\r\n" + lastResult + "\r\n" + event);
			lastResult = MainWindow.isCart() ? "" : lastResult;
		}
		else {
			MainWindow.appendMainText("\r\n" + event);
		}
	}
}
