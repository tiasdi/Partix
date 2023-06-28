package me.x_tias.partix.database;

import me.x_tias.partix.Partix;

import java.sql.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

public class SeasonDb {

    private static final String DATABASE = "season";
    private static Connection connection;
    private static final String url = Databases.getUrl();
    private static final String dbName = Databases.getName();
    private static final String username = Databases.getUsername();
    private static final String password = Databases.getPassword();
    private static final Logger logger = Partix.getInstance().getLogger();


    public static enum Stat {
        WINS(0),
        LOSSES(0),
        POINTS(0);
        private final int defaultValue;

        private Stat(int defaultValue) {
            this.defaultValue = defaultValue;
        }

        public int getDefaultValue() {
            return defaultValue;
        }
    }

    private static Connection getConnection() throws SQLException {
        String connectionUrl = url + dbName + "?autoReconnect=true";

        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);

        return DriverManager.getConnection(connectionUrl, connectionProps);
    }


    public static void setup() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            // Create the `base` table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS `" + DATABASE + "` (" +
                    "`uuid` VARCHAR(36) PRIMARY KEY)";
            statement.execute(createTableQuery);

            // For each stat in the Stat enum, add a column to the `base` table if it doesn't exist
            for (Stat stat : Stat.values()) {
                String columnName = stat.name();
                String addColumnQuery = "ALTER TABLE `" + DATABASE + "` " +
                        "ADD COLUMN IF NOT EXISTS `" + columnName + "` INT DEFAULT " + stat.getDefaultValue();
                statement.execute(addColumnQuery);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to setup database.", e);
        }
    }

    public static void create(UUID playerUUID) {
        if (playerUUID == null) {
            return;
        }
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT IGNORE INTO " + DATABASE + " (uuid) VALUES (?)")) {

            statement.setString(1, playerUUID.toString());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create record.", e);
        }
    }

    public static void add(UUID playerUUID, Stat stat, int amount) {
        if (playerUUID == null) {
            return;
        }
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE " + DATABASE + " SET " + stat.name() + " = " + stat.name() + " + ? WHERE uuid = ?")) {

            statement.setInt(1, amount);
            statement.setString(2, playerUUID.toString());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create record.", e);
        }
    }

    public static void remove(UUID playerUUID, Stat stat, int amount) {
        if (playerUUID == null) {
            return;
        }
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE " + DATABASE + " SET " + stat.name() + " = GREATEST(0, " + stat.name() + " - ?) WHERE uuid = ?")) {

            statement.setInt(1, amount);
            statement.setString(2, playerUUID.toString());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create record.", e);
        }
    }

    public static int get(UUID playerUUID, Stat stat) {
        if (playerUUID == null) {
            return -1;
        }
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT " + stat.name() + " FROM " + DATABASE + " WHERE uuid = ?")) {

            statement.setString(1, playerUUID.toString());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(stat.name());
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get record.", e);
        }
    }

    public static HashMap<Integer, UUID> getTop(Stat stat, int n) {
        HashMap<Integer, UUID> map = new HashMap<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT uuid, " + stat.name() + " FROM " + DATABASE + " ORDER BY " + stat.name() + " DESC LIMIT ?")) {

            statement.setInt(1, n);

            try (ResultSet rs = statement.executeQuery()) {
                int rank = 1;
                while (rs.next()) {
                    map.put(rank, UUID.fromString(rs.getString("uuid")));
                    rank++;
                }
            }
        } catch (Exception ignored) {
        }
        return map;
    }

    public static void setAll(Stat stat, int value) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE " + DATABASE + " SET " + stat.name() + " = ?")) {

            statement.setInt(1, value);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to set all players' value.", e);
        }
    }
}