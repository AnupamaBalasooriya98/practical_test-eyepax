package com.eyepax.practical_test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class UserAccount {
	public static WebDriver driver;

	Faker faker = new Faker();

	@BeforeClass
	public void beforeClassMethod() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		driver.get("http://automationpractice.com/index.php");
	}

	/**
	 * Verify user can create account
	 */
	@Test (priority = 0)
	public void verifyUserCreateAccount() {
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		
		// Navigate to create account page
		driver.findElement(By.cssSelector(".login")).click();

		// Enter user email
		driver.findElement(By.cssSelector("#email_create"))
				.sendKeys(faker.internet().emailAddress());
		driver.findElement(By.cssSelector("#SubmitCreate")).click();
		
		// Enter personal information
		driver.findElement(By.cssSelector(".radio-inline")).click();
		driver.findElement(By.cssSelector("#customer_firstname")).sendKeys(firstName);
		driver.findElement(By.cssSelector("#customer_lastname")).sendKeys(lastName);
		driver.findElement(By.cssSelector("#passwd"))
				.sendKeys("Pass@#321");
		selectItemFromDropdown("#days", "2");
		selectItemFromDropdown("#months", "January");
		selectItemFromDropdown("#years", "1998");
		
		// Enter address
		driver.findElement(By.cssSelector("#address1"))
				.sendKeys(faker.address().fullAddress());
		driver.findElement(By.cssSelector("#city")).sendKeys(faker.address().city());
		selectItemFromDropdown("#id_state", "Alabama");
		driver.findElement(By.cssSelector("#postcode"))
				.sendKeys(faker.number().digits(5));
		driver.findElement(By.cssSelector("#phone_mobile"))
				.sendKeys(faker.phoneNumber().cellPhone());
		
		// Click register button
		driver.findElement(By.cssSelector("#submitAccount")).click();
		
		String actual = driver.findElement(By.cssSelector(".account")).getText();
		String expected = firstName + " " + lastName;
		
		Assert.assertEquals(actual, expected);
	}

	@AfterClass
	public void afterClassMethod(){
		driver.quit();
	}
	
	public void selectItemFromDropdown(String selector, String text) {
		Select dropdown = new Select(driver.findElement(By.cssSelector(selector)));
		dropdown.selectByVisibleText(text);
	}
}
