package swatikagalkarprojects.Tests;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import swatikagalkarproject.TestComponents.BaseTest;
import swatikagalkarproject.pageObjects.CartPage;
import swatikagalkarproject.pageObjects.CheckoutPage;
import swatikagalkarproject.pageObjects.ConfirmationPage;
import swatikagalkarproject.pageObjects.LandingPage;
import swatikagalkarproject.pageObjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest
{
	String productName="ZARA COAT 3";
	@Test(groups= {"ErrorHandling"})
	public void loginErrorValidation() throws IOException
	{
		
		landingPage.loginApplication("anshika@gmail.com", "Iamin@000");
		Assert.assertEquals("Incorrect email or pssword.",landingPage.getErrorMessage());		
	}
	
	@Test
	public void productErrorValidation()
	{
			ProductCatalogue productCatalogue=landingPage.loginApplication("swatikagalkar@gmail.com", "HIgmail@123");
			List<WebElement> products=productCatalogue.getProductList();
			productCatalogue.addProductToCart(productName);
			CartPage cartPage=productCatalogue.goToCartPage();		
			Boolean match=cartPage.verifyProductDisplay("ZARA COAT 33");
			Assert.assertFalse(match);
	}
}
