package test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class GlistenCosmeticsTest {
	private static WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;
	private Actions action;

	@BeforeMethod(alwaysRun = true)
	public void browserSetup() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver;
		action = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}


	@Test
	public void testAddItemToCart() {
		String EXPECTED_AMOUNT = "1";

		driver.get("https://www.glistencosmetics.com/collections/palettes/products/pastel-split-liner");
		closeModals();

		WebElement buttonAddToCart = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("product-single__cart-submit")));
		buttonAddToCart.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn--view-cart")));

		WebElement cartCounter =  driver.findElement(By.id("CartCount"));
		wait.until(ExpectedConditions.textToBePresentInElement(cartCounter, EXPECTED_AMOUNT));

		Assert.assertEquals(cartCounter.getText(), EXPECTED_AMOUNT);
	}


	private void closeModals(){
		WebElement recommendationModal = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("recommendation-modal__container")));
		wait.until(ExpectedConditions.visibilityOf(recommendationModal));

		WebElement recommendationModalClose = driver.findElement(By.className("recommendation-modal__close-button"));
		recommendationModalClose.click();

		wait.until(ExpectedConditions.invisibilityOf(recommendationModal));

		js.executeScript("window.scrollBy(0,500)", "");
		WebElement advertisementModal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-testid='POPUP']")));
		wait.until(ExpectedConditions.visibilityOf(advertisementModal));

		action.sendKeys(Keys.ESCAPE).perform();

		wait.until(ExpectedConditions.invisibilityOf(advertisementModal));
	}

	@AfterMethod(alwaysRun = true)
	public void browserTearDown()  {
		driver.quit();
		driver = null;
	}
}