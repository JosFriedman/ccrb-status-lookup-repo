package gov.nyc.doitt.ccrb.statuslookup.service;

public interface DoITTCaptchaService {
	public String validateCaptcha (String usersAnswer, boolean forceCaptchaValidation);
}
