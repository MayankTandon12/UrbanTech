package urbantech.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateDeliveryAddress {
    //change to main.getpath for real.
    private final String testPath = "src/main/assets/dbTest/SC";
    private String addressLine;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    public ValidateDeliveryAddress(String addressLine, String city, String province, String country, String postalCode) {

        boolean validAddress = checkValidAddress(addressLine, city, province, country, postalCode);

        if (validAddress) {
            this.addressLine = addressLine;
            this.city = city;
            this.province = province;
            this.country = country;
            this.postalCode = postalCode;
            // add to database by different method
//            addAddress();
        }
    }

    public boolean checkValidAddress(String addressLine, String city, String province, String country, String postalCode) {

        boolean validAddress;

        validAddress = testLine(addressLine) && (testCity(city) && testProvince(province) && testCountry(country) && testPostalCode(postalCode));

        return validAddress;
    }

    private boolean testLine(String addressLine) {
        boolean validLine = false;
        if (addressLine.length() > 0)
            validLine = true;
        else
            System.out.println("Address  cannot be empty");
        return validLine;
    }

    //check for city
    private boolean testCity(String city) {

        boolean validCity = false;
        if (city.equalsIgnoreCase("winnipeg")) {
            validCity = true;
        } else
            System.out.println("Not a valid City");

        return validCity;
    }

    //check for province
    private boolean testProvince(String province) {
        boolean validProvince = false;

        if (province.equalsIgnoreCase("manitoba") || province.equalsIgnoreCase("mb")) {
            validProvince = true;
        } else
            System.out.println("Not a valid Province");

        return validProvince;
    }

    //check for country
    private boolean testCountry(String country) {
        boolean validCountry = false;

        if ((country.equalsIgnoreCase("canada")) || (country.equalsIgnoreCase("ca"))) {
            validCountry = true;
        } else
            System.out.println("Not a valid Country");

        return validCountry;
    }

    //check PostalCode
    private boolean testPostalCode(String postalCode) {
        boolean validPostalCode;

        //get everything in uppercase
        postalCode = postalCode.toUpperCase();
        String regex = "^?R[2-3][A-Z] ?\\d[A-Z]\\d$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(postalCode);
        validPostalCode = ((Matcher) matcher).matches();

        if (!validPostalCode)
            System.out.println("not  a valid Postal Code");

        return validPostalCode;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "Street:" + addressLine +
                "\nCity:" + city +
                "\nProvince:" + province +
                "\nCountry:" + country +
                "\nPostal Code:" + postalCode + "\n";
    }
}
