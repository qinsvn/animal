/**
 * 
 */
package com.ts.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ivy
 *
 */
@Controller
@RequestMapping("/templates")
public class PageController {

	@GetMapping("/{first}")
	public String to(@PathVariable("first") String first) {
		return first;
	}
	@GetMapping("/{first}/{second}")
	public String to(@PathVariable("first") String first,@PathVariable("second") String second) {
		return  String.format(	"%s/%s",first,second);
	}

	@GetMapping("/{first}/{second}/{third}")
	public String to(@PathVariable("first") String first,@PathVariable("second") String second,@PathVariable("third") String third) {
		return  String.format(	"%s/%s/%s",first,second,third);
	}
	@GetMapping("/{first}/{second}/{third}/{fourth}")
	public String to(@PathVariable("first") String first,@PathVariable("second") String second,@PathVariable("third") String third,@PathVariable("fourth") String fourth) {
		return  String.format(	"%s/%s/%s/%s",first,second,third,fourth);
	}
}
