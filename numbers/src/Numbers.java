// Numbers.java CS5125/6025 2017 Cheng
// Various functions for Chapter 2 number theory concepts
// Completed by Kristian Snyder

import java.util.*;

public class Numbers {

    boolean divides(int a, int b) {
        return b % a == 0;
    }

    List<Integer> divisors(int n) {
        List<Integer> results = new ArrayList<Integer>();
        for (int i = 1; i <= n; i++) if (divides(i, n)) results.add(i);
        return results;
    }

    void printDivisors(int n) {
        List<Integer> results = divisors(n);
        System.out.print("Divisors of " + n + ": ");
        for (int i = 0; i < results.size(); i++) {
            System.out.print(results.get(i));
            if (i < results.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("\n");
    }

    void printMatchingDivisors(int a, int b) {
        List<Integer> aDivisors = divisors(a);
        List<Integer> bDivisors = divisors(b);
        aDivisors.retainAll(bDivisors);
        System.out.print("Matching divisors of " + a + " and " + b + ": ");
        System.out.print(aDivisors.toString() + "\n");
    }

    boolean isPrime(int n) {
        int i = 2;
        for (; i < n / 2; i++) if (divides(i, n)) return false;
        return true;
    }

    void listPrimes() {
        for (int i = 2; i < 2000; i++) if (isPrime(i)) System.out.println(i);
    }

    /**
     * Returns a list of prime numbers from the lower bound inclusive
     * to the higher bound exclusive. Returns an empty set if the higher
     * bound is less than or equal to the lower bound.
     *
     * @param lo the lower bound of the range (inclusive)
     * @param hi the higher bound of the range (exclusive)
     * @return the list of primes in the range
     * @see Numbers
     */
    List<Integer> getPrimes(int lo, int hi) {
        List<Integer> results = new ArrayList<Integer>();
        if (lo < 2) {
            lo = 2;
        }
        if (hi <= lo) {
            return results;
        }
        for (int i = lo; i < hi; i++) {
            if (isPrime(i)) {
                results.add(i);
            }
        }
        return results;
    }

    int gcd(int a, int b) {
        if (a < b) {
            int t = a;
            a = b;
            b = t;
        }
        int r = a % b;
        while (r > 0) {
            a = b;
            b = r;
            r = a % b;
        }
        return b;
    }

    boolean relativelyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    int order(int a, int modulus) {
        int m = 1;
        int power = a;
        while (m < modulus && power > 1) {
            power = (power * a) % modulus;
            m++;
        }
        if (m < modulus) return m;
        return -1;
    }

    int totient(int n) {
        int relativelyPrimeNumbers = 1;
        for (int i = 2; i < n; i++) if (relativelyPrime(n, i)) relativelyPrimeNumbers++;
        return relativelyPrimeNumbers;
    }

    List<Integer> coprimes(int n) {
        List<Integer> results = new ArrayList<Integer>();
        for (int i = 1; i < n; i++) {
            if (relativelyPrime(n, i)) {
                results.add(i);
            }
        }
        return results;
    }

    /**
     * Computes the modular exponent of b to exponent p mod n.
     * n must be less than sqrt(max_int).
     *
     * @param b
     * @param exp
     * @param n
     * @return
     */
    int modPow(int b, int exp, int n) {
        int a = b;
        for (int i = 1; i < exp; i++) {
            b = (b * a) % n;
        }
        return b;
    }

    /**
     * Prints the primitive roots for n. If none are found, returns false.
     * Otherwise, returns true.
     * @param n
     * @return
     */
    boolean primitiveRoots(int n) {
        System.out.print("Primitive roots for " + n + ": ");
        List<Integer> coprimesForN = coprimes(n);
        int phi = totient(n);
        boolean found = false;
        for (int i = 1; i < n; i++) {
            List<Integer> pows = new ArrayList<Integer>();
            boolean success = true;
            for (int j = 1; j <= phi; j++) {
                int pow = modPow(i, j, n);
                if (!coprimesForN.contains(pow) || pows.contains(pow)) {
                    success = false;
                    break;
                } else {
                    pows.add(pow);
                }
            }
            if (success) {
                found = true;
                System.out.print(i + " ");
            }
        }
        System.out.print("\n");
        return found;
    }

    void printPrimitiveForm(int n) {
        System.out.print(n + " is of the form ");
        if (n == 2 || n == 4) {
            System.out.print(n);
        } else {
            List<Integer> primes = getPrimes(3, n+1);
            for (Integer prime : primes) {
                for (int i = 1; i < 3; i++) {
                    boolean done = false;
                    int current = prime;
                    int a = 1;
                    while (true) {
                        if (current * i == n) {
                            if (i == 2) System.out.print("2*" + prime + "^" + a);
                            else System.out.print(prime + "^" + a);
                            done = true;
                            break;
                        } else if (current * i > n) {
                            break;
                        }
                        current = current * prime;
                        a += 1;
                    }
                    if (done) {
                        break;
                    }
                }
            }
        }
        System.out.print(".\n");
    }

    void additionTable(int modulus) {
        System.out.print("+");
        for (int i = 0; i < modulus; i++) System.out.print(" " + i);
        System.out.println();
        for (int i = 0; i < modulus; i++) {
            System.out.print(i);
            for (int j = 0; j < modulus; j++) System.out.print(" " + ((i + j) % modulus));
            System.out.println();
        }
        System.out.println();
    }

    void multiplicationTable(int modulus) {
        System.out.print("x");
        for (int i = 0; i < modulus; i++) System.out.print(" " + i);
        System.out.println();
        for (int i = 0; i < modulus; i++) {
            System.out.print(i);
            for (int j = 0; j < modulus; j++) System.out.print(" " + ((i * j) % modulus));
            System.out.println();
        }
        System.out.println();
    }

    void powerTable(int modulus) {
        System.out.print("^");
        for (int i = 2; i < modulus; i++) System.out.print(" " + i);
        System.out.println();
        for (int i = 1; i < modulus; i++) {
            int power = i;
            System.out.print(i);
            for (int j = 2; j < modulus; j++) {
                power = (power * i) % modulus;
                System.out.print(" " + power);
            }
            System.out.println();
        }
        System.out.println();
    }

    void discreteLog(int modulus, int base) {
        int[] logs = new int[modulus];
        int power = base;
        logs[base] = 1;
        for (int i = 2; i < modulus; i++) {
            power = (power * base) % modulus;
            logs[power] = i;
        }
        for (int i = 1; i < modulus; i++)
            System.out.println(i + " = " + base + "^" + logs[i]);
    }

    public static void main(String[] args) {
        Numbers numbers = new Numbers();
        int a = 1160718174;
        int b = 316258250;
        System.out.println("GCD of " + a + " and " + b + " is " + numbers.gcd(a, b));
        numbers.printDivisors(1989);
        numbers.printDivisors(2017);
        numbers.printMatchingDivisors(1989, 2017);
        int lo = 2001;
        int hi = 3000;
        System.out.println("Number of primes between " + (lo - 1) + " and " + hi + ": " +
                numbers.getPrimes(lo, hi).size());
        System.out.println("Totient of 255: " + numbers.totient(255) + "; 256: " + numbers.totient(256) +
                "; 257: " + numbers.totient(257) + "\n");
        numbers.additionTable(8);
        numbers.multiplicationTable(8);
        numbers.powerTable(8);
        for (int i = 2; i < 20; i++) {
            if (numbers.primitiveRoots(i)) {
                numbers.printPrimitiveForm(i);
            }
        }
        System.out.println("\nDiscrete log of 10 for mod 19:");
        numbers.discreteLog(19, 10);
    }
}