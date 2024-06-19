package support;


import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class RandomData {
        public static String getRandomEmail(String[] args) {
            return randomAlphabetic(10).concat("@example.com").toLowerCase();
        }

        public static String getRandomString() {
            return randomAlphabetic(10).toLowerCase();
        }
    }


