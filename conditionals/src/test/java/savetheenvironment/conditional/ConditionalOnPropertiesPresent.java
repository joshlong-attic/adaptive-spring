package savetheenvironment.conditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * Looks for configuration properties of a known format and - if they're
 * present - permits the creation of the bean.
 *
 * @author Josh Long
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnPropertiesPresentCondition.class)
public @interface ConditionalOnPropertiesPresent {
    /*
     * the root for the configuration properties, e.g, the value $ROOT in the expression <CODE>$ROOT.username</CODE>.
     */
    String [] value() default "";
}
