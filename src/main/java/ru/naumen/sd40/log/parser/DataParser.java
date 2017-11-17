package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;



public interface DataParser {
	
	public void parseData(TimeParserInterface time, HashMap<Long, DataSet> data, String file) throws  IOException, ParseException;

}
