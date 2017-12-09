package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;

public class SdngParser implements DataParser<SdngData> {
	
	private ComplexParser parser = new ComplexParser(new ActionDoneParser(), new ErrorParser());
	
	public void parseData(SdngData data, String line) throws  IOException, ParseException
	{
		parser.parseData(data, line);
	}

}
