import java.util.ArrayList;

import jminusminus.CLEmitter;

import static jminusminus.CLConstants.*;

/**
 * This class programmatically generates the class file for the following Java application:
 * 
 * <pre>
 * public class IsPrime {
 *     // Entry point.
 *     public static void main(String[] args) {
 *         int n = Integer.parseInt(args[0]);
 *         boolean result = isPrime(n);
 *         if (result) {
 *             System.out.println(n + " is a prime number");
 *         } else {
 *             System.out.println(n + " is not a prime number");
 *         }
 *     }
 *
 *     // Returns true if n is prime, and false otherwise.
 *     private static boolean isPrime(int n) {
 *         if (n < 2) {
 *             return false;
 *         }
 *         for (int i = 2; i <= n / i; i++) {
 *             if (n % i == 0) {
 *                 return false;
 *             }
 *         }
 *         return true;
 *     }
 * }
 * </pre>
 */
public class GenIsPrime {
    public static void main(String[] args) {
        CLEmitter e = new CLEmitter(true);
        // Create an ArrayList instance to store modifiers
        ArrayList<String> modifiers = new ArrayList<String>();

        // public class IsPrime {
        modifiers.add("public");
        e.addClass(modifiers, "IsPrime", "java/lang/Object", null, true);

        // public static void main(String[] args) {
        modifiers.clear();
        modifiers.add("public");
        modifiers.add("static");
        e.addMethod(modifiers, "main", "([Ljava/lang/String;)V", null, true);
        // [Ljava/lang/String; : argTypes : String[] args
        // V : returnType : void
        // null: method does not throw any exceptions
        // true : isSynthetic boolean

        // int n = Integer.parseInt(args[0]);
        e.addNoArgInstruction(ALOAD_0);
        e.addNoArgInstruction(ICONST_0);
        e.addNoArgInstruction(AALOAD);
        e.addMemberAccessInstruction(INVOKESTATIC, "java/lang/Integer", "parseInt",
                "(Ljava/lang/String;)I");
        e.addNoArgInstruction(ISTORE_1); // Store the value of n in location 1

        // boolean result = isPrime(n);
        e.addNoArgInstruction(ILOAD_1); // Load the value of n
        e.addMemberAccessInstruction(INVOKESTATIC, "IsPrime", "isPrime", "(I)I");
        e.addNoArgInstruction(ISTORE_2); // Store the value of result in location 2

        // System.out.println(n)

        // Get System.out on stack
        e.addMemberAccessInstruction(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        // Create an intance (say sb) of StringBuffer on stack for string concatenations
        //    sb = new StringBuffer();
        e.addReferenceInstruction(NEW, "java/lang/StringBuffer");
        e.addNoArgInstruction(DUP);
        e.addMemberAccessInstruction(INVOKESPECIAL, "java/lang/StringBuffer", "<init>", "()V");
        // sb.append(n);
        e.addNoArgInstruction(ILOAD_1); // Load the value of n to print
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer", "append",
                "(I)Ljava/lang/StringBuffer;");


        // if (result) {
        e.addNoArgInstruction(ILOAD_2); // Load result value
        e.addNoArgInstruction(ICONST_1); // Compare with 1 to check if it is true
        e.addBranchInstruction(IF_ICMPEQ, "Prime"); // If equal, jump to "Prime" label

        // sb.append(" is not a prime number");
        e.addLDCInstruction(" is not a prime number");
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuffer;");
        e.addBranchInstruction(GOTO, "endIf");

        //Prime label
        e.addLabel("Prime");
        // sb.append(" is a prime number");
        e.addLDCInstruction(" is a prime number");
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuffer;");

        e.addLabel("endIf");
        // System.out.println(sb.toString());
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/lang/StringBuffer",
                "toString", "()Ljava/lang/String;");
        e.addMemberAccessInstruction(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V");

        // return;
        e.addNoArgInstruction(RETURN);

        // private static boolean isPrime(int n) {
        modifiers.clear();
        modifiers.add("private");
        modifiers.add("static");
        e.addMethod(modifiers, "isPrime", "(I)I", null, true);

        // if n >= 2 goto A:
        e.addNoArgInstruction(ILOAD_0); // Loads n
        e.addNoArgInstruction(ICONST_2); //Loads a constant value: 2
        e.addBranchInstruction(IF_ICMPGE, "A"); // compares if n < 2, then jumps to "A" label.

        // return false;
        e.addNoArgInstruction(ICONST_0); // Loads a constant value: 0
        e.addNoArgInstruction(IRETURN); // return value 0 = false

        // A: i = 2
        e.addLabel("A");
        e.addNoArgInstruction(ICONST_2); // Load a constant value: 2
        e.addNoArgInstruction(ISTORE_1); // Store the value of i in location 1

        // D: if i > n / i goto B :
        e.addLabel("D");
        e.addNoArgInstruction(ILOAD_1); // Load the value of i
        e.addNoArgInstruction(ILOAD_0); // Load the value of n
        e.addNoArgInstruction(ILOAD_1); // Load the value of i
        e.addNoArgInstruction(IDIV); // perform n/i operation
        e.addBranchInstruction(IF_ICMPGT, "B"); // i > n/i comparison

        // if n % i != 0 goto C:
        e.addNoArgInstruction(ICONST_0); // Load a constant value: 0
        e.addNoArgInstruction(ILOAD_0); // Load the value of n
        e.addNoArgInstruction(ILOAD_1); // Load the value of i
        e.addNoArgInstruction(IREM); // perform n%i operation
        e.addBranchInstruction(IF_ICMPNE, "C"); // n % i != 0

        // return false;
        e.addNoArgInstruction(ICONST_0); // Loads a constant value: 0
        e.addNoArgInstruction(IRETURN); // return value 0 = false

        // C: increment i by 1
        e.addLabel("C");
        e.addIINCInstruction(1, 1);
        // e.addNoArgInstruction(ILOAD_1); // Load the value of i
        // e.addNoArgInstruction(ICONST_1); // Load a constant value: 1
        // e.addNoArgInstruction(IADD); // perform i+1 operation
        // e.addNoArgInstruction(ISTORE_1); // Store the new value of i in location 1
        e.addBranchInstruction(GOTO, "D");

        // return True;
        e.addLabel("B");
        e.addNoArgInstruction(ICONST_1); // Load a constant value: 1
        e.addNoArgInstruction(IRETURN); // return value 1 = true

        e.write();
    }
}
