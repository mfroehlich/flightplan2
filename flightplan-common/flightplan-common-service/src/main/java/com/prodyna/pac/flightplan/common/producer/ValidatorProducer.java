/**
 * 
 */
package com.prodyna.pac.flightplan.common.producer;

import javax.enterprise.inject.Produces;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ValidatorProducer {

    @Produces
    public Validator createValidator() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator;
    }
}
