package szilveszterandras.vspf;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Subscriber<T> {
	public String topic;
	public Notifiable<T> notifiable;
	public int hashCode;
	public Subscriber(String topic, Notifiable<T> n, int hashCode) {
		this.topic = topic;
		this.notifiable = n;
		this.hashCode = hashCode;
	}
}

public class Notifier {
	public static final Logger logger = LoggerFactory.getLogger(Notifier.class);
	private static Notifier instance = null;
	@SuppressWarnings("rawtypes")
	private List<Subscriber> subscribers = new ArrayList<Subscriber>();
	
	public Notifier() {};
	
	public <T> void subscribe(String topic, Notifiable<T> n, int hashCode) {
		if (this.getSubscriber(topic, hashCode) == null) {
			this.subscribers.add(new Subscriber<T>(topic, n, hashCode));
			
			logger.debug(String.format("Added notification subscriber on topic %s with hashcode %d", topic, hashCode));
		}
	}
	
	public void unsubscribe(String topic, int hashCode) {
		Subscriber<?> sub = this.getSubscriber(topic, hashCode);
		if (sub != null) {
			this.subscribers.remove(sub);
			logger.debug(String.format("Removed notification subscriber on topic %s with hashcode %d", topic, hashCode));
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> void publish(String topic, T data) {
		@SuppressWarnings("rawtypes")
		List<Subscriber> subs = new ArrayList<Subscriber>(); 
		for (Subscriber<T> s : this.subscribers) {
			if (s.topic.equals(topic)) {
				subs.add(s);
			}
		}
		for (Subscriber<T> s : subs) {
			s.notifiable.onEvent(data);
		}
		logger.debug(String.format("Sent notification on topic %s with payload %s", topic, data));
	}
	@SuppressWarnings("unchecked")
	public <T> Subscriber<T> getSubscriber (String topic, int hashCode) {
		for (Subscriber<T> s : this.subscribers) {
			if (s.topic.equals(topic) && s.hashCode == hashCode) {
				return s;
			}
		}
		return null;
	}
	
	public static Notifier getInstance() {
		if (instance == null) {
			instance = new Notifier();
		}
		return instance;
	}
}
