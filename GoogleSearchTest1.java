import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearchTest1 {

	public static void main(String[] args) throws InterruptedException {

		//Create a WebDriver instance and open the page
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.google.com");
		driver.manage().window().maximize();

		//Locate the textbox and enter the search text
		WebElement textbox = driver.findElement(By.name("q"));
		textbox.sendKeys("12345\n");
		textbox.submit();

		//Wait until the results page loads
		WebElement tmpElement = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id("resultStats")));

		assertEquals("Results page did not load properly",
				"12345 - Google Search", driver.getTitle());

		System.out.println("Verified Results page by title");

		//Locate the image
		WebElement image = driver.findElement(By.xpath(".//*[@id='lu_map']"));

		String title = image.getAttribute("title");

		assertEquals("Image title is not correct",
				"Map of Schenectady, NY 12345", title);

		//Verify if the image has a height and width, this will help determine if the image has loaded or not
		Boolean imageDisplayed = (Boolean) ((JavascriptExecutor) driver)
				.executeScript(
						"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
						image);
		if (imageDisplayed) {
			System.out.println("Verified image");
		} else {
			fail("Image did not load");
			System.out.println("Could not verify image");
		}

		//Locate the anchor tag above the image
		WebElement anchorTag = image.findElement(By.xpath(".."));

		if (anchorTag.getAttribute("href").startsWith(
				"https://www.google.com/maps/place")) {
			System.out.println("URL is Present");
		} else {
			fail("Image is not linked to the correct URL");
			System.out.println("URL is not Present");
		}

		assertEquals("Search text is not present in the search box", "12345",
				textbox.getAttribute("value"));

		System.out.println("Verified Searchbox text by getAttribute");
		
		//Close and quit the driver
		driver.close();
		driver.quit();

	}
}
