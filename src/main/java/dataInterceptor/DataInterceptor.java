package dataInterceptor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.testng.IDataProviderInterceptor;
import org.testng.IDataProviderMethod;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;

public class DataInterceptor implements IDataProviderInterceptor {

	@Override
	public Iterator<Object[]> intercept(Iterator<Object[]> original, IDataProviderMethod dataProviderMethod,
			ITestNGMethod method, ITestContext iTestContext) {

		Optional<String> multiplyFactor = extractAttributeValue(method, "multiply_factor");
		Optional<String> testType = extractAttributeValue(method, "test_type");
		Optional<String> dataEditor = extractAttributeValue(method, "data_editor");
		Optional<String> wantedData = extractAttributeValue(method, "wanted_data");

		if (wantedData.isPresent() && multiplyFactor.isPresent()) {
			int factor = Integer.parseInt(multiplyFactor.get());
			original = applyMultiplication(original, factor, wantedData.get());
		}
		if (dataEditor.isPresent()) {
			original = applyDataEditing(original);
		}
		if (testType.isPresent()) {
			original = applyFiltering(original, testType.get());
		}
		return original;
	}

	private Optional<String> extractAttributeValue(ITestNGMethod method, String attributeName) {
		return Arrays.stream(method.getAttributes()).filter(it -> attributeName.equalsIgnoreCase(it.name()))
				.flatMap(it -> Arrays.stream(it.values())).findFirst();
	}

	private Iterator<Object[]> applyMultiplication(Iterator<Object[]> originalIterator, int factor,
			String wantedDataCondition) {
		Predicate<Object> predicate = Predicates.getPredicate(wantedDataCondition);
		List<Object[]> resultList = new LinkedList<>();

		while (originalIterator.hasNext()) {
			Object[] data = originalIterator.next();
			if (predicate.test(data)) {
				for (int i = 0; i < factor; i++) {
					resultList.add(data);
				}
			}
		}
		return resultList.iterator();
	}

	private Iterator<Object[]> applyDataEditing(Iterator<Object[]> originalIterator) {
		Consumer<Object[]> consumer = Consumers.getConsumer();

		List<Object[]> modifiedData = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(originalIterator, Spliterator.ORDERED), false).map(data -> {
					consumer.accept(data);
					return data;
				}).collect(Collectors.toList());

		return modifiedData.iterator();
	}

	private Iterator<Object[]> applyFiltering(Iterator<Object[]> originalIterator, String testTypeCondition) {
		Predicate<Object> predicate = Predicates.getPredicate(testTypeCondition);

		Spliterator<Object[]> split = Spliterators.spliteratorUnknownSize(originalIterator, Spliterator.ORDERED);
		return StreamSupport.stream(split, false).filter(predicate).collect(Collectors.toList()).iterator();
	}
}
