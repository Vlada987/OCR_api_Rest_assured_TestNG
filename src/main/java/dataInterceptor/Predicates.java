package dataInterceptor;

import java.util.function.Predicate;

public class Predicates {

	public static Predicate<Object> getPredicate(String clazzName) {
		switch (clazzName.toLowerCase()) {
		case "authenticationfilter":
			return object -> {
				if (object.getClass().isArray()) {
					Object[] row = (Object[]) object;
					String testType = (String) row[2];
					return "api_key_test".equalsIgnoreCase(testType);
				}
				return false;
			};

		case "servicefilter":
			return object -> {
				if (object.getClass().isArray()) {
					Object[] row = (Object[]) object;
					String testType = (String) row[2];
					return "service_test".equalsIgnoreCase(testType);
				}
				return false;
			};

		case "onlyvalid":
			return object -> {
				if (object.getClass().isArray()) {
					Object[] row = (Object[]) object;
					String testType = (String) row[1];
					return "the user is able to use OCR with valid api key".equalsIgnoreCase(testType);
				}
				return false;
			};

		default:
			return input -> false;
		}
	}

//	public static Predicate<Object> getPredicate(String clazzName) {
//	    try {
//	        return (Predicate<Object>) Class.forName(clazzName)
//	                .getDeclaredConstructor()
//	                .newInstance();
//	    } catch (Exception e) {
//	        return input -> false;
//	    }
//	}
//
//	
//	
//	
//	public class AuthenticationFilter implements Predicate<Object> {
//
//	    @Override
//	    public boolean test(Object object) {
//	        if (object.getClass().isArray()) {
//	        	
//	            Object[] row = (Object[]) object;
//	            String testType = (String) row[2];
//	            
//	            return "api_key_test".equalsIgnoreCase(testType);
//	        }
//	        return false;
//	    }
//	}
//
//	public class ServiceTestFilter implements Predicate<Object> {
//
//	    @Override
//	    public boolean test(Object object) {
//	        if (object.getClass().isArray()) {
//	            Object[] row = (Object[]) object;
//	            String testType = (String) row[2];
//	            return "service_test".equalsIgnoreCase(testType);
//	        }
//	        return false;
//	    }
//	}
//
//	

}
