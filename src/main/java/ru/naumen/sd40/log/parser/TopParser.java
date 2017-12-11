package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * Top output parser
 * @author dkolmogortsev
 *
 */
@Component
public class TopParser implements DataParser<TopData>
{


    private Pattern cpuAndMemPattren = Pattern
            .compile("^ *\\d+ \\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ +\\S+ \\S+ +(\\S+) +(\\S+) +\\S+ java");
 
    public void parseData(TopData data, String line) throws IOException, ParseException
    {
    	
        if (data != null)
        {
            //get la
            Matcher la = Pattern.compile(".*load average:(.*)").matcher(line);
            if (la.find())
            {
                data.addLa(Double.parseDouble(la.group(1).split(",")[0].trim()));
                return;
            }

            //get cpu and mem
            Matcher cpuAndMemMatcher = cpuAndMemPattren.matcher(line);
            if (cpuAndMemMatcher.find())
            {
                data.addCpu(Double.valueOf(cpuAndMemMatcher.group(1)));
                data.addMem(Double.valueOf(cpuAndMemMatcher.group(2)));
                return;
            }
        }
    }


}
