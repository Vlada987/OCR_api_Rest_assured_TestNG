package dataInterceptor;

import java.util.function.Consumer;

public class Consumers {

	public static Consumer<Object[]> getConsumer() {
		return object -> {
			if (object.getClass().isArray()) {
				Object[] row = (Object[]) object;
				if (row.length > 5) {
					if ("api_key_null".equals(row[5])) {
						row[5] = null;
						System.out.println(row[5]);
					} else if ("api_key_empty".equals(row[5])) {
						row[5] = "";

					}
				}
			}
		};
	}
}
