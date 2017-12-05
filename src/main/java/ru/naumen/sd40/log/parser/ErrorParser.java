package ru.naumen.sd40.log.parser;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * Created by doki on 22.10.16.
 */
@Component
public class ErrorParser implements DataParser
{
    Pattern warnRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) WARN");
    Pattern errorRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) ERROR");
    Pattern fatalRegEx = Pattern.compile("^\\d+ \\[.+?\\] \\(.+?\\) FATAL");

    public void parseData(DataSet data, String line)
    {
    	ErrorData errorData = data.getErrors();
    	
        if (warnRegEx.matcher(line).find())
        {
            errorData.incrementWarnCount();
        }
        if (errorRegEx.matcher(line).find())
        {
            errorData.incrementErrorCount();
        }
        if (fatalRegEx.matcher(line).find())
        {
            errorData.incrementFatalCount();
        }
    }

    
}
