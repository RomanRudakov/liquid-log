package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.perfhouse.influx.InfluxDAOImpl;
import ru.naumen.sd40.log.parser.GCParser.GCTimeParser;

/**
 * Created by doki on 22.10.16.
 */
@Component
public class Parser
{
    /**
     * 
     * @param args [0] - sdng.log, [1] - gc.log, [2] - top, [3] - dbName, [4] timezone
     * @throws IOException
     * @throws ParseException
     */
	private InfluxDAO influxDAO;
	private ActionDoneParser actionDone;
	private ErrorParser errorParser;
	private TopParser topParser;
	private GCParser gcParser;
	
	@Autowired
	public Parser(InfluxDAO influxDAO, ActionDoneParser actionDone, ErrorParser errorParser, TopParser topParser, GCParser gcParser)
	{
		this.influxDAO = influxDAO;
		this.actionDone = actionDone;
		this.errorParser = errorParser;
		this.topParser = topParser;
		this.gcParser = gcParser;
	}
	
    public void DoParser(String nameDB, String typePars, String pathLog, String timeZona, Boolean trace) throws IOException, ParseException
    {
    	SaveDataParser storage = new SaveDataParser(influxDAO);
    	
        storage.connect(nameDB, trace);
        
        String log = pathLog;
       
        TimeParser timeParser;
        DataParser dataParser;

        String mode = typePars;      
        switch (mode)
        {
        case "sdng":
            //Parse sdng
        	{
        		timeParser = new TimeParserImpl(timeZona);
        		dataParser = new ComplexParser(errorParser, actionDone);
        	}
            break;
        case "gc":
            //Parse gc log
        	{
        		timeParser = new GCTimeParser(timeZona);
        		dataParser = gcParser;
        	}
            break;
        case "top":
        	{
        		timeParser = new TopTimeParser(timeZona, log);
        		dataParser = topParser;
        	}
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode);
        }
      
        try (BufferedReader br = new BufferedReader(new FileReader(log)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                long time = timeParser.parseTime(line);

                if (time == 0)
                {
                    continue;
                }

                int min5 = 5 * 60 * 1000;
                long count = time / min5;
                long key = count * min5;

               dataParser.parseData(storage.get(key), line);
            }
        }
        storage.finalSave();
        
    }
}