package com.hortonworks.mayo.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hortonworks.mayo.model.Annotation;
import com.hortonworks.mayo.services.AnnotationService;

@Controller
@RequestMapping("/annotations")
public class AnnotationsController {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private AnnotationService annotationService;

	@RequestMapping(value = "{title}", method = RequestMethod.GET)
	public @ResponseBody
	Annotation getAnnotationInJSON(@PathVariable String title) {
		logger.info("Processing request for title: " + title);
		Annotation annotation = new Annotation();
		try {
			annotation = annotationService.getAnnotationByTitle(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return annotation;

	}

	public AnnotationService getAnnotationService() {
		return annotationService;
	}

	public void setAnnotationService(AnnotationService annotationService) {
		this.annotationService = annotationService;
	}
}
