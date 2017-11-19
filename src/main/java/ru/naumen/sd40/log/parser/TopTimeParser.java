package ru.naumen.sd40.log.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopTimeParser implements TimeParser {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH:mm");
	
	private Pattern timeRegex = Pattern.compile("^_+ (\\S+)");
	
	private String dataDate;
	
	private long time = 0L;
	
	public TopTimeParser(String timeZone, String file)
	{
		Matcher matcher = Pattern.compile("\\d{8}|\\d{4}-\\d{2}-\\d{2}").matcher(file);
        if (!matcher.find())
        {
            throw new IllegalArgumentException();
        }
        this.dataDate = matcher.group(0).replaceAll("-", "");
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
	}

	public long parseTime(String line) throws ParseException
	{
        Matcher matcher = timeRegex.matcher(line);
        if (matcher.find())
        {
            time = sdf.parse(dataDate + matcher.group(1)).getTime();
            return time;
        }
        return time;
	}

}
