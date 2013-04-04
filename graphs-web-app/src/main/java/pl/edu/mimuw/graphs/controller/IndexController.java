package pl.edu.mimuw.graphs.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.mimuw.graphs.controller.util.AbstractGraphController;
import pl.edu.mimuw.graphs.exceptions.GraphsException;

@Controller
public class IndexController extends AbstractGraphController {

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public ModelAndView defaultAddress(HttpServletRequest request) throws GraphsException {
		ModelMap modelMap = createBaseModelMap();
		return new ModelAndView("index", modelMap);
	}
}
