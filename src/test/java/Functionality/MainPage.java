package Functionality;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import ObjectRepository.Elements;
import ObjectRepository.contact;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class MainPage {
	public WebDriver driver;
	WebDriverWait wait;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;
	
	@BeforeClass
	public void setUp1() throws IOException {
		htmlReporter= new ExtentHtmlReporter("Reports\\extentreportT.html");
		extent=new ExtentReports();
		extent.attachReporter(htmlReporter);
	
	}
	
	@AfterClass
	public void tearDown1() {
		extent.flush();
	}
		
		
	@BeforeMethod
	public void beforeMethod() throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		
	}

	
	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
	
	@Test
	public void Case1_MenuListTest() throws IOException, InterruptedException {
	    test=extent.createTest("Menu List Automation");
		
		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails=new Properties();
		contactDetails.load(input);
		
		contact contactUs = PageFactory.initElements(driver, contact.class);
		driver.get(contactDetails.getProperty("applicationUrl"));
		driver.manage().window().maximize();
		
	 	List <WebElement> MenuList= driver.findElements(By.xpath("//*[@id=\"block-mainnavigationbt\"]/ul/li/a"));
		System.out.println("No of informative links in Menu section : "+ MenuList.size());
		test.info("No of informative links in Menu section :"+ MenuList.size());
		
		//String Text="";
		
		Iterator<WebElement> itr=MenuList.iterator();
		while(itr.hasNext())
		{
			System.out.println("Menu Item : "+itr.next().getAttribute("title"));
			//Text=Text+itr.next().getAttribute("title")+"\n";
		}
		test.pass("Menu Items retreived successfully");
			
		
	}

	@SuppressWarnings("static-access")
	@Test
	public void Case3_ContactUs() throws InterruptedException, IOException, AWTException {
		
		test=extent.createTest("Contact Us Page Automation");
		
		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails=new Properties();
		contactDetails.load(input);
		
		contact contactUs = PageFactory.initElements(driver, contact.class);
		driver.get(contactDetails.getProperty("applicationUrl"));
		driver.manage().window().maximize();
		Thread.sleep(3000);
		
	    contactUs.acceptCookies.click();
		contactUs.contactLink.click();
		contactUs.fullName.sendKeys(contactDetails.getProperty("fullName"));
		contactUs.businessEmailAddress.sendKeys(contactDetails.getProperty("EmailAddress"));
		contactUs.organization.sendKeys(contactDetails.getProperty("OrganizationName")); 
		contactUs.phone.sendKeys(contactDetails.getProperty("phone"));
		  
		Select Country=new Select(contactUs.countryList);
		Country.selectByVisibleText(contactDetails.getProperty("countryName"));
		TakesScreenshot ts1=(TakesScreenshot) driver;
		File Src1 = ts1.getScreenshotAs(OutputType.FILE);
		File dest1=new File("ContactUs_Screen1.png");
		FileHandler.copy(Src1, dest1);
		test.addScreenCaptureFromPath("ContactUs_Screen1.png");
		
		Select relationship=new Select(contactUs.relationship);
		relationship.selectByVisibleText(contactDetails.getProperty("relationship"));

		  
		contactUs.comments.sendKeys(contactDetails.getProperty("comments"));
		test.log(Status.INFO, "Provided all contact details");
				
		contactUs.fileUpload.click();
		Thread.sleep(2000);
		Robot robo = new Robot();
		StringSelection Str=new StringSelection("C:\\Users\\swapnapriya.kura\\OneDrive - HCL Technologies Ltd\\Documents\\SDET Software\\Test File Upload.docx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(Str, null);
				
		robo.keyPress(KeyEvent.VK_CONTROL);
		robo.keyPress(KeyEvent.VK_V);
		robo.keyRelease(KeyEvent.VK_CONTROL);
		robo.keyRelease(KeyEvent.VK_V);
		robo.keyPress(KeyEvent.VK_ENTER);
	    robo.keyRelease(KeyEvent.VK_ENTER);
	    Thread.sleep(3000);
	    contactUs.privacyPolicy.click();
	    test.log(Status.INFO, "Uploaded Contact document");
	    
	    Thread.sleep(5000);
	    
		TakesScreenshot ts2=(TakesScreenshot) driver;
		File Src2 = ts2.getScreenshotAs(OutputType.FILE);
		File dest2=new File("ContactUs_Screen2.png");
		FileHandler.copy(Src2, dest2);
		test.addScreenCaptureFromPath("ContactUs_Screen2.png");
		//Thread.sleep(5000);
	    
	    test.pass("Contact Us Page automation success");
	    		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void Case2_compareScreenshot() throws IOException, InterruptedException {
		
		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails=new Properties();
		contactDetails.load(input);
		contact contactUs = PageFactory.initElements(driver, contact.class);

		driver.manage().deleteAllCookies();
		driver.get(contactDetails.getProperty("applicationUrl"));
		driver.manage().window().maximize();
		Thread.sleep(2000);		
	    contactUs.acceptCookies.click();
	    Thread.sleep(1000);
	    
	    contactUs.investorLink.click();
	    Thread.sleep(1000);
	    contactUs.whyHCLLink.click();
	    Thread.sleep(2000);
		
	    test.info("Retrieved actual screenshot");
		TakesScreenshot ts3=(TakesScreenshot) driver;
		File Src3 = ts3.getScreenshotAs(OutputType.FILE);
		File dest3=new File("CompareScreen_Actual.png");
		FileHandler.copy(Src3, dest3);
		
		test=extent.createTest("Image Comparision for matching images");
		
		test.addScreenCaptureFromPath("CompareScreen_Actual.png");
		test.addScreenCaptureFromPath("CompareScreen_ExpMatch.png");
		
		test.info("Retrieved actual screenshot");
		BufferedImage ImageExp=ImageIO.read(new File("CompareScreen_ExpMatch.png"));
		DataBuffer bufferInput=ImageExp.getData().getDataBuffer();
		int sizeInput=bufferInput.getSize();
		
		BufferedImage ImageAct=ImageIO.read(new File("CompareScreen_Actual.png"));
		DataBuffer bufferOutput=ImageAct.getData().getDataBuffer();
		int sizeOutput=bufferOutput.getSize();
		
        Boolean matchFlag=true;
        test.info("comparing both actual and expected screenshot");
		if(sizeInput==sizeOutput)
			{
			for(int i=0;i< sizeInput;i++) {
				if(bufferInput.getElem(i)!=bufferOutput.getElem(i)) {
					matchFlag=false;
					break;
					
				}
			}
				    
			}
		else
		{ 
			matchFlag=false;
			
		}
		if(matchFlag==true)
		{ 
			test.pass("Images Matching");}
		else 
		{
			 test.fail("Images not Matching");
		}
		
		
	}
	
	@Test
	public void Case4_PageText() throws IOException, InterruptedException {
		test=extent.createTest("Test Retrieval Automation");
		
		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails=new Properties();
		contactDetails.load(input);
		
		contact contactUs = PageFactory.initElements(driver, contact.class);
		driver.get(contactDetails.getProperty("applicationUrl"));
		driver.manage().window().maximize();
		Thread.sleep(2000);
		
	    contactUs.acceptCookies.click();
	    
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//*[@id=\"block-mainnavigationbt\"]/ul/li[8]/a")));
		act.build().perform();
		Thread.sleep(2000);
		driver.findElement(By.partialLinkText("Annual Report 2022")).click();
		
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,1000)");
		
		Thread.sleep(2000);
		
		String Text1=driver.findElement(By.xpath("//*[@class=\"col-md-6 col-lg-6 col-xl-7\"]/p[1]")).getText();
		//System.out.println(Text1);
		String Text2=driver.findElement(By.xpath("//*[@class=\"col-md-6 col-lg-6 col-xl-7\"]/p[2]")).getText();
		//System.out.println(Text2);
		
		String Text3="";
		List <WebElement> textList=driver.findElements(By.xpath("//*[@class=\"col-md-6 col-lg-6 col-xl-7\"]/ul/li"));
		Iterator<WebElement> itr=textList.iterator();
		while(itr.hasNext())
		{
			Text3=Text3+itr.next().getText()+"\n";			
		}
		//System.out.println(Text3);
		
		String Text=Text1+"\n"+Text2+"\n"+Text3;
		System.out.println(Text);
		
		test.info("Retrieved text from application");
		
		File textFile=new File("textFile.txt");
		try {
			textFile.createNewFile();
			FileWriter writer=new FileWriter(textFile);
			BufferedWriter bwriter=new BufferedWriter(writer);
			
			bwriter.write(Text);
			bwriter.close();
			writer.close();
			test.pass("Information added successfuly into a text file.");
					}
		catch(Exception e) {
			e.printStackTrace();
			test.fail("Unable to add infomration to text file");
		}

	}
}
