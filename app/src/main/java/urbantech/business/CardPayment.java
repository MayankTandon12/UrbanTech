package urbantech.business;

import java.util.Calendar;

public class CardPayment {
    private String cardNum;
    private String cvv;
    private String expireDate;

    public CardPayment(String cardNum, String cvv, String expireDate) {
        this.cardNum = cardNum;
        this.cvv = cvv;
        this.expireDate = expireDate;
    }

    public CardPayment() {
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public boolean validCardNum() {
        if (this.validLuhn(this.cardNum)) {
            //visa
            if (this.cardNum.length() == 13 || this.cardNum.length() == 16 || this.cardNum.length() == 19) {
                if (this.cardNum.charAt(0) == '4') {
                    return true;
                }
            }
            //mastercard
            if (this.cardNum.length() == 16) {
                return this.cardNum.startsWith("51") || this.cardNum.startsWith("52") ||
                        this.cardNum.startsWith("53") || this.cardNum.startsWith("54") ||
                        this.cardNum.startsWith("55");
            }
        }
        return false;
    }

    public boolean validCVV() {
        return this.cvv.length() == 3;
    }

    public boolean validExpireDate() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        if (this.expireDate.length() == 4) {
            int dateMM = Integer.parseInt(this.expireDate.substring(0, 2));
            int dateYY = Integer.parseInt(this.expireDate.substring(2, 4));
            dateYY = Integer.parseInt("20" + dateYY);
            if (dateMM < 1 || dateMM > 13) {
                return false;
            }
            if (dateYY > year) {
                return true;
            } else if (dateYY == year) {
                return dateMM >= month;
            }
        }
        return false;
    }


    // Luhn algorithm valid check
    public boolean validLuhn(String num) {
        int sum = 0;
        int[] cardArray = new int[num.length()];
        for (int i = 0; i < num.length(); i++) {
            cardArray[i] = Integer.parseInt(num.substring(i, i + 1));
        }
        for (int i = cardArray.length - 2; i >= 0; i -= 2) {
            int temp = cardArray[i];
            temp = 2 * temp;
            if (temp > 9) {
                temp = temp % 10 + 1;
            }
            cardArray[i] = temp;
        }
        for (int j : cardArray) {
            sum += j;
        }
        return (sum % 10 == 0);
    }

    @Override
    public String toString() {
        return "Card Number:" + cardNum +
                "\nCVV:" + cvv +
                "\nExpiry:" + expireDate;
    }

    public boolean ifLuhn() {
        return validLuhn(this.cardNum);
    }
}
