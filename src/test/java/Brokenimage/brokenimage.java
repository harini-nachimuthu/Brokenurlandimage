package Brokenimage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class brokenimage {
	protected static String url="https://the-internet.herokuapp.com/broken_images";
	WebDriver  driver;
  @Test
  public void brokenimage() throws IOException {
	  List<WebElement> links=driver.findElements(By.tagName("img"));
	  System.out.println("Total images in url :"+links.size());
	  
	  for(WebElement link:links) {
		  String image=link.getAttribute("src");
		  URL url=new URL(image);
		  HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
		  httpURLConnection.setConnectTimeout(5000);
		  httpURLConnection.connect();
		  
		  if(httpURLConnection.getResponseCode()==200) {
		    	System.out.println("Working url :"+httpURLConnection.getResponseMessage());
		    }else {
		    	System.out.println("broken url: "+httpURLConnection.getResponseMessage());

		    }
		    httpURLConnection.disconnect();
		  }
		  
	  }
  
  @BeforeSuite
  public void beforeSuite() {
	  WebDriverManager.chromedriver().setup();
	  driver=new ChromeDriver();
	  driver.manage().window().maximize();
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
  }
  @BeforeClass
  public void beforclass() {
	  driver.navigate().to(url);
  }
  @AfterSuite
  public void afterSuite() {
	  if(driver!=null) {
		  driver.close();
	  }
  }
}
