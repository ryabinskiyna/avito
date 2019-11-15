package ru.itpark.util;

import ru.itpark.exception.DataAccessException;
import ru.itpark.exception.DataIdGenerationException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplate {
    public static <T> T execute(String url, String sql, PreparedStatementExecutor<T> executor) {
        try (
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            return executor.execute(statement);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public static int executeUpdate(String url, String sql, PreparedStatementSetter setter) {
        return execute(url, sql, stmt -> {
            setter.set(stmt);
            return stmt.executeUpdate();
        });
    }

    public static int executeUpdateReturningId(String url, String sql, PreparedStatementSetter setter) {
        return execute(url, sql, stmt -> {
            setter.set(stmt);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

            throw new DataIdGenerationException();
        });
    }

    public static <T> List<T> executeQuery(String url, String sql, PreparedStatementSetter setter, RowMapper<T> mapper) {
        return execute(url, sql, stmt -> {
            setter.set(stmt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                List<T> result = new LinkedList<>();
                while (resultSet.next()) {
                    result.add(mapper.map(resultSet));
                }
                return result;
            }
        });
    }


    public static <T> Optional<T> executeQueryForObject(String url, String sql, PreparedStatementSetter setter, RowMapper<T> mapper) {
        return execute(url, sql, stmt -> {
            setter.set(stmt);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapper.map(resultSet));
                }

                return Optional.empty();
            }
        });
    }

    public static <T> List<T> executeQuery(String url, String sql, RowMapper<T> mapper) {
        try (
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            List<T> result = new LinkedList<>();
            while (resultSet.next()) {
                result.add(mapper.map(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
