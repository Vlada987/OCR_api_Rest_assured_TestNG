package dataInterceptor;

import java.util.Optional;
import java.util.function.Predicate;

public class Predicates {
	
	public static Predicate<Object> getPredicate(String filterValue) {
	    String filterLower = filterValue.toLowerCase();
	    return object -> Optional.ofNullable(object)
	            .filter(o -> o.getClass().isArray())
	            .map(o -> (Object[]) o)
	            .map(row -> (String) row[2])
	            .filter(testCase -> filterLower.equalsIgnoreCase(testCase))
	            .isPresent();
	}
			


}
