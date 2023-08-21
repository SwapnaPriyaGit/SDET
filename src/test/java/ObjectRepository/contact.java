package ObjectRepository;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class contact {
	
	@FindBy(xpath="//*[@title=\"Contact Us\"]")
	public static WebElement contactLink;
	
	@FindBy(xpath="//*[@title=\"Investor Relations\"]")
	public static WebElement investorLink;
	
	@FindBy(xpath="//*[@id=\"block-secondarymenublock\"]/div/div/div/div[2]/ul/li[5]/a")
	public static WebElement whyHCLLink;
	
	@FindBy(id="onetrust-accept-btn-handler")
	public static WebElement acceptCookies;
		
	@FindBy(id="edit-full-name")
	public static WebElement fullName;
	
	@FindBy(id="edit-email-address")
	public static WebElement businessEmailAddress;
	
	@FindBy(id="edit-organization")
	public static WebElement organization;
	
	@FindBy(id="edit-phone")
	public static WebElement phone;
	
	@FindBy(id="edit-country")
	public static WebElement countryList;
	
	@FindBy(id="edit-message-comments")
	public static WebElement comments;
	
	@FindBy(id="edit-query-type")
	public static WebElement relationship;
	
	@FindBy(id="edit-upload-multifile--label")
	public static WebElement fileUpload;
	
	@FindBy(id="edit-privacy-policy")
	public static WebElement privacyPolicy;
	
	@FindBy(id="edit-actions-submit")
	public static WebElement submit;
	
	@FindBy(id="edit-full-name-error")
	public static WebElement fullNameError;
	
	@FindBy(id="edit-email-address-error")
	public static WebElement EmailError;
	
	@FindBy(id="edit-phone-error")
	public static WebElement phoneError;
	
	@FindBy(id="edit-country-error")
	public static WebElement countryError;
	
	@FindBy(id="edit-query-type-error")
	public static WebElement relationError;
	
	@FindBy(id="edit-message-comments-error")
	public static WebElement commentsError;
	
	@FindBy(id="privacy_policy-error")
	public static WebElement privacyError;
	
	@FindBy(xpath="//*[@id=\"block-mainnavigationbt\"]/ul/li[8]/a")
	public static WebElement textLink;
	
	@FindBy(xpath="//*[@class=\"col-md-6 col-lg-6 col-xl-7\"]/p[1]")
	public static WebElement textComponent1;
	
	@FindBy(xpath="//*[@class=\"col-md-6 col-lg-6 col-xl-7\"]/p[2]")
	public static WebElement textComponent2;
	
	@FindBys(@FindBy(xpath="//*[@class=\"col-md-6 col-lg-6 col-xl-7\"]/ul/li"))
	public static List<WebElement> textComponent3;
	
	@FindBys(@FindBy(xpath="//*[@class=\"header-services-menu header-full-width-menu nav-item menu-item--expanded dropdown\"]/ul/li/ul/li"))
	public static List<WebElement> services;
	
	@FindBys(@FindBy(xpath="//*[@class=\\\"header-industries-menu header-full-width-menu nav-item menu-item--expanded dropdown\\\"]/ul/li"))
	public static List<WebElement> industires;
	
	@FindBys(@FindBy(xpath="//*[@class=\\\"header-about-menu nav-item menu-item--expanded dropdown\\\"]/ul/li"))
	public static List<WebElement> aboutUs;
	
	@FindBys(@FindBy(xpath="//*[@class=\\\"header-insights-menu nav-item menu-item--expanded dropdown\\\"]/ul/li"))
	public static List<WebElement> trendsAndInsights;
	
	@FindBys(@FindBy(xpath="//*[@class=\\\"header-careers-menu nav-item menu-item--expanded dropdown\\\"]/ul/li"))
	public static List<WebElement> careers;

}
