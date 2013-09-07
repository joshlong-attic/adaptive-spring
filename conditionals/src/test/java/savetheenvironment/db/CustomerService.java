package savetheenvironment.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    private final RowMapper<Customer> customerRowMapper = new RowMapper<Customer>() {
        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Customer(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getLong("id")
            );
        }
    };
    private final JdbcTemplate jdbcTemplate;

    public CustomerService(JdbcTemplate jdbcTemplate) {
        assert jdbcTemplate != null : "you must provide a value to the JdbcTemplate";
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Customer> loadAllCustomers() {
        return jdbcTemplate.query("select * from customer ", this.customerRowMapper);
    }
}