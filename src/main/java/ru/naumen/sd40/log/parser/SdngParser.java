package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;

public class SdngParser implements DataParser {
	
	public void parseData(DataSet data, String line) throws  IOException, ParseException
    {
    	data.parseLineError(line);
    	data.parseLineAction(line);
    }

}
