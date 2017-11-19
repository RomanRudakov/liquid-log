package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;

public class ComplexParser implements DataParser {
	
	private DataParser[] parserMass;
	
	public ComplexParser(DataParser ... parserMass)
	{
		this.parserMass = parserMass;
	}
	
	public void parseData(DataSet data, String line) throws  IOException, ParseException
    {
    	for(DataParser parser: parserMass)
    	{
    		parser.parseData(data, line);
    	}
    }

}
