package arbitraryarithmetic;

public class AFloat {
    private String value;

    //default constructor
    public AFloat() {
        this.value = "0";
    }
    //constructor AFloat
    public AFloat ( String s) {
        this.value = s;
    }
    //copy constructor
    public AFloat(AFloat value1) {
        this.value = value1.value;
    }
    //parsing
    public static AFloat parse( String s) {
        return new AFloat(s);
    }

    public AFloat negate() {
        if (this.value.startsWith("-")) {
            return new AFloat(this.value.substring(1));
        } else if (this.value.equals("0") || this.value.equals("0.0")) {
            return this;
        }
        else {
            return new AFloat("-" + this.value);
        }
    }

    private static String padRight(String s, int n) {
        int len = s.length();
        if (len >= n) return s;
        char[] res = new char[n];
        for (int i = 0; i < len; i++) {
            res[i] = s.charAt(i);
        }
        for (int i = len; i < n; i++) {
            res[i] = '0';
        }
        return new String(res);
        }

    private static String padLeft(String s, int n) {
        int len = s.length();
        if (len >= n) return s;
        char[] res = new char[n];
        int pad = n - len;
        for (int i = 0; i < pad; i++) {
                res[i] = '0';
        }
        for (int i = 0; i < len; i++) {
            res[pad + i] = s.charAt(i);
        }
        return new String(res);
    }

    private int compareStrings(String a, String b) {
        if (a.length() != b.length()) return Integer.compare(a.length(), b.length());
        return a.compareTo(b);
    }

