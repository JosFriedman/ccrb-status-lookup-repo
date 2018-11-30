package gov.nyc.doitt.ccrb.statuslookup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("doittCaptcha")
public class DoITTCaptchaServiceImpl implements DoITTCaptchaService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Value("${ccrbsl.captchaurl}")
	private String captchaurl;

	@Value("${ccrbsl.captchausername}")
	private String captchausername;

	@Value("${ccrbsl.captchapassword}")
	private String captchapassword;
	
	
	private static final String ALGORITHM = "HmacSHA1";	
	
	@Value("${ccrbsl.disablecaptcha}")
	private boolean disablecaptcha;
	
	public String validateCaptcha (String usersAnswer, boolean forceCaptchaValidation) {
		logger.info("disablecaptcha: " + disablecaptcha);
		
		//if captcha is disabled, just return true
		if (disablecaptcha && ! forceCaptchaValidation) {
			return "true";
		}
		
		String captchasignature;
		
		String sigParams;
		
		sigParams = getSignatureParams(captchausername, usersAnswer);
		
		captchasignature = getSignature(sigParams, captchapassword);

		logger.info("running captcha");
		
		CloseableHttpClient captchaClient = HttpClients.createDefault();
		String errorMessage = null;
		String captchaResponseBody = null;
		int captchaResponseCode = 0;
		
		try {
			HttpPost captchaPost = new HttpPost(captchaurl);
		
			List<NameValuePair> captchaParams = new ArrayList<NameValuePair>();
			captchaParams.add(new BasicNameValuePair("userName", captchausername));
			captchaParams.add(new BasicNameValuePair("user_answer", usersAnswer));
			captchaParams.add(new BasicNameValuePair("signature", captchasignature));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(captchaParams,Consts.UTF_8);
			
			captchaPost.setEntity(entity);

			CloseableHttpResponse captchaResponse=captchaClient.execute(captchaPost); 
			captchaResponseBody = EntityUtils.toString(captchaResponse.getEntity());
			captchaResponseCode = captchaResponse.getStatusLine().getStatusCode();

			if (logger.isDebugEnabled()) {
				logger.debug("Captcha URL:" + captchaurl);
				logger.debug("Captcha user_answer:" + usersAnswer);
				logger.debug("Captcha userName:" + captchausername);
				logger.debug("Capture userPassword:" + captchapassword);
				logger.debug("Captcha signature:" + captchasignature);
				logger.debug("Captcha response body:" + captchaResponseBody);
				logger.debug("Captcha response code:" + captchaResponseCode);
			}
			
			if (captchaResponseCode != HttpStatus.SC_OK) {
				// error
				errorMessage = "captcha error: http status code = " + captchaResponseCode + ", response = " + captchaResponseBody;
			} else if (!"true".equals(captchaResponseBody)) {
				// error -- http call may succeed (return SC_OK) from a technical point of view but Captcha logic check
				// may fail in which case captchaResponseBody is not set to "true"
				errorMessage = "captcha error: " + captchaResponseBody;
				logger.error(errorMessage);
			} else {
				errorMessage = "true";
			}
		}
		catch (ClientProtocolException e) {
			errorMessage = "ClientProtocolException: " + e.toString();
			logger.error("Captcha error: " + errorMessage, e);
		}
		catch (IOException e) {
			errorMessage = "IOException: " + e.toString();
			logger.error("Captcha error: " + errorMessage, e);
		}
		catch (Exception e) {
			errorMessage = "Exception:" + e.toString();
			logger.error("Captcha error: " + errorMessage, e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Returning:" + errorMessage);
		}
		return errorMessage;
	}
	
	// This method creates a signature parameters string by appending the URL parameters
	// along with the provisioned CAPTCHA Service Account name and usersAnswer.
	// The values to append must stay in this order.
    private static String getSignatureParams(String captchausername, String usersAnswer) {
        StringBuilder signatureParams = new StringBuilder();
        signatureParams.append("POST");
        signatureParams.append("/doittcaptchaservice/validate.htm");
        signatureParams.append(captchausername);
        signatureParams.append(usersAnswer);
        signatureParams.append("2");
        return signatureParams.toString();
    }
    
	
	// This method takes the signature parameters (value) and the provisioned CAPTCHA account's password (key)
	// and generates a hashed signature value.
	private static String getSignature(String value, String key) {
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, ALGORITHM);

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            // Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
