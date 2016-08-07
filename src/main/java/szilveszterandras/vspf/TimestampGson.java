package szilveszterandras.vspf;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

class TimestampSerializer implements JsonSerializer<Date> {

	@Override
	public JsonElement serialize(Date date, Type typeofDate, JsonSerializationContext context) {
		return new JsonPrimitive(date.getTime());
	}
}

public class TimestampGson {
	private Gson gson;

	public TimestampGson() {
		this.gson = new GsonBuilder().registerTypeAdapter(Date.class, new TimestampSerializer()).create();
	}
	public String toJson(Object obj) {
		return gson.toJson(obj);
	}
}
