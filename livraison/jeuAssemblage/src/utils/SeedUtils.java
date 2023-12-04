package utils;

public final class SeedUtils {
    
    private SeedUtils() {
        
    }

    public static long generateRandomSeed() {

        return (long) (Math.random() * Long.MAX_VALUE);
    }
}
