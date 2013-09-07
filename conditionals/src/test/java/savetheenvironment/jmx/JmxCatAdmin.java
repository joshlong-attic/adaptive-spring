package savetheenvironment.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import savetheenvironment.CatAdmin;

@ManagedResource
public class JmxCatAdmin extends CatAdmin {

    @Override
    @ManagedOperation
    public void updateCat(String name) {
        super.updateCat(name);
    }
}
