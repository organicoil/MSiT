package ua.nure.msit.dvortsov.wumpus.util;

public abstract class Const {

    public abstract static class Room {
        public static class Events {
            public static final String START = "start";
            public static final String WUMPUS = "wumpus";
            public static final String PIT = "pit";
            public static final String BREEZE = "breeze";
            public static final String STENCH = "stench";
            public static final String SCREAM = "scream";
            public static final String GOLD = "gold";
            public static final String BUMP = "bump";
        }

        public abstract static class Status {
            public static int TRUE = 1;
            public static int FALSE = 2;
            public static int POSSIBLE = 3;
            public static int NO_GOLD_WAY = 4;
            public static int NO_STATUS = -1;
        }
    }

}
