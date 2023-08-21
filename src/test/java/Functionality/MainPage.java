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
import org.openqa.selenium.chrome.ChromeOptions;
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
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import ObjectRepository.contact;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
//import io.github.bonigarcia.wdm.WebDriverManager;
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
		
		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails = new Properties();
		contactDetails.load(input);
		
		htmlReporter = new ExtentHtmlReporter(contactDetails.getProperty("extentReportsPath"));
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

	}

	@AfterClass
	public void tearDown1() {
		extent.flush();
	}

	@BeforeMethod
	public void beforeMethod() throws InterruptedException {
		/*
		 * WebDriverManager.chromedriver().setup(); ChromeOptions options = new
		 * ChromeOptions(); options.addArguments("--remote-allow-origins=*"); driver =
		 * new ChromeDriver(options);
		 */
    	System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
      	driver = new ChromeDriver();	
		
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	@Test
	public void Case1_MenuListTest() throws IOException, InterruptedException {
		test = extent.createTest("Menu List Automation");

		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails = new Properties();
		contactDetails.load(input);

		driver.get(contactDetails.getProperty("applicationUrl"));
		driver.manage().window().maximize();

		List<WebElement> MenuList = driver.findElements(By.xpath("//*[@id=\"block-mainnavigationbt\"]/ul/li/a"));
		System.out.println("No of informative links in Menu section : " + MenuList.size());
		test.info("No of informative links in Menu section :" + MenuList.size());

		Iterator<WebElement> itr = MenuList.iterator();
		while (itr.hasNext()) {
			System.out.println("Menu Item : " + itr.next().getAttribute("title"));
		}
		test.pass("Menu Items retreived successfully");

	}

	@SuppressWarnings("static-access")
	@Test
	public void Case3_ContactUs() throws InterruptedException, IOException, AWTException {

		test = extent.createTest("Contact Us Page Automation");

		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails = new Properties();
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

		Select Country = new Select(contactUs.countryList);
		Country.selectByVisibleText(contactDetails.getProperty("countryName"));
		TakesScreenshot ts1 = (TakesScreenshot) driver;
		File Src1 = ts1.getScreenshotAs(OutputType.FILE);
		File dest1 = new File(contactDetails.getProperty("ContactScreen1Path"));
		FileHandler.copy(Src1, dest1);
		test.addScreenCaptureFromPath(contactDetails.getProperty("ContactScreen1Path"));

		Select relationship = new Select(contactUs.relationship);
		relationship.selectByVisibleText(contactDetails.getProperty("relationship"));

		contactUs.comments.sendKeys(contactDetails.getProperty("comments"));
		test.log(Status.INFO, "Provided all contact details");

		contactUs.fileUpload.click();
		Thread.sleep(2000);
		
		StringSelection Str = new StringSelection(contactDetails.getProperty("ContactFileUpload"));
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(Str, null);
		Thread.sleep(3000);
		
		Robot robo = new Robot();
		robo.keyPress(KeyEvent.VK_CONTROL);
		robo.keyPress(KeyEvent.VK_V);
		robo.keyRelease(KeyEvent.VK_CONTROL);
		robo.keyRelease(KeyEvent.VK_V);
		robo.keyPress(KeyEvent.VK_ENTER);
		robo.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(3000);
		test.log(Status.INFO, "Uploaded Contact document");
		
		contactUs.privacyPolicy.click();
		Thread.sleep(5000);

		TakesScreenshot ts2 = (TakesScreenshot) driver;
		File Src2 = ts2.getScreenshotAs(OutputType.FILE);
		File dest2 = new File(contactDetails.getProperty("ContactScreen2Path"));
		FileHandler.copy(Src2, dest2);
		test.addScreenCaptureFromPath(contactDetails.getProperty("ContactScreen2Path"));

		test.pass("Contact Us Page automation success");

	}

	@SuppressWarnings("static-access")
	@Test
	public void Case2_compareScreenshot() throws IOException, InterruptedException {
		test = extent.createTest("Image Comparision for matching images");

		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails = new Properties();
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
		TakesScreenshot ts3 = (TakesScreenshot) driver;
		File Src3 = ts3.getScreenshotAs(OutputType.FILE);
		File dest3 = new File(contactDetails.getProperty("CompareActImagePath"));
		FileHandler.copy(Src3, dest3);

		test.addScreenCaptureFromPath(contactDetails.getProperty("CompareActImagePath"));
		test.addScreenCaptureFromPath(contactDetails.getProperty("CompareExpImagePath"));

		test.info("Retrieved actual screenshot");
		BufferedImage ImageExp = ImageIO.read(new File(contactDetails.getProperty("CompareExpImagePath")));
		DataBuffer bufferInput = ImageExp.getData().getDataBuffer();
		int sizeInput = bufferInput.getSize();

		BufferedImage ImageAct = ImageIO.read(new File(contactDetails.getProperty("CompareActImagePath")));
		DataBuffer bufferOutput = ImageAct.getData().getDataBuffer();
		int sizeOutput = bufferOutput.getSize();

		Boolean matchFlag = true;
		test.info("comparing both actual and expected screenshot");
		if (sizeInput == sizeOutput) {
			for (int i = 0; i < sizeInput; i++) {
				if (bufferInput.getElem(i) != bufferOutput.getElem(i)) {
					matchFlag = false;
					break;

				}
			}

		} else {
			matchFlag = false;

		}
		if (matchFlag == true) {
			test.pass("Images Matching");
		} else {
			test.fail("Images not Matching");
		}

	}

	@Test
	public void Case4_PageText() throws IOException, InterruptedException {
		test = extent.createTest("Text Retrieval Automation");

		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails = new Properties();
		contactDetails.load(input);

		contact contactUs = PageFactory.initElements(driver, contact.class);
		driver.get(contactDetails.getProperty("applicationUrl"));
		driver.manage().window().maximize();
		Thread.sleep(2000);

		contactUs.acceptCookies.click();

		Actions act = new Actions(driver);
		act.moveToElement(contactUs.textLink);
		act.build().perform();
		Thread.sleep(2000);
		driver.findElement(By.partialLinkText("Annual Report 2022")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");

		Thread.sleep(2000);

		String Text1 = contactUs.textComponent1.getText();
		String Text2 = contactUs.textComponent2.getText();
		String Text3 = "";
		List<WebElement> textList = contactUs.textComponent3;
		Iterator<WebElement> itr = textList.iterator();
		while (itr.hasNext()) {
			Text3 = Text3 + itr.next().getText() + "\n";
		}

		String Text = Text1 + "\n" + Text2 + "\n" + Text3;
		System.out.println(Text);

		test.info("Retrieved text from application");

		File textFile = new File(contactDetails.getProperty("TextFilePath"));
		try {
			textFile.createNewFile();
			FileWriter writer = new FileWriter(textFile);
			BufferedWriter bwriter = new BufferedWriter(writer);
			bwriter.write(Text);
			bwriter.close();
			writer.close();
			test.pass("Information added successfuly into a text file.");
			
		} catch (Exception e) {
			e.printStackTrace();
			test.fail("Unable to add infomration to text file");
		}

	}

	@Test
	public void Case5_ContactUsValidation() throws IOException, InterruptedException {
		test = extent.createTest("Contact Us Page error message validation Automation");

		InputStream input = new FileInputStream("src/test/resources/contactUs.properties");
		Properties contactDetails = new Properties();
		contactDetails.load(input);

		contact contactUs = PageFactory.initElements(driver, contact.class);
		driver.get(contactDetails.getProperty("applicationUrl"));
		driver.manage().window().maximize();
		Thread.sleep(3000);

		contactUs.acceptCookies.click();
		contactUs.contactLink.click();
		contactUs.submit.submit();

		test.info("Retrieved all error messages from screen");
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(contactUs.fullNameError.getText(), contactDetails.getProperty("fullNameErrorMsg"));
		test.info("Validated Full Name related error message");
		softAssert.assertEquals(contactUs.EmailError.getText(), contactDetails.getProperty("EmailErrorMsg"));
		test.info("Validated Email related error message");
		softAssert.assertEquals(contactUs.phoneError.getText(), contactDetails.getProperty("phoneErrorMsg"));
		test.info("Validated phone related error message");
		softAssert.assertEquals(contactUs.countryError.getText(), contactDetails.getProperty("countryErrorMsg"));
		test.info("Validated country related error message");
		softAssert.assertEquals(contactUs.relationError.getText(), contactDetails.getProperty("relationErrorMsg"));
		test.info("Validated relationship related error message");
		softAssert.assertEquals(contactUs.commentsError.getText(), contactDetails.getProperty("commentsError"));
		test.info("Validated comments related error message");
		softAssert.assertEquals(contactUs.privacyError.getText(), contactDetails.getProperty("privacyError"));
		test.info("Validated Privacy related error message");

		test.info("comparied all error messages from screen with expected messages");
		softAssert.assertAll();

		Thread.sleep(2000);

		TakesScreenshot ts4 = (TakesScreenshot) driver;
		File Src4 = ts4.getScreenshotAs(OutputType.FILE);
		File dest4 = new File(contactDetails.getProperty("ErrorScreen1"));
		FileHandler.copy(Src4, dest4);
		test.addScreenCaptureFromPath(contactDetails.getProperty("ErrorScreen1"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

		Thread.sleep(1000);

		TakesScreenshot ts5 = (TakesScreenshot) driver;
		File Src5 = ts5.getScreenshotAs(OutputType.FILE);
		File dest5 = new File(contactDetails.getProperty("ErrorScreen2"));
		FileHandler.copy(Src5, dest5);
		test.addScreenCaptureFromPath(contactDetails.getProperty("ErrorScreen2"));

		Thread.sleep(1000);

		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,600)");

		TakesScreenshot ts6 = (TakesScreenshot) driver;
		File Src6 = ts6.getScreenshotAs(OutputType.FILE);
		File dest6 = new File(contactDetails.getProperty("ErrorScreen3"));
		FileHandler.copy(Src6, dest6);
		test.addScreenCaptureFromPath(contactDetails.getProperty("ErrorScreen3"));

		test.pass("All error message validations passed");
	}

}
