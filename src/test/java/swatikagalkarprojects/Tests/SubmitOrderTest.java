package swatikagalkarprojects.Tests;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import swatikagalkarproject.TestComponents.BaseTest;
import swatikagalkarproject.pageObjects.CartPage;
import swatikagalkarproject.pageObjects.CheckoutPage;
import swatikagalkarproject.pageObjects.ConfirmationPage;
import swatikagalkarproject.pageObjects.LandingPage;
import swatikagalkarproject.pageObjects.OrderPage;
import swatikagalkarproject.pageObjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest
{
	String productName="ZARA COAT 3";
	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String>input)throws IOException
	{

//hello
		ProductCatalogue productCatalogue=landingPage.loginApplication(input.get("email"),input.get("password"));
		List<WebElement> products=productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage=productCatalogue.goToCartPage();		
		Boolean match=cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage=cartPage.goToCheckoutPage();
		checkoutPage.selectCountry("India");
		ConfirmationPage confirmPage=checkoutPage.submitOrder();
		String confirmMessage=confirmPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));	
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void orderHistoryTest()
	{
		ProductCatalogue productCatalogue=landingPage.loginApplication("anshika@gmail.com", "Iamking@000");
		OrderPage orderPage=productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	}
	
	//1.Using normal data provider 
	
	//@DataProvider
	//public Object[][] getData()
	//{
		//return new Object[][] {
			//{"anshika@gmail.com","Iamking@000","ZARA COAT 3"},
			//{"swatikagalkar@gmail.com","HIgmail@123","ADIDAS ORIGINAL"}};
		
	//}
	
	//2.Using hashmaps
	//@DataProvider
	//public Object[][] getData()
	//{
		//Map<String,String> map=new HashMap<String,String>();
		//map.put("email", "anshika@gmail.com");
		//map.put("password", "Iamking@000");
		//map.put("product", "ZARA COAT 3");
		
		//Map<String,String> map1=new HashMap<String,String>();
		//map1.put("email", "swatikagalkar@gmail.com");
		//map1.put("password", "HIgmail@123");
		//map1.put("product", "ADIDAS ORIGINAL");
		
		//return new Object[][] {{map},{map1}};
	//}
	
//3.using json
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String,String>> data=getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//swatikagalkarproject//data//PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	

}
