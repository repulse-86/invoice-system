package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventDispatcher {
	private final Map<String, List<EventListener>> listeners = new HashMap<>();

	public EventDispatcher() {
		listeners.put("client_added", new ArrayList<>());
		listeners.put("service_added", new ArrayList<>());
		listeners.put("invoice_created", new ArrayList<>());
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
