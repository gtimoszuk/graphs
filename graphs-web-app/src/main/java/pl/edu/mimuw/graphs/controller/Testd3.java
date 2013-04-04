package pl.edu.mimuw.graphs.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Just a test class to be removed in future
 * 
 * @author gtimoszuk
 * 
 */
@Controller
public class Testd3 {

	@RequestMapping(value = "/testd3", method = RequestMethod.GET)
	public ModelAndView defaultAddress(HttpServletRequest request) {
		return new ModelAndView("test/testd3", new HashMap<String, String>());
	}
}
