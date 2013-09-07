package savetheenvironment.jmx;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.StringUtils;
import savetheenvironment.Cat;
import savetheenvironment.CatAdmin;
import savetheenvironment.refresh.*;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { JmxCatConfiguration.class})
public class JmxRefreshableBeanFactoryBeanTest {

    @Inject
    private Cat cat;
    @Inject
    private CatAdmin catAdmin;

    @Test
    public void testRefreshableBeans() {

        RefreshableFactoryBean.Refreshable refreshableBean = (RefreshableFactoryBean.Refreshable) cat;

        Assert.assertTrue(StringUtils.isEmpty(cat.getName()));

        final String name1 = "Scratch", name2 = "Fluffy";

        String existingValueOfName = cat.getName();
        catAdmin.updateCat(name1);
        Assert.assertEquals(cat.getName(), existingValueOfName);

        refreshableBean.refresh();

        Assert.assertEquals(cat.getName(), name1);


        catAdmin.updateCat(name2);
        Assert.assertEquals(cat.getName(), name1);
        refreshableBean.refresh();
        Assert.assertEquals(cat.getName(), name2);
    }
}


