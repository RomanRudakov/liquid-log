package ru.naumen.sd40.log.parser;

import java.text.ParseException;

public interface TimeParser {
	
	public long parseTime(String line) throws ParseException;

}
