package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import org.influxdb.dto.BatchPoints;

import ru.naumen.perfhouse.influx.InfluxDAO;
import ru.naumen.sd40.log.parser.GCParser.GCTimeParser;

/**
 * Created by doki on 22.10.16.
 */
public class App
{
    /**
     * 
     * @param args [0] - sdng.log, [1] - gc.log, [2] - top, [3] - dbName, [4] timezone
     * @throws IOException
     * @throws ParseException
     */
    public static void DoParser(String nameDB, String typePars, String pathLog, String timeZona, Boolean trace) throws IOException, ParseException
    {
        
    	String influxDb = nameDB;

        influxDb = influxDb.replaceAll("-", "_");
        

        InfluxDAO storage = null;
        if (influxDb != null)
        {
            storage = new InfluxDAO(System.getProperty("influx.host"), System.getProperty("influx.user"),
                    System.getProperty("influx.password"));
            storage.init();
            storage.connectToDB(influxDb);
        }
        InfluxDAO finalStorage = storage;
        String finalInfluxDb = influxDb;
        BatchPoints points = null;

        if (storage != null)
        {
            points = storage.startBatchPoints(influxDb);
        }

        String log = pathLog;

        HashMap<Long, DataSet> data = new HashMap<>();

        TimeParser timeParser = new TimeParser();
        GCTimeParser gcTime = new GCTimeParser();
        
        timeParser = new TimeParser(timeZona);
        gcTime = new GCTimeParser(timeZona);
        

        String mode = typePars;
        switch (mode)
        {
        case "sdng":
            //Parse sdng
        	{
        		ActionDoneParser action = new ActionDoneParser();
                action.parseData(timeParser, data, log);
        	}
            break;
        case "gc":
            //Parse gc log
        	{
        		GCParser action = new GCParser();
        		action.parseData(gcTime, data, log);
        	}
            break;
        case "top":
            TopParser topParser = new TopParser(log, data);
            
            topParser.configureTimeZone(timeZona);
            
            //Parse top
            topParser.parse();
            break;
        default:
            throw new IllegalArgumentException(
                    "Unknown parse mode! Availiable modes: sdng, gc, top. Requested mode: " + mode);
        }

        if (trace)
        {
            System.out.print("Timestamp;Actions;Min;Mean;Stddev;50%%;95%%;99%%;99.9%%;Max;Errors\n");
        }
        BatchPoints finalPoints = points;
        data.forEach((k, set) ->
        {
            ActionDoneParser dones = set.getActionsDone();
            dones.calculate();
            ErrorParser erros = set.getErrors();
            if (trace)
            {
                System.out.print(String.format("%d;%d;%f;%f;%f;%f;%f;%f;%f;%f;%d\n", k, dones.getCount(),
                        dones.getMin(), dones.getMean(), dones.getStddev(), dones.getPercent50(), dones.getPercent95(),
                        dones.getPercent99(), dones.getPercent999(), dones.getMax(), erros.getErrorCount()));
            }
            if (!dones.isNan())
            {
                finalStorage.storeActionsFromLog(finalPoints, finalInfluxDb, k, dones, erros);
            }

            GCParser gc = set.getGc();
            if (!gc.isNan())
            {
                finalStorage.storeGc(finalPoints, finalInfluxDb, k, gc);
            }

            TopData cpuData = set.cpuData();
            if (!cpuData.isNan())
            {
                finalStorage.storeTop(finalPoints, finalInfluxDb, k, cpuData);
            }
        });
        storage.writeBatch(points);
    }
}