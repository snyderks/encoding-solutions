// PolynomialsB.java CS6025 Cheng 2017
// Outputs primitive elements of GF(2^n) with different irreducibles
// Usage: java PolynomialsB
// Completed by Kristian Snyder

import java.io.*;
import java.util.*;

public class PolynomialsB{

    private static final int numberOfBits = 8;
    private static final int fieldSize = 1 << numberOfBits;
    private static final List<Integer> factors = findFactors(fieldSize-1);
    private static final int[] irreducibles = new int[]{  // all degree 8 irreducible polynomials
            0x11b, 0x11d, 0x12b, 0x12d, 0x139, 0x13f, 0x14d, 0x15f, 0x163, 0x165, 0x169, 0x171, 0x177, 0x17b, 0x187, 0x18b, 0x18d, 0x19f, 0x1a3, 0x1a9, 0x1b1, 0x1bd, 0x1c3, 0x1cf, 0x1d7, 0x1dd, 0x1e7, 0x1f3, 0x1f5, 0x1f9 };

    private int modMultiply(int a, int b, int m){
        int product = 0;
        for (; b > 0; b >>= 1){
            if ((b & 1) > 0) product ^= a;
            a <<= 1;
            if ((a & fieldSize) > 0) a ^= m;
        }
        return product;
    }

    private static List<Integer> findFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        // n is always a factor of n
        factors.add(n);
        for (int i = 1; i < n/2; i++) {
            if (n % i == 0) { factors.add(i); }
        }
        return factors;
    }

    private void findPrimitiveElements(int irreducible){
        // Currently this function prints orders of all elements from 2 to 255
        // You need to modify it so only primitive elements smaller than 10 are printed.
        List<Integer> primitives = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            int power = i;
            int j = 2; for (; j < fieldSize; j++) {
                power = modMultiply(i, power, irreducible);
                if (power == 1) break;
            }
            // If the order reached was a factor of 2^8 - 1, try it
            if (factors.contains(j)) {
                power = i;
                List<Integer> found = new ArrayList<>();
                found.add(power);
                j = 2; for (; j < fieldSize; j++) {
                    power = modMultiply(i, power, irreducible);
                    if (!found.contains(power)) {
                        found.add(power);
                    }
                }

                if (found.size() == fieldSize - 1) {
                    primitives.add(i);
                }
            }
        }
        // Print the primitives
        System.out.print("Primitives for " + Integer.toBinaryString(irreducible) + ": ");
        for (int index = 0; index < primitives.size(); index++) {
            System.out.print(primitives.get(index));
            if (index < primitives.size() - 1) { System.out.print(", "); }
        }
        System.out.println();
    }

    void smallPrimitiveElements(){  // call this function from main instead
        for (int irreducible : irreducibles){
            findPrimitiveElements(irreducible);
        }
    }


    public static void main(String[] args){
        PolynomialsB p = new PolynomialsB();
        p.smallPrimitiveElements();
    }
}