    private String removeLeadingZeros(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') {
            i++;
        }
        return s.substring(i);
    }

    private String subtractStrings(String a, String b) {
        int lenA = a.length();
        int lenB = b.length();

        // Padding shorter string with leading zeros
        while (lenA < lenB) {
            a = "0" + a;
           lenA++;
        }
        while (lenB < lenA) {
            b = "0" + b;
           lenB++;
        }

        int[] result = new int[lenA];
        int borrow = 0;

        // Perform the subtraction digit by digit
        for (int i = lenA - 1; i >= 0; i--) {
            int digitA = a.charAt(i) - '0';
            int digitB = b.charAt(i) - '0';
            int diff = digitA - digitB - borrow;

            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result[i] = diff;
        }

        // Convert result array back to a string
            char[] resultChars = new char[lenA];
        int start = 0;


        // Copy the result to the resultChars array
        int index = 0;
        for (int i = start; i < lenA; i++) {
            resultChars[index++] = (char) (result[i] + '0');
        }

        return new String(resultChars, 0, index);
    }

    public String truncateDecimalPlaces(String value, int maxDecimalPlaces) {
        int decimalIndex = value.indexOf(".");
        if (decimalIndex == -1) {
            // No decimal point found, return the value as is
            return value;
        }

        // Ensure the number has enough decimal places to truncate
        if (value.length() > decimalIndex + maxDecimalPlaces + 1) {
            value = value.substring(0, decimalIndex + maxDecimalPlaces + 1); // +1 to keep the decimal point
        }

        return value;
    }


    //overload operators
    //addition
    public AFloat add(AFloat value1){
        boolean negA = this.value.startsWith("-");
        boolean negB = value1.value.startsWith("-");
        String a = negA ? this.value.substring(1) : this.value;
        String b = negB ? value1.value.substring(1) : value1.value;

        String[] aParts = a.split("\\.", 2);
        String[] bParts = b.split("\\.", 2);

        String intA = aParts[0];
        String fracA = aParts.length > 1 ? aParts[1] : "";
        String intB = bParts[0];
        String fracB = bParts.length > 1 ? bParts[1] : "";

        // Pad fractional parts to equal length
        int maxFracLen = Math.max(fracA.length(), fracB.length());
        fracA = padRight(fracA, maxFracLen);
        fracB = padRight(fracB, maxFracLen);

        // Add fractional parts from right to left
        int carry = 0;
        char[] fracSum = new char[maxFracLen];
        for (int i = maxFracLen - 1; i >= 0; i--) {
            int digitA = fracA.charAt(i) - '0';
            int digitB = fracB.charAt(i) - '0';
            int sum = digitA + digitB + carry;
            fracSum[i] = (char) ((sum % 10) + '0');
            carry = sum / 10;
        }

        // Pad integer parts to equal length
        int maxIntLen = Math.max(intA.length(), intB.length());
        intA = padLeft(intA, maxIntLen);
        intB = padLeft(intB, maxIntLen);

        // Add integer parts from right to left
        char[] intSum = new char[maxIntLen];
        for (int i = maxIntLen - 1; i >= 0; i--) {
            int digitA = intA.charAt(i) - '0';
            int digitB = intB.charAt(i) - '0';
            int sum = digitA + digitB + carry;
            intSum[i] = (char) ((sum % 10) + '0');
            carry = sum / 10;
        }
        // Handle final carry
            String intResult;
        if (carry != 0) {
            intResult = (char) (carry + '0') + new String(intSum);
        } else {
            intResult = new String(intSum);
        }

        // Compose fractional part string
        String fracResult = new String(fracSum);

        String result;
        if (fracResult.isEmpty()) {
            result = intResult;
        } else {
            result = intResult + "." + fracResult;
        }

        // Manage sign
        if (negA && negB) {
            result = "-" + result;
        }
        return new AFloat(result);
    }

    //subtraction
    public AFloat subtract(AFloat value1){
        boolean negA = this.value.startsWith("-");
        boolean negB = value1.value.startsWith("-");
        String a = negA ? this.value.substring(1) : this.value;
        String b = negB ? value1.value.substring(1) : value1.value;

        // Case 1: A - (-B) => A + B
        if (!negA && negB) return this.add(new AFloat(b));
        // Case 2: -A - B => -(A + B)
        if (negA && !negB) return this.negate().add(new AFloat(b)).negate();
        // Case 3: -A - (-B) => B - A
        if (negA && negB) return new AFloat(b).subtract(new AFloat(a));

        String[] aParts = a.split("\\.", 2);
        String[] bParts = b.split("\\.", 2);

        String aInt = aParts[0];
        String bInt = bParts[0];
        String aFrac = aParts.length > 1 ? aParts[1] : "0";
        String bFrac = bParts.length > 1 ? bParts[1] : "0";

        // Pad fractional parts to equal length (right side)
        int fracLen = Math.max(aFrac.length(), bFrac.length());
        aFrac = padRight(aFrac, fracLen);
        bFrac = padRight(bFrac, fracLen);

        // Pad integer parts to equal length (left side)
        int intLen = Math.max(aInt.length(), bInt.length());
        aInt = padLeft(aInt, intLen);
        bInt = padLeft(bInt, intLen);

        String fullA = aInt + aFrac;
        String fullB = bInt + bFrac;


        boolean negativeResult = false;
        if (compareStrings(fullA, fullB) < 0) {
            // If a < b, swap
            String temp = fullA;
            fullA = fullB;
            fullB = temp;
            negativeResult = true;
        }
        char[] result = new char[fullA.length()];
        int borrow = 0;

        for (int i = fullA.length() - 1; i >= 0; i--) {
            int d1 = fullA.charAt(i) - '0';
           int d2 = fullB.charAt(i) - '0' + borrow;

            if (d1 < d2) {
                d1 += 10;
               borrow = 1;
            } else {
                borrow = 0;
            }

            result[i] = (char) ((d1 - d2) + '0');
        }

        // Insert decimal point
        String intPart = new String(result, 0, intLen);
        String fracPart = new String(result, intLen, fracLen);


        String finalResult = intPart;
        if (!fracPart.isEmpty()) {
            finalResult += "." + fracPart;
        }

        if (finalResult.isEmpty()) finalResult = "0";
        if (negativeResult && !finalResult.equals("0")) finalResult = "-" + finalResult;

        return new AFloat(finalResult);
    }
    //multiplication
    public AFloat multiply(AFloat value1){
        boolean negA = this.value.startsWith("-");
        boolean negB = value1.value.startsWith("-");
        String a = negA ? this.value.substring(1) : this.value;
        String b = negB ? value1.value.substring(1) : value1.value;

        // Count decimal digits in each number
        int decDigitsA = a.contains(".") ? a.length() - a.indexOf('.') - 1 : 0;
        int decDigitsB = b.contains(".") ? b.length() - b.indexOf('.') - 1 : 0;
        int totalDecimalDigits = decDigitsA + decDigitsB;

        // Remove decimal points for integer multiplication
        String intA = a.replace(".", "");
        String intB = b.replace(".", "");

        // Initialize result array
        int[] result = new int[intA.length() + intB.length()];

        // Multiply like integers
        for (int i = intA.length() - 1; i >= 0; i--) {
            int digitA = intA.charAt(i) - '0';
            for (int j = intB.length() - 1; j >= 0; j--) {
                int digitB = intB.charAt(j) - '0';
                int sum = digitA * digitB + result[i + j + 1];
                result[i + j + 1] = sum % 10;
                result[i + j] += sum / 10;
            }
        }

        // Convert int array digits to a char array for the result string
        int start=0;
        int resultLength = result.length - start;
        char[] resultChars = new char[resultLength];
        for (int i = 0; i < resultLength; i++) {
            resultChars[i] = (char)(result[start + i] + '0');
        }

        String resultStr = new String(resultChars);
        // Insert decimal point at the correct position
        if (resultStr.length() <= totalDecimalDigits)
        if (totalDecimalDigits > 0) {
            char[] padded = new char[totalDecimalDigits + 2]; // "0." + zero padding + digits
            padded[0] = '0';
            padded[1] = '.';
            for (int i = 0; i < totalDecimalDigits - resultStr.length(); i++) {
                padded[2 + i] = '0';
            }
            for (int i = 0; i < resultStr.length(); i++) {
                padded[2 + totalDecimalDigits - resultStr.length() + i] = resultStr.charAt(i);
            }
            resultStr = new String(padded);
        } else {
            // Normal case: insert decimal point
            resultStr = resultStr.substring(0, resultStr.length() - totalDecimalDigits) + "." + resultStr.substring(resultStr.length() - totalDecimalDigits);
        }


        return new AFloat(resultStr);

    }
        //division
        public AFloat divide(AFloat value1){
            if (value1.value.equals("0") || value1.value.equals("0.0")) {
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

            // Count decimal digits in each number
            int decDigitsA = dividend.contains(".") ? dividend.length() - dividend.indexOf('.') - 1 : 0;
            int decDigitsB = divisor.contains(".") ? divisor.length() - divisor.indexOf('.') - 1 : 0;
            int DecimalDigits = decDigitsA - decDigitsB;

            // Remove decimal points for integer division
            String intA = dividend.replace(".", "");
            String intB = divisor.replace(".", "");

            if (DecimalDigits > 0) {
                for (int i = 0; i < DecimalDigits; i++) {
                    intB += "0";
                }
            } else if (DecimalDigits < 0) {
                for (int i = 0; i < -DecimalDigits; i++) {
                    intA += "0";
                }
            }

            // Initialize result array
            char[] result = new char[intA.length()];
            int resultIndex = 0;
                String remainder = "";

            // Perform long division
            for (int i = 0; i < intA.length(); i++) {
                // Append the current digit from dividend to the remainder
                remainder += intA.charAt(i);
                remainder = removeLeadingZeros(remainder);  // Remove leading zeros

               int count = 0;

                // Perform subtraction until the remainder is smaller than the divisor
                while (compareStrings(remainder, intB) >= 0) {
                    remainder = subtractStrings(remainder, intB);
                    count++;
                }

                // Store the quotient digit in the result array
                result[resultIndex++] = (char) (count + '0');
            }

            // Construct the final result string
            int start =0;
            char[] finalResult = new char[resultIndex - start];
            for (int i = 0; i < finalResult.length; i++) {
                finalResult[i] = result[start + i];
            }
            String quotient = new String(finalResult);

            if (DecimalDigits > 0) {
                int len = quotient.length();
                if (len <= DecimalDigits) {
                    // Pad with leading zeros
                    String padded = "0.";
                    for (int i = 0; i < DecimalDigits - len; i++) {
                        padded += "0";
                    }
                    padded += quotient;
                    quotient = padded;
                } else {
                    // Insert the decimal point at the correct position
                    quotient = quotient.substring(0, quotient.length() - DecimalDigits) + "." + quotient.substring(quotient.length() - DecimalDigits);
                }
            }

            int maxDecimalPlaces = 30;
            String finalquotient = truncateDecimalPlaces(quotient, maxDecimalPlaces);
        return new AFloat(finalquotient);

        }

    @Override
    public String toString() {
        return this.value;
    }
}