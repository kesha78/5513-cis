package ru.ifmo.cis.mrp.front.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "countValidator")
public class CountValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	String componentValue = value.toString();
	pattern = Pattern.compile("[0-9]");
	matcher = pattern.matcher(componentValue);
	
	if(!matcher.find()) {
	    FacesMessage message = new FacesMessage();
	    message.setSeverity(FacesMessage.SEVERITY_ERROR);
	    message.setSummary("Count is not valid.");
	    message.setDetail("Count is not valid.");
	    context.addMessage("userForm:Count", message);
	    throw new ValidatorException(message);
	}
    }
}