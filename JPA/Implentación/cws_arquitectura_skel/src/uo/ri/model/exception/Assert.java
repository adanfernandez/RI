package uo.ri.model.exception;

public class Assert {

	public static void isNotNull(Object o, String errorMsg)
			throws BusinessException {
		isTrue( o != null, errorMsg );
	}

	public static void isNotNull(Object o) throws BusinessException {
		isTrue( o != null, o.getClass().getName() + " cannot be null here" );
	}

	public static void isFalse(boolean condition) throws BusinessException {
		isTrue( !condition, "Invalid assertion" );
	}

	public static void isFalse(boolean condition, String errorMsg)
			throws BusinessException {
		isTrue( !condition, errorMsg );
	}

	public static void isTrue(boolean condition) throws BusinessException {
		isTrue( condition, "Invalid assertion" );
	}

	public static void isTrue(boolean condition, String errorMsg)
			throws BusinessException {
		if (condition == true)
			return;
		throw new BusinessException( errorMsg );
	}

}
