package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {
	// container to hold lahat ng events and its corresponding listeners
	// ang isang event pwede na merong maraming listeners
	private final Map<String, List<EventListener>> listeners = new HashMap<>();

	// Registers all event types at the time of instantiation
	public EventDispatcher() {
		registerEventTypes("client_added", "service_added", "invoice_created");
	}

	// actual logic sa pag register ng events
	private void registerEventTypes(String... eventTypes) {
		for (String eventType : eventTypes) {
			listeners.putIfAbsent(eventType, new ArrayList<>()); // empty list of listeners muna
		}
	}

	// assign a listener sa isang event
	public void subscribe(String eventType, EventListener listener) {
		listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
	}

	// Notifies all listeners subscribed to a specific event type
	public void notify(String eventType, Object data) {
		if (listeners.containsKey(eventType)) {
			for (EventListener listener : listeners.get(eventType)) {
				listener.update(eventType, data);
			}
		}
	}
}
