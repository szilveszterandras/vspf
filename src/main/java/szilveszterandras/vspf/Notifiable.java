package szilveszterandras.vspf;

public interface Notifiable<T> {
	public void onEvent(T data);
}