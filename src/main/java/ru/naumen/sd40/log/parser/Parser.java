package ru.naumen.sd40.log.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.naumen.perfhouse.influx.InfluxDAO;
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
	private SdngSave sdngSave;
	private GCSave gcSave;
	private TopSave topSave;
	
	@Autowired
	public Parser(InfluxDAO influxDAO, ActionDoneParser actionDone, ErrorParser errorParser, TopParser topParser, GCParser gcParser, 
				  SdngSave sdngSave, GCSave gcSave, TopSave topSave)
	{
		this.influxDAO = influxDAO;
		this.actionDone = actionDone;
		this.errorParser = errorParser;
		this.topParser = topParser;
		this.gcParser = gcParser;
		this.sdngSave = sdngSave;
		this.gcSave = gcSave;
		this.topSave = topSave;
	}
	
    public void DoParser(String nameDB, String typePars, String pathLog, String timeZona, Boolean trace) throws IOException, ParseException
    {
    	SaveDataParser storage = new SaveDataParser(influxDAO);
    	
        //storage.connect(nameDB, trace, );
        
        String log = pathLog;
       
        DataCreator dataCreate;
        TimeParser timeParser;
        DataParser dataParser;
        Saver saver;

        String mode = typePars;      
        switch (mode)
        {
        case "sdng":
            //Parse sdng
        	{
        		dataCreate = new SdngCreator();
        		timeParser = new TimeParserImpl(timeZona);
        		dataParser = new SdngParser();
        		saver = sdngSave;
        	}
            break;
        case "gc":
            //Parse gc log
        	{
        		dataCreate = new GCDataCreator();
        		timeParser = new GCTimeParser(timeZona);
        		dataParser = gcParser;
        		saver = gcSave;
        	}
            break;
        case "top":
        	{
        		dataCreate = new TopDataCreator();
        		timeParser = new TopTimeParser(timeZona, log);
        		dataParser = topParser;
        		saver = topSave;
        	}
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode);
        }
        
        storage.connect(nameDB, trace, saver, dataCreate);
      
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