package urbantech.business;

import org.junit.Assert;
import org.junit.Test;


public class AddressTesting {

    @Test
    public void test(){
        System.out.println("\nStarting Unit Testing for Address");
        ValidateDeliveryAddress test = new ValidateDeliveryAddress("My House","Winnipeg","MB","Canada","R2M 7G5");
        Assert.assertNotNull(test);
        Assert.assertTrue(test.getCity().equalsIgnoreCase("Winnipeg"));
        Assert.assertTrue(test.getProvince().equalsIgnoreCase("MB"));
        Assert.assertTrue(test.getCountry().equalsIgnoreCase("Canada"));
        Assert.assertTrue(test.getPostalCode().equalsIgnoreCase("R2M 7G5"));

    }

    @Test
    public void testCity(){
        System.out.println("\nUnit Testing for City:");

        ValidateDeliveryAddress city1 = new ValidateDeliveryAddress("My house","Winnipeg","MB","Canada","R2M 5Y3");
        ValidateDeliveryAddress city2 = new ValidateDeliveryAddress("My house","winnipeg","MB","Canada","R2M 5Y3");
        ValidateDeliveryAddress city3 = new ValidateDeliveryAddress("My house","wiNNIpeg","MB","Canada","R2M 5Y3");

        //Not a valid City for this app
        System.out.println("these 2 are not valid cities for thia app:");
        ValidateDeliveryAddress city4 = new ValidateDeliveryAddress("My house","manitoba","MB","Canada","R2M 5Y3");
        ValidateDeliveryAddress city5 = new ValidateDeliveryAddress("My house","toronto","MB","Canada","R2M 5Y3");

    }

    @Test
    public void testProvince(){
        System.out.println("\nUnit Testing for Province:");
        ValidateDeliveryAddress province1 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","R2M 5Y3");
        ValidateDeliveryAddress province2 = new ValidateDeliveryAddress("My house","Winnipeg","manitoba","Canada","R2M 5Y3");
        ValidateDeliveryAddress province3 = new ValidateDeliveryAddress("My house","Winnipeg","MB","Canada","R2M 5Y3");
        ValidateDeliveryAddress province4 = new ValidateDeliveryAddress("My house","Winnipeg","mb","Canada","R2M 5Y3");

        //not a valid province for this app
        System.out.println("these 2 are not valid province for thia app:");
        ValidateDeliveryAddress province5 = new ValidateDeliveryAddress("My house","Winnipeg","BC","Canada","R2M 5Y3");
        ValidateDeliveryAddress province6 = new ValidateDeliveryAddress("My house","Winnipeg","Manytoba","Canada","R2M 5Y3");


    }

    @Test
    public void testCountry(){
        System.out.println("\nUnit Testing for Country:");
        ValidateDeliveryAddress country1 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","R2M 5Y3");
        ValidateDeliveryAddress country2 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","canada","R2M 5Y3");
        ValidateDeliveryAddress country3 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","CA","R2M 5Y3");
        ValidateDeliveryAddress country4 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","ca","R2M 5Y3");

        //Not a valid country for this app
        System.out.println("these 2 are not valid Countries for thia app:");
        ValidateDeliveryAddress country5 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","America","R2M 5Y3");
        ValidateDeliveryAddress country6 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","CAN","R2M 5Y3");

    }

    @Test
    public void testPostalCode(){
        System.out.println("\nUnit Testing for Postal Code:");
        ValidateDeliveryAddress postal1 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","R2M 5Y3");
        ValidateDeliveryAddress postal2 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","r2m 2t9");
        ValidateDeliveryAddress postal3 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","R3p 0y0");
        ValidateDeliveryAddress postal4 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","r3r3r3");
        ValidateDeliveryAddress postal5 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","R2M5Y3");

        // Not a valid postal code for this app
        System.out.println("These 2 are not valid postal codes for this app");
        ValidateDeliveryAddress postal6 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","R1M 5Y3");
        ValidateDeliveryAddress postal7 = new ValidateDeliveryAddress("My house","Winnipeg","Manitoba","Canada","O5k 7P2");

        System.out.println("\nEnd of Address Testing");

    }

}

