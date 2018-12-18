package utils;

public class StartStepStopCodes {
    private final static int MAX_COUNT = 32;


    /*
    * @param value encoding number
    * @return: count of leading zeroes in start step stop prefix
    * */
    public static int getCountOfLeadingZeros(int value) {
        int i = 0;
        while (value != 0) {
            value >>>= 1;
            i++;
        }

        i /= 2;

        if (i < 1) {
            return 0;
        }

        if (i > 15) {
            return 14;
        }

        return i - 1;
    }

    public static int getCountOfDigitsByPrefixZeroCount(int zeroCount) {
        if (zeroCount < 0 || zeroCount > 14) throw new IllegalArgumentException(String.valueOf(zeroCount));

        if (zeroCount == 0) return 3;

        if (zeroCount == 14) return 32;

        return 3 + zeroCount * 2;
    }

}
