package utils;

public class StartStepStopCodes {
    private final static int MAX_COUNT = 32;

    public void getCountOfLeadingZeros(int value) {
        int i = 0;
        while (value != 0) {
            value >>>= 1;
            i++;
        }
    }

}
