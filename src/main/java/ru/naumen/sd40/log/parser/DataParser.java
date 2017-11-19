package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;



public interface DataParser {
	
	public void parseData(DataSet data, String line) throws  IOException, ParseException;

}
