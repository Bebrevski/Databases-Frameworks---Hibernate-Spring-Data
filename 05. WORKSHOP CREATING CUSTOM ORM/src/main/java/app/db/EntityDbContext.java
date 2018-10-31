package app.db;

import app.db.annotations.Column;
import app.db.annotations.Entity;
import app.db.annotations.PrimaryKey;
import app.db.base.DbContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDbContext<T> implements DbContext<T> {

    private static final String SELECT_QUERY_TEMPLATE = "SELECT * FROM {0}";
    private static final String SELECT_WHERE_QUERY_TEMPLATE = "SELECT * FROM {0} WHERE {1}";
    private static final String SELECT_SINGLE_QUERY_TEMPLATE = "SELECT * FROM {0} LIMIT 1";
    private static final String SELECT_SINGLE_WHERE_QUERY_TEMPLATE = "SELECT * FROM {0} WHERE {1} LIMIT 1";
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO {0}({1}) VALUES({2})";
    private static final String UPDATE_QUERY_TEMPLATE = "UPDATE {0} SET {1} WHERE {2}={3}";

    private static final String SET_QUERY_TEMPLATE = "{0}={1}";
    private static final String WHERE_PRIMARY_KEY = " {0}={1} ";

    private final Connection connection;
    private final Class<T> klass;

    public EntityDbContext(Connection connection, Class<T> klass) {
        this.connection = connection;
        this.klass = klass;
    }

    // Add & Update
    @Override
    public boolean persist(T entity) throws IllegalAccessException, SQLException {
        Field primaryKeyField = getPrimaryKeyField();
        primaryKeyField.setAccessible(true);

        long primaryKey = (long) primaryKeyField.get(entity);

        if (primaryKey > 0) {
            return update(entity);
        }

        return insert(entity);
    }

    private boolean insert(T entity) throws SQLException {
        List<String> columns = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList())
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        String columnName = field.getAnnotation(Column.class).name();

                        Object value = field.get(entity);

                        columns.add(columnName);
                        values.add(value);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        String columnNames = String.join(", ", columns);
        String columnValues = values
                .stream()
                .map(value -> {
                    if (value instanceof String) {
                        return "\'" + value + "\'";
                    }

                    return value;
                })
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String query = MessageFormat.format(
                INSERT_QUERY_TEMPLATE,
                getTableName(),
                columnNames,
                columnValues
        );

        return connection.prepareStatement(query).execute();
    }

    private boolean update(T entity) throws IllegalAccessException, SQLException {
        List<String> updateQuery = getColumnFields()
                .stream()
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        String columnName = field.getAnnotation(Column.class).name();
                        Object value = field.get(entity);

                        if (value instanceof String) {
                            value = "\'" + value + "\'";
                        }

                        return MessageFormat.format(
                                SET_QUERY_TEMPLATE,
                                columnName,
                                value
                        );
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    return null;
                })
                .collect(Collectors.toList());

        String updateQueriesString = String.join(", ", updateQuery);

        Field primaryKey = getPrimaryKeyField();
        primaryKey.setAccessible(true);

        String primaryKeyName = primaryKey.getAnnotation(PrimaryKey.class).name();

        long primaryKeyValue = (long) primaryKey.get(entity);

        String query = MessageFormat.format(
                UPDATE_QUERY_TEMPLATE,
                getTableName(),
                primaryKeyName,
                primaryKey
        );

        return connection.prepareStatement(query).execute();
    }

    private String getTableName() {
        Annotation annotation = Arrays.stream(klass.getAnnotations())
                .filter(a -> a.annotationType() == Entity.class)
                .findFirst()
                .orElse(null);

        if (annotation == null) {
            return klass.getSimpleName().toLowerCase() + "s";
        }

        return klass.getAnnotation(Entity.class).name();
    }

    private Field getPrimaryKeyField() {
        return Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Class " + klass + "does not have a primary key annotation"));
    }


    @Override
    public List<T> find() throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return find(null);
    }

    @Override
    public List<T> find(String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String queryTemplate = where == null
                ? SELECT_QUERY_TEMPLATE
                : SELECT_WHERE_QUERY_TEMPLATE;

        return find(queryTemplate, where);
    }

    @Override
    public List<T> find(String template, String where) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String queryString = MessageFormat.format(
                template,
                getTableName(),
                where
        );

        PreparedStatement ps = connection.prepareStatement(queryString);
        ResultSet resultSet = ps.executeQuery();

        return toList(resultSet);
    }

    private List<T> toList(ResultSet resultSet) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<>();

        while (resultSet.next()) {
            T entity = this.createEntity(resultSet);
        }

        return result;
    }

    private T createEntity(ResultSet resultSet) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, SQLException {
        T entity = klass.getConstructor().newInstance();
        List<Field> columnFields = getColumnFields();

        Field primaryKeyField = getPrimaryKeyField();

        primaryKeyField.setAccessible(true);
        String primaryKeyColumnName = primaryKeyField.getAnnotation(PrimaryKey.class)
                .name();
        long primaryKeyValue = resultSet.getLong(primaryKeyColumnName);
        primaryKeyField.set(entity, primaryKeyValue);

        columnFields.forEach(field -> {
            String columnName = field.getAnnotation(Column.class)
                    .name();
            try {
                field.setAccessible(true);
                if (field.getType() == Long.class || field.getType() == long.class) {
                    long value = resultSet.getLong(columnName);
                    field.set(entity, value);
                } else if (field.getType() == String.class) {
                    String value = resultSet.getString(columnName);
                    field.set(entity, value);
                }
            } catch (SQLException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        return entity;
    }

    private List<Field> getColumnFields(){
        return Arrays.stream(klass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());
    }

    @Override
    public T findFirst() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(SELECT_SINGLE_QUERY_TEMPLATE, null).get(0);
    }

    @Override
    public T findFirst(String where) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
       return find(SELECT_SINGLE_WHERE_QUERY_TEMPLATE, where).get(0);
    }

    @Override
    public T findFirst(long id) throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String primaryKeyName =
                getPrimaryKeyField().getAnnotation(PrimaryKey.class)
                        .name();

        String whereString = MessageFormat.format(
                WHERE_PRIMARY_KEY,
                primaryKeyName,
                id
        );

        return findFirst(whereString);
    }
}
