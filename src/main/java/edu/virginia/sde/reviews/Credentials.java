package edu.virginia.sde.reviews;

public class Credentials {
    private static String username;
    private static String sqliteDataName;
    private static String appName;

    public static String getAppName() {return appName;}
    public static void setAppName(String appName) {Credentials.appName = appName;}
    public static void setUsername(String user) {username = user;}
    public static String getSqliteDataName() {return sqliteDataName;}
    public static void setSqliteDataName(String sqliteDataName) {Credentials.sqliteDataName = sqliteDataName;}
    public static String getUsername() { return username; }


}
