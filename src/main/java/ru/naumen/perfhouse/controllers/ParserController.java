package ru.naumen.perfhouse.controllers;

import java.io.IOException;


import java.text.ParseException;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import ru.naumen.sd40.log.parser.Parser;

@Controller
public class ParserController 
{
	@Inject
	private Parser parserClass;

	@RequestMapping("/parserLog")
	public  ModelAndView index()
	{
		return new ModelAndView("parserLog");
	}

	
	@RequestMapping(path = "/parserLog/res")
	public ModelAndView res(@RequestParam("nameDB") String nameDB, @RequestParam("typePars") String typePars, @RequestParam("pathLog") String pathLog, 
			                @RequestParam("timeZona") String timeZona, @RequestParam(name = "trace", defaultValue = "false") Boolean trace) throws IOException, ParseException
	{
		parserClass.DoParser(nameDB, typePars, pathLog, timeZona, trace);
	    
	    return new ModelAndView("parsRes");
	}
	

}
