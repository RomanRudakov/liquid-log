package ru.naumen.sd40.log.parser;

import static ru.naumen.sd40.log.parser.NumberUtils.getSafeDouble;
import static ru.naumen.sd40.log.parser.NumberUtils.roundToTwoPlaces;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class GCParser implements DataParser
{
    public final static class GCTimeParser implements TimeParserInterface
    {
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                new Locale("ru", "RU"));

        private static final Pattern PATTERN = Pattern
                .compile("^(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}\\+\\d{4}).*");

        public GCTimeParser()
        {
            DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        }

        public GCTimeParser(String timeZone)
        {
            DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(timeZone));
        }

        public long parseTime(String line) throws ParseException
        {
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find())
            {
                Date parse = DATE_FORMAT.parse(matcher.group(1));
                return parse.getTime();
            }
            return 0L;
        }
    }

    private DescriptiveStatistics ds = new DescriptiveStatistics();

    private Pattern gcExecutionTime = Pattern.compile(".*real=(.*)secs.*");

    public double getCalculatedAvg()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMean()));
    }

    public long getGcTimes()
    {
        return ds.getN();
    }

    public double getMaxGcTime()
    {
        return roundToTwoPlaces(getSafeDouble(ds.getMax()));
    }

    public boolean isNan()
    {
        return getGcTimes() == 0;
    }

    public void parseLine(String line)
    {
        Matcher matcher = gcExecutionTime.matcher(line);
        if (matcher.find())
        {
            ds.addValue(Double.parseDouble(matcher.group(1).trim().replace(',', '.')));
        }
    }
    
    public void parseData(TimeParserInterface gcTime, HashMap<Long, DataSet> data, String log) throws  IOException, ParseException
    {
    	try (BufferedReader br = new BufferedReader(new FileReader(log)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                long time = ((GCTimeParser)gcTime).parseTime(line);

                if (time == 0)
                {
                    continue;
                }

                int min5 = 5 * 60 * 1000;
                long count = time / min5;
                long key = count * min5;
                data.computeIfAbsent(key, k -> new DataSet()).parseGcLine(line);
            }
        }
    }
}
