package test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestDriversEmail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
System.setProperty("webdriver.chrome.driver","/Users/kashishgupta/eclipse-workspace/hello/lib/chromedriver/chromedriver");
WebDriver driver = new ChromeDriver();
driver.navigate().to("http://mail.google.com");
driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
driver.findElement(By.cssSelector("#identifierId")).sendKeys("YourEmail");
driver.findElement(By.cssSelector("#identifierNext")).click();
driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
driver.findElement(By.cssSelector("#password > div.aCsJod.oJeWuf > div > div.Xb9hP > input")).sendKeys("YourPassword");
driver.findElement(By.cssSelector("#passwordNext > content")).click();
driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);


	}}
