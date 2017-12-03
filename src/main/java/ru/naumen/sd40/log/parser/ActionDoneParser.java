package ru.naumen.sd40.log.parser;

import java.io.IOException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

/**
 * Created by doki on 22.10.16.
 */
@Component
public class ActionDoneParser implements DataParser
{
    
    Pattern doneRegEx = Pattern.compile("Done\\((\\d+)\\): ?(.*?Action)");

    public void parseData(DataSet data, String line) throws  IOException, ParseException
    {
    	
        Matcher matcher = doneRegEx.matcher(line);
        ActionDoneData actionData = data.getActionsData();
        

        if (matcher.find())
        {
            String actionInLowerCase = matcher.group(2).toLowerCase();
            if (actionData.getExcludedActions().contains(actionInLowerCase))
            {
                return;
            }

            actionData.getTimes().add(Integer.parseInt(matcher.group(1)));
            if (actionInLowerCase.equals("addobjectaction"))
            {
            	actionData.incrementAddObjectActions();
            }
            else if (actionInLowerCase.equals("editobjectaction"))
            {
                actionData.incrementEditObjectsActions();
            }
            else if (actionInLowerCase.matches("(?i)[a-zA-Z]+comment[a-zA-Z]+"))
            {
                actionData.incrementCommentActions();
            }
            else if (!actionInLowerCase.contains("advlist")
                    && actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+List[a-zA-Z]+"))

            {
                actionData.incrementGetListActions();
            }
            else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+Form[a-zA-Z]+"))
            {
                actionData.incrementGetFormActions();
            }
            else if (actionInLowerCase.matches("(?i)^([a-zA-Z]+|Get)[a-zA-Z]+DtObject[a-zA-Z]+"))
            {
                actionData.incrementGetDtObjectActions();
            }
            else if (actionInLowerCase.matches("(?i)[a-zA-Z]+search[a-zA-Z]+"))
            {
                actionData.incrementSearchActions();
            }
            else if (actionInLowerCase.equals("getcatalogsactions"))
            {
                actionData.incrementCatalogsActions();
            }

        }
    }

}
