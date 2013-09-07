package savetheenvironment.conditionals;

import java.io.Serializable;

public class Customer implements Serializable {
    static final long serialVersionUID = 5L  ;
    public Customer(){}

    private String firstName, lastName;

    private long id;

    public Customer(String firstName, String lastName, long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getId() {
        return id;
    }



}
