package edu.virginia.sde.reviews;

public class Credentials {
    private static String username;
    private static String sqliteDataName;
    public static void setUsername(String user) {username = user;}

    public static String getSqliteDataName() {return sqliteDataName;}

    public static void setSqliteDataName(String sqliteDataName) {Credentials.sqliteDataName = sqliteDataName;}

    public static String getUsername() { return username; }


}
