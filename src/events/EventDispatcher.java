package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {
	private final Map<String, List<EventListener>> listeners = new HashMap<>();

	public EventDispatcher() {
		registerEventTypes("client_added", "service_added", "invoice_created");
	}

	private void registerEventTypes(String... eventTypes) {
		for (String eventType : eventTypes) {
			listeners.putIfAbsent(eventType, new ArrayList<>());
		}
	}

	public void subscribe(String eventType, EventListener listener) {
		listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
	}

	public void notify(String eventType, Object data) {
		if (listeners.containsKey(eventType)) {
			for (EventListener listener : listeners.get(eventType)) {
				listener.update(eventType, data);
			}
		}
	}
}
