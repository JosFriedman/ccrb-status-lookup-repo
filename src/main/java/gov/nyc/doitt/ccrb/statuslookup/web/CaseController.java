package gov.nyc.doitt.ccrb.statuslookup.web;

import gov.nyc.doitt.ccrb.statuslookup.domain.CaseEntity;
import gov.nyc.doitt.ccrb.statuslookup.service.CaseManager;
import gov.nyc.doitt.ccrb.statuslookup.service.DoITTCaptchaService;
import gov.nyc.doitt.ccrb.statuslookup.service.EtlStatusManager;
import gov.nyc.doitt.ccrb.statuslookup.util.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class CaseController {

 	protected final Log logger = LogFactory.getLog(getClass());
    
    @Autowired
    private EtlStatusManager etlStatusManager;
    
 	@Autowired
    private CaseManager caseManager;
	
	@Autowired
	private DoITTCaptchaService doittCaptcha;
	
	@Value("${ccrbsl.agencysite}")
	private String agencysite;
	
	@Value("${ccrbsl.captchajsurl}")
	private String captchajsurl;
	
	@Value("${ccrbsl.captchausername}")
	private String captchausername;

	@Value("${ccrbsl.nycgovprefix}")
	private String nycgovprefix;
	
	@Value("${ccrbsl.formpath}")
	private String formpath;
	
	@Value("${ccrbsl.formactionpath}")
	private String formactionpath;
	
    @RequestMapping(value="/results", method = RequestMethod.POST)
    public ModelAndView displaySpecificResults(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	if (logger.isDebugEnabled()) {
    		logger.debug("query string:" + request.getQueryString());
    		logger.debug("remote user:" + request.getRemoteUser());
    	}
    	
    	try {
	        Integer case_id = null;
	        //check that case id is present and a valid number
	        if (request.getParameter("case_id").equals(null) || ! Utils.isInteger(request.getParameter("case_id"))) {
	        	logger.error("invalid case id"+ request.getParameter("case_id"));
	        	return new ModelAndView("redirect:/?error=" + Constants.CASE_ID_ERROR_CODE + "&case_id=" + request.getParameter("case_id"));
	        } else {
	        	//set case_id
	        	case_id = Integer.parseInt(request.getParameter("case_id"));
	        	logger.info("valid case id:" + case_id);
	        }
	        //Validate Captcha
		    String validCaptcha = doittCaptcha.validateCaptcha(request.getParameter("g-recaptcha-response"),false);
		        		
		    //First, check ETL status    
		    if (this.etlStatusManager.getEtlStatus().getRunning().equals(1)) {
	    			//ETL is in progress - do a redirect
		    		logger.error("etl is running");
		    		if (logger.isDebugEnabled()) { 
		    			logger.debug("etl status: " + this.etlStatusManager.getEtlStatus().getRunning());
		    		}
					return new ModelAndView("redirect:/?error=" + Constants.ETL_ERROR_CODE + "&case_id=" + case_id);
	    	} else {
	    			//ETL not in progress, check captcha
	    			logger.info("etl not running, validating captcha");
	    			if (validCaptcha.equals("true")) {
	    				//Captcha is valid - lookup case
	    				logger.info("valid captcha, proceeding to lookup case");

	    				Map<String, Object> myModel = new HashMap<String, Object>();
	    				myModel.put("ccrbsl_agencysite", this.agencysite);
	    		    	myModel.put("ccrbsl_nycgovprefix", this.nycgovprefix);
	    		    	myModel.put("ccrbsl_formpath", this.formpath);
	
	    				
	    				CaseEntity myCase = this.caseManager.getCase(case_id);
	    				if(myCase != null) {
	    					//Case is found
	    					myModel.put("case",myCase);
	    					logger.info("case found:"+ case_id);
	    				} else {
	    					//Case isn't found
	    					myModel.put("notfound", case_id);
	    					logger.info("case not found:"+ case_id);
	    				}
	    				
	    				return new ModelAndView("viewcase", "model", myModel);
	    		    } else {
	    		    	//Captcha is invalid - redirect to bad captcha page w/case_id in url query
	    		    	logger.error("captcha error" + validCaptcha);
	    		    	return new ModelAndView("redirect:/?error=" + Constants.CAPTCHA_ERROR_CODE + "&case_id=" + case_id);
	    		    }
	    	}
    	} catch (Exception e) {
    		logger.error("CaseController exception" + e.toString());
    		return new ModelAndView("redirect:/?error=" + Constants.ETL_ERROR_CODE);
    	}
   }
    
    @RequestMapping(value="/results", method = RequestMethod.GET)
    public ModelAndView showInvalidRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	logger.error("Get request made to results page");
        //Get not supported
		return new ModelAndView("redirect:/?error=" + Constants.GET_ERROR_CODE);
		
    }
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView showFormRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	Map<String, Object> myModel = new HashMap<String, Object>();
    	
    	myModel.put("ccrbsl_agencysite", this.agencysite);
    	myModel.put("ccrbsl_captchajsurl", this.captchajsurl);
    	myModel.put("ccrbsl_captchausername", this.captchausername);
    	myModel.put("ccrbsl_nycgovprefix", this.nycgovprefix);
    	myModel.put("ccrbsl_formactionpath", this.formactionpath);
        
		return new ModelAndView("caselookup-form", "model", myModel);
		
    }
    
}
