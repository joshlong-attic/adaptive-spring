package savetheenvironment.conditionals;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Josh Long
 */
@ManagedResource
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
    private JdbcTemplate jdbcTemplate;

    public  CustomerService() {
    }

    public CustomerService(JdbcTemplate jdbcTemplate) {
        assert jdbcTemplate != null : "you must provide a value to the JdbcTemplate";
        this.jdbcTemplate = jdbcTemplate;
    }

    @ManagedOperation
    public List<Customer> loadAllCustomers() {
        List<Customer> customerList =  jdbcTemplate.query(
                "select * from customer ", this.customerRowMapper);
        return customerList;
    }
}
