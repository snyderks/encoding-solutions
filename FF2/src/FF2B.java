import java.math.BigInteger;

/**
 * FF2B - Reverses encryption defined in FF2A.
 * Largely reuses FF2A class methods and variables.
 * Created by kristian on 2/13/17.
 */
public class FF2B extends FF2A {
    void FF2rounds(){  // step 5 of Algorithm FF2, Figure 7.15
        byte[] blockInBytes = new byte[blockSize + 1]; // for BigInteger use
        // ------BEGIN CHANGES------
        // NOTE: Looping from 9 to 0 instead of 0 to 9
        for (int i = 9; i >= 0; i--){
        // ------END CHANGES------
            state[0] = i;  // making Q in Figure 7.15 5.i
            byte[] byteArray = num(XB).toByteArray();
            int len = byteArray.length;
            for (int j = 0; j < len; j++){
                int t = byteArray[j];
                if (t < 0) t += 256;
                state[blockSize - len + j] = t;
            }
            for (int j = 1; j < blockSize -len; j++) state[j] = 0;
            blockCipher();	 // Y <- CIPH_J(Q), 5.ii
            for (int j = 0; j < blockSize; j++) blockInBytes[j + 1] = (byte)(state[j]);
            blockInBytes[0] = 0;  // so that y is not negative
            BigInteger y = new BigInteger(blockInBytes);
            int m = (i % 2 == 0) ? u : v;  // 5.iv
            // ------BEGIN CHANGES------
            // NOTE: changing addition to subtraction
            BigInteger c = num(XA).subtract(y);  // 5.vi
            // ------END CHANGES------
            int[] C = new int[m];  // 5.vii
            for (int j = m-1; j >= 0; j--) {
                BigInteger[] qr = c.divideAndRemainder(radix);
                C[j] = qr[1].abs().intValue();
                c = qr[0];
            }
            XA = XB; XB = C; // 5.viii and 5.ix
        }
    }

    private void decrypted(){
        for (int i = 0; i < XA.length; i++) {
            System.out.print((char)(XA[i] + 'a'));
        }
        for (int i = 0; i < XB.length; i++) {
            System.out.print((char)(XB[i] + 'a'));
        }
        System.out.println();
    }

    public static void main(String[] args){
        if (args.length < 2){
            System.err.println("Usage: java FF2A keyfile string");
            return;
        }
        FF2B ff2 = new FF2B();
        ff2.makeLog();
        ff2.buildS();
        ff2.readKey(args[0]);
        ff2.expandKey();
        ff2.getString(args[1]);
        ff2.computeJ();
        ff2.FF2rounds();
        ff2.decrypted();
    }
}
