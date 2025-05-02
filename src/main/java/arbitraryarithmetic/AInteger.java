package arbitraryarithmetic;

public class AInteger {
    private String value;

    //default constructor
    public AInteger() {
        this.value = "0";
    }
    //constructor AInteger
    public AInteger ( String s) {
        this.value = s;
    }
    //copy constructor
    public AInteger(AInteger value1) {
        this.value = value1.value;
    }
    //parsing
    public static AInteger parse( String s) {
        return new AInteger(s);
    }

    // Helper method to negate the current AInteger's value
    public AInteger negate() {
        if (this.value.equals("0")) {
            // Zero negated is still zero
            return this;
        }
        if (this.value.startsWith("-")) {
            // If already negative, remove the minus sign
            return new AInteger(this.value.substring(1));
        } else {
           // Otherwise, add minus sign
            return new AInteger("-" + this.value);
        }
    }

    private static int compareStrings(String num1, String num2) {
        if (num1.length() != num2.length()) {
            return num1.length() - num2.length();
        }
        return num1.compareTo(num2);
    }

    private String removeLeadingZeros(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') {
            i++;
        }
        return s.substring(i);
    }



    //overload operators
    //addition
    public AInteger add(AInteger value1){
        if (this.value.startsWith("-") && value1.value.startsWith("-")) {
            AInteger temp1 = new AInteger(this.value.substring(1));
            AInteger temp2 = new AInteger(value1.value.substring(1));
            return temp1.add(temp2).negate();
        } else if (this.value.startsWith("-")) {
            AInteger temp1 = new AInteger(this.value.substring(1));
            return value1.subtract(temp1);
        } else if (value1.value.startsWith("-")) {
            AInteger temp1 = new AInteger(value1.value.substring(1));
            return this.subtract(temp1);
        }

        String num1 = this.value;
        String num2 = value1.value;
    
        int len1 = num1.length();
        int len2 = num2.length();
        int maxLen = Math.max(len1, len2) + 1; // +1 for possible carry
    
        char[] tempResult = new char[maxLen];
        int carry = 0, k = 0;
        int i = len1 - 1;
        int j = len2 - 1;

        while (i >= 0 || j >= 0 || carry != 0) {
            int digit1 = (i >= 0) ? num1.charAt(i--) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j--) - '0' : 0;

            int sum = digit1 + digit2 + carry;
            tempResult[k++] = (char) ((sum % 10) + '0');
            carry = sum / 10;
        }

        char[] finalResult = new char[k];
        for (int m = 0; m < k; m++) {
            finalResult[m] = tempResult[k - m - 1];
        }

        return new AInteger(new String(finalResult));
    }
    //subtraction
    public AInteger subtract(AInteger value1){
        String num1 = this.value;
        String num2 = value1.value;

        
        boolean resultNegative = false;
        if (compareStrings(num1, num2) < 0) {
            String temp = num1;
            num1 = num2;
            num2 = temp;
            resultNegative = true;
        }

        if (this.value.startsWith("-") && value1.value.startsWith("-")) {
            // Both are negative: subtract the absolute values and negate the result
            AInteger temp1 = new AInteger(this.value.substring(1));
            AInteger temp2 = new AInteger(value1.value.substring(1));
            return temp2.subtract(temp1); // Result is negative
        } else if (this.value.startsWith("-")) {
            // This is negative: add the absolute value of this to value1
            AInteger temp1 = new AInteger(this.value.substring(1));
            return value1.add(temp1).negate(); // Result is negative
        } else if (value1.value.startsWith("-")) {
            // value1 is negative: add the absolute value of value1 to this
            AInteger temp1 = new AInteger(value1.value.substring(1));
            return this.add(temp1);
        }
        int len1 = num1.length();
        int len2 = num2.length();
        int maxLen = Math.max(len1, len2) ;
        char[] tempResult = new char[maxLen];
        int borrow = 0, k = 0;
        int i = len1 - 1;
        int j = len2 - 1;

        while (i >= 0 || j >= 0) {
            int digit1 = (i >= 0) ? num1.charAt(i--) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j--) - '0' : 0;

            int diff = digit1 - digit2 - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            tempResult[k++] = (char) (diff + '0');
        }
        char[] finalResult = new char[k];
        for (int m = 0; m < k; m++) {
            finalResult[m] = tempResult[k - m - 1];
        }

        String result = new String(finalResult);
        if (resultNegative) {
            result = "-" + result;
        }
        return new AInteger(result);
    }
    //multiplication
    public AInteger multiply(AInteger value1){
        String num1 = this.value;
        String num2 = value1.value;
        boolean negativeResult = false;

        if (num1.startsWith("-")) {
            negativeResult = !negativeResult;
            num1 = num1.substring(1);
        }
        if (num2.startsWith("-")) {
            negativeResult = !negativeResult;
            num2 = num2.substring(1);
        }

        int len1 = num1.length();
        int len2 = num2.length();
        int[] product = new int[len1 + len2]; 

        // Multiply each digit of num1 with each digit of num2
        for (int i = len1 - 1; i >= 0; i--) {
            int digit1 = num1.charAt(i) - '0';
            for (int j = len2 -1; j >= 0; j--) {
                int digit2 = num2.charAt(j) - '0';
                int sum = digit1 * digit2 + product[i + j + 1];
                product[i + j + 1] = sum % 10; // digit
                product[i + j] += sum / 10;    // carry
            }
        }

        // Convert int array digits to a char array for the result string
        int start=0;
        int resultLength = product.length - start;
        char[] resultChars = new char[resultLength];
        for (int i = 0; i < resultLength; i++) {
            resultChars[i] = (char)(product[start + i] + '0');
        }

        String result = new String(resultChars);
        if (negativeResult && !result.equals("0")) {
            result = "-" + result;
        }
        return new AInteger(result);
    }
    //division
    public AInteger divide(AInteger value1){
        if (value1.value.equals("0")) {
            throw new ArithmeticException("Division by zero");
        }

        String dividend = this.value;
        String divisor = value1.value;
        boolean negativeResult = false;

        if (dividend.startsWith("-")) {
            negativeResult = !negativeResult;
            dividend = dividend.substring(1);
        }
        if (divisor.startsWith("-")) {
            negativeResult = !negativeResult;
            divisor = divisor.substring(1);
        }

        char[] result = new char[dividend.length()];
        int resultIndex = 0;

        String remainder = "";

        for (int i = 0; i < dividend.length(); i++) {
            remainder += dividend.charAt(i);
            remainder = removeLeadingZeros(remainder);

            int count = 0;

            AInteger Aremainder = new AInteger(remainder);
            AInteger Adivisor = new AInteger(divisor);
            while (compareStrings(remainder, divisor) >= 0) {
                Aremainder = Aremainder.subtract(Adivisor);
                remainder = Aremainder .value;
                count++;
           }

           result[resultIndex++] = (char) (count + '0');
        }
        int start = 0;
        while (start < resultIndex - 1 && result[start] == '0') {
            start++;
        }

        char[] finalResult = new char[resultIndex - start];
        for (int i = 0; i < finalResult.length; i++) {
            finalResult[i] = result[start + i];
        }

        String quotient = new String(finalResult);
        if (negativeResult && !quotient.equals("0")) {
            quotient = "-" + quotient;
        }


        return new AInteger(quotient);
    }
    @Override
    public String toString() {
        return this.value.toString();
    }
}

