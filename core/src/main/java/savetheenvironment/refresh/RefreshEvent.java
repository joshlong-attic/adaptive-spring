package savetheenvironment.refresh;

import org.springframework.context.ApplicationEvent;

public class RefreshEvent extends ApplicationEvent {
    public Object getRefreshedObject() {
        return this.getSource();
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public RefreshEvent(Object source) {
        super(source);
    }
}
