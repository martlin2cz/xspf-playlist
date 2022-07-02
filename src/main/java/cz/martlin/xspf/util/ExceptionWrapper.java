package cz.martlin.xspf.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An utility, which encapsulates the checked {@link XSPFException} to uncecked
 * {@link XSPFRuntimeException} in context, where the checked exception is not
 * allowed.
 * 
 * Use as follows:
 * 
 * <pre>
 * something
 * .map(ExceptionWrapper.wrapFunction(f -> new FileInputStream(f)))
 * .filter(ExceptionWrapper.wrapPredicate(ins -> ins.read() > 0))
 * .forEach(ExceptionWrapper.wrapConsumer(ins -> ins.close()))
 * 
 * <pre>
 * 
 * @author martin
 *
 */
public class ExceptionWrapper {

	/**
	 * Wraps the given consumer (returns consumer catching all its exceptions and
	 * rethrowing as {@link XSPFRuntimeException}).
	 * 
	 * @param <T>
	 * @param consumer
	 * @return
	 */
	public static <T> Consumer<T> wrapConsumer(ConsumerThrowing<T> consumer) {
		return (x) -> {
			try {
				consumer.consume(x);
			} catch (Exception e) {
				throw new XSPFRuntimeException(e);
			}
		};
	}

	/**
	 * Wraps the given function (returns function catching all its exceptions and
	 * rethrowing as {@link XSPFRuntimeException}).
	 * 
	 * @param <IT>
	 * @param <OT>
	 * @param function
	 * @return
	 */
	public static <IT, OT> Function<IT, OT> wrapFunction(FunctionThrowing<IT, OT> function) {
		return (x) -> {
			try {
				return function.apply(x);
			} catch (Exception e) {
				throw new XSPFRuntimeException(e);
			}
		};
	}

	/**
	 * Wraps the given predicated (returns predicate catching all its exceptions and
	 * rethrowing as {@link XSPFRuntimeException}).
	 * 
	 * @param <T>
	 * @param predicate
	 * @return
	 */
	public static <T> Predicate<T> wrapPredicate(PredicateThrowing<T> predicate) {
		return (x) -> {
			try {
				return predicate.test(x);
			} catch (Exception e) {
				throw new XSPFRuntimeException(e);
			}
		};
	}

///////////////////////////////////////////////////////////////////////////////

	/**
	 * An {@link Consumer}, but with throwing exception.
	 * 
	 * @author martin
	 *
	 * @param <T>
	 */
	@FunctionalInterface
	public static interface ConsumerThrowing<T> {
		public void consume(T item) throws Exception;
	}

	/**
	 * An {@link Function}, but with throwing exception.
	 * 
	 * @author martin
	 *
	 * @param <IT>
	 * @param <OT>
	 */
	@FunctionalInterface
	public static interface FunctionThrowing<IT, OT> {
		public OT apply(IT item) throws Exception;
	}

	/**
	 * An {@link Predicate}, but with throwing exception.
	 * 
	 * @author martin
	 *
	 * @param <T>
	 */
	@FunctionalInterface
	public interface PredicateThrowing<T> {
		public boolean test(T item) throws Exception;

	}
}
