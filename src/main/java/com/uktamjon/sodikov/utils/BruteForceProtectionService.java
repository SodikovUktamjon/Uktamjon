    package com.uktamjon.sodikov.utils;

    import java.util.Map;
    import java.util.concurrent.ConcurrentHashMap;

    import org.springframework.stereotype.Service;

    @Service
    public class BruteForceProtectionService {

        public static final int MAX_ATTEMPTS = 3;
        public static final long LOCKOUT_DURATION = 10 * 1000; // 1 minute

        public final Map<String, BruteForceEntry> bruteForceMap = new ConcurrentHashMap<>();

        public void recordFailedLogin(String username) {
            bruteForceMap.compute(username, (key, value) -> {
                if (value == null) {
                    value = new BruteForceEntry();
                }
                value.incrementAttempts();
                return value;
            });
        }

        public boolean isUserBlocked(String username) {
            BruteForceEntry entry = bruteForceMap.get(username);
            return entry != null && entry.isBlocked();
        }

        public static class BruteForceEntry {
            private int attempts = 0;
            private long blockUntilTimestamp = 0;

            public void incrementAttempts() {
                attempts++;
                if (attempts >= MAX_ATTEMPTS) {
                    blockUntilTimestamp = System.currentTimeMillis() + LOCKOUT_DURATION;
                }
            }

            public boolean isBlocked() {
                return blockUntilTimestamp > System.currentTimeMillis();
            }
        }
    }
