package com.hortonworks.mayo.services;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.hortonworks.mayo.model.Annotation;

@ContextConfiguration
public class AnnotationServiceTest extends AbstractJUnit4SpringContextTests {

	@Autowired
	AnnotationService annotationService;

	String title = "Mayo Clinic";

	@Test
	public void testRetrieve() {
		try {
			Annotation annotation = annotationService
					.getAnnotationByTitle(title);
			if (annotation != null)
				Assert.assertTrue(annotation != null
						&& annotation.getAnnotations().length() > 0);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
