package com.artemus.app.awsmail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.ws.rs.core.Application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class SendMail extends Application {

	static Logger logger = LogManager.getLogger();

	public static final String PROPERTIES_FILE = "Email.properties";
	public static Properties properties = new Properties();

	// Replace sender@example.com with your "From" address.
	// This address must be verified with Amazon SES.
	static final String FROM = "artemusedi@gmail.com";

	// Replace recipient@example.com with a "To" address. If your account
	// is still in the sandbox, this address must be verified.
	// static String TO = "akshay.tikone@giantleapsystems.com";

	// The configuration set to use for this email. If you do not want to use a
	// configuration set, comment the following variable and the
	// .withConfigurationSetName(CONFIGSET); argument below.
	// static final String CONFIGSET = "ConfigSet";

	// The subject line for the email.
	static String SUBJECT = "Artemus API JSON Response for ";

	/*
	 * // The HTML body for the email. static final String HTMLBODY =
	 * "<h1>Artemus Amazon SES test (AWS SDK for Java)</h1>" +
	 * "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>";
	 * 
	 * // The email body for recipients with non-HTML email clients. static final
	 * String TEXTBODY = "This email was sent through Amazon SES " +
	 * "using the AWS SDK for Java.";
	 */

	public void sendMail(String scacCode, String apiTag, int errorFlag,String apiName,String errormessage) throws IOException {
		String HTMLBODY = "";
		String TO = new String("");
		String api=new String("");
		String subject=new String("");
		String TEXTBODY = new String("");
		api=apiTag;
		// Read Properties File
		properties = readProperties();
		TO = properties.getProperty(scacCode.toUpperCase());
		StringTokenizer emailAddresses =  
	             new StringTokenizer(TO, ","); 
		subject = SUBJECT + api;
		TEXTBODY = "";
		if (errorFlag == 1) {
			HTMLBODY = "<h1>"+api + " is Rejected for "+api+" "+apiName+"</h1>"
					+"<p> Error Message : "+errormessage;
		} else {
			HTMLBODY = "<h1>"+api + " is Accepted for "+api+" "+apiName+"</h1>";
		}
		logger.info("Mail representative :Subject : " + subject + " TEXTBODY: " + TEXTBODY + " HTMLBODY: " + HTMLBODY
				+ " scacCode emailAddresses: " + scacCode.toUpperCase()+" " +emailAddresses);
		System.out.println("Mail representative :Subject : " + subject + " TEXTBODY: " + TEXTBODY + " HTMLBODY: "
				+ HTMLBODY + " scacCode emailAddresses: "+ scacCode.toUpperCase()+" " +emailAddresses);
		 while (emailAddresses.hasMoreTokens()) {
			 try {
					AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
							// Replace US_WEST_2 with the AWS Region you're using for
							// Amazon SES.
							.withRegion(Regions.US_EAST_1).build();
					SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(emailAddresses.nextToken()))
							.withMessage(new Message()
									.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(HTMLBODY))
											.withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
									.withSubject(new Content().withCharset("UTF-8").withData(subject)))
							.withSource(FROM);
					// Comment or remove the next line if you are not using a
					// configuration set
					// .withConfigurationSetName(CONFIGSET);
					client.sendEmail(request);
					System.out.println("Email sent!");
					logger.info("Email sent!");
				} catch (Exception ex) {
					System.out.println("The email was not sent. Error message: " + ex.getMessage());
					logger.info("The email was not sent. Error message: " + ex.getMessage());
				}
		 }
	           
		
	}

	private Properties readProperties() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				// TODO Add your custom fail-over code here
				e.printStackTrace();
			}
		}
		return properties;
	}

}
