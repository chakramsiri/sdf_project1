package arbitraryarithmetic;

import java.math.BigDecimal;
import java.math.MathContext;

public class AFloat {
    private BigDecimal value;

    // Default constructor
    public AFloat() {
        this.value = BigDecimal.ZERO;
    }

    // Constructor with string input
    public AFloat(String s) {
        this.value = new BigDecimal(s);
    }

    // Copy constructor
    public AFloat(AFloat other) {
        this.value = other.value;
    }

    // Static parse method
    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    // Addition of BigFloat converting to string and again to AInteger(to BigFloat)
    public AFloat add(AFloat other) {
        return new AFloat(this.value.add(other.value).toString());
    }

    // Subtraction BigFloat converting to string and again to AInteger(to BigFloat)
    public AFloat sub(AFloat other) {
        return new AFloat(this.value.subtract(other.value).toString());
    }

    // Multiplication BigFloat converting to string and again to AInteger(to BigFloat)
    public AFloat mul(AFloat other) {
        return new AFloat(this.value.multiply(other.value).toString());
    }

    // Division BigFloat converting to string and again to AInteger(to BigFloat)
    public AFloat div(AFloat other) {
        if (other.value.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero error");
        }
        return new AFloat(this.value.divide(other.value, MathContext.DECIMAL128).toString());
    }

    //Converting to string again
    @Override
    public String toString() {
        return value.toString();
    }
}
