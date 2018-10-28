package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class JdbcDataRepository<T> implements DataRepository {

    @Override
    public void insert(Object object) throws SQLException {
        List<String> values = (this.getValues((T) object));
        StringBuilder param = new StringBuilder();
        for (int i = 0; i <values.size() ; i++) {
            param.append("?, ");
        }
        param.deleteCharAt(param.length()-1);
        param.deleteCharAt(param.length()-1);

        String queryString =
                "INSERT INTO " + this.getTableName() +
                        " (" + this.getColumns()+ ")"
                        + "VALUES ("+ param + ")";
        PreparedStatement query =
                connection.prepareStatement(queryString);
        for (int i = 0; i <values.size() ; i++) {


            query.setString(i+1, values.get(i));
        }

        query.executeUpdate();
    }

    private final Connection connection;

    public JdbcDataRepository(Connection connection) {
        this.connection= connection;
    }

    @Override
    public List<T> getAll() throws SQLException {
       String queryString = "SELECT * FROM " + this.getTableName();
        PreparedStatement query =
                connection.prepareStatement(queryString);
       ResultSet resultSet= query.executeQuery();
      // List<T> list = toList(resultSet);
       return toList(resultSet);
       
    }



    protected abstract List<String> getValues(T object);

    protected abstract String getColumns();

    private List<T> toList(ResultSet resultSet) throws SQLException {

        List<T> list = new ArrayList<>();
        while (resultSet.next()){
            T object = this.parseRow(resultSet);
            list.add(object);

        }
        return list;
    }

    protected abstract T parseRow(ResultSet resultSet) throws SQLException;

    protected abstract String getTableName();


}
