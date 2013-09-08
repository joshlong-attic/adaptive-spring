package savetheenvironment.conditional;

import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

/**
 * Looks for properties of the form {@code $db.username}, {@code $db.password}, etc.
 *
 * @author Josh Long
 */
public class OnPropertiesPresentCondition extends SpringBootCondition {
    private String[] keys = {};

    protected Class<? extends Annotation> getConditionalAnnotationType() {
        return ConditionalOnPropertiesPresent.class;
    }

    @Override
    public Outcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        keys = (String[]) metadata.getAnnotationAttributes(
                getConditionalAnnotationType().getName()).get("value");
        Environment environment = context.getEnvironment();

        String[] props = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            props[i] = environment.getProperty(keys[i]);
        }
        boolean allPropertiesPresent = allNotNull(props);
        return allPropertiesPresent ?
                Outcome.match() :
                Outcome.noMatch("the properties " + StringUtils.arrayToCommaDelimitedString(keys) + " were not present.");
    }

    private boolean allNotNull(String... args) {
        if (args == null || args.length == 0)
            return false;
        for (String x : args)
            if (x == null)
                return false;
        return true;
    }
}
