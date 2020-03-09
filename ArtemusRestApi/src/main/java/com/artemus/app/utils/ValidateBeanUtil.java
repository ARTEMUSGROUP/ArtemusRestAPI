package com.artemus.app.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.artemus.app.model.request.Voyage;

public class ValidateBeanUtil {

	// Step 1
	static ValidatorFactory factory;
	static Validator validator;

	public static void buildDefaultValidatorFactory() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		factory.close();
	}

	public static StringBuffer getConstraintViolationMsgForVoyage(Voyage objBean) {
		// Step 2
		Set<ConstraintViolation<Voyage>> violations = validator.validate(objBean);
		// Step 3
		StringBuffer objString = new StringBuffer();
		for (ConstraintViolation<Voyage> violation : violations) {
			if (objString.length()>0) {
				objString.append(" , ");
			}
			objString.append(violation.getMessage());
		}
		
		return objString;
	}

}
