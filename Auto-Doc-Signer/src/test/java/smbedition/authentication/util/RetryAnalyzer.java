//package smbedition.authentication.util;
//
//import org.testng.IRetryAnalyzer;
//import org.testng.ITestResult;
//
//public class RetryAnalyzer implements IRetryAnalyzer {
//
//    private int retryCount = 0;
//    private static final int maxRetryCount = 3; // retry up to 3 times
//    private static final long baseDelay = 3000; // 2 seconds
//
//    @Override
//    public boolean retry(ITestResult result) {
//        if (retryCount < maxRetryCount) {
//            retryCount++;
//
//            // Exponential backoff: 2s, 4s, 8s
//            long delay = baseDelay * (1L << (retryCount - 1));
//
//            System.out.println("Retrying test " + result.getName() +
//                    " due to failure. Attempt #" + retryCount +
//                    " after " + delay + "ms");
//
//            try {
//                Thread.sleep(delay);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//            return true; // re-run the test
//        }
//        return false; // stop retrying
//    }
//}
