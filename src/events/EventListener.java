package events;

@FunctionalInterface
public interface EventListener {
	void update(String eventType, Object data);
}
