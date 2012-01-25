
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;


/**
 * Used in {@link PlayerCommands} to determine which strings invoke the annotated
 * {@link BaseCommand}
 * @author 14mRh4X0r
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Command {

    /**
     * The command names
     * @return value
     */
    String[] value() default "";
}
