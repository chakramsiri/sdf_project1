import arbitraryarithmetic.AFloat;
import arbitraryarithmetic.AInteger;

public class MyInfArith {
    public static void main(String[] args) {
        String type_number = args[0]; //int/float
        String operation = args[1]; //add/sub/mul/div
        String operand1 = args[2]; //First operand
        String operand2 = args[3]; //second operand

        try {
            if (type_number.equals("int")) {
                AInteger num1 = AInteger.parse(operand1);
                AInteger num2 = AInteger.parse(operand2);
                AInteger result = null;

                switch (operation) {
                    case "add" -> result = num1.add(num2);
                    case "sub" -> result = num1.subtract(num2);
                    case "mul" -> result = num1.multiply(num2);
                    case "div" -> result = num1.divide(num2);
                    default -> {
                        System.out.println("Please enter a valid operation");
                        return;
                    }
                }
                System.out.println(result);
            }
            else if (type_number.equals("float")) {
                AFloat num1 = AFloat.parse(operand1);
                AFloat num2 =AFloat.parse(operand2);
                AFloat result = null;

                switch (operation) {
                    case "add" -> result = num1.add(num2);
                    case "sub" -> result = num1.sub(num2);
                    case "mul" -> result = num1.mul(num2);
                    case "div" -> result = num1.div(num2);
                    default -> {
                        System.out.println("Please enter a valid operation");
                        return;
                    }
                }
                System.out.println(result);
            } 
            else {
                System.out.println("Please enter a valid type");
            }
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }
}
