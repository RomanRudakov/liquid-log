package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;



public interface DataParser<T extends DataSet> {
	
	public void parseData(T data, String line) throws  IOException, ParseException;

}
