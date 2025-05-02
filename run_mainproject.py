import subprocess
import sys
#Check for required arguments
if len(sys.argv) < 5:
    print("Please check the amount of arguments passed.")
    sys.exit(1)
#taking inputs
type_number = sys.argv[1] #int/float
operation = sys.argv[2] #add/sub/mul/div
operand1 = sys.argv[3] #First operand
operand2 = sys.argv[4] #second operand
#compiling the java command line 
compile_command = [
    "javac", "-d", ".",  "src/main/java/arbitraryarithmetic/AInteger.java", "src/main/java/arbitraryarithmetic/AFloat.java", "src/main/java/MyInfArith.java"
]

compilation = subprocess.run(compile_command)
#running the command
run_command = [
    "java", "-cp", ".", "MyInfArith", type_number, operation, operand1, operand2
]

subprocess.run(run_command)
