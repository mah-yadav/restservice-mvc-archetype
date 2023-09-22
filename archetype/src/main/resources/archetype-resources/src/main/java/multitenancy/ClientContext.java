#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.multitenancy;


public class ClientContext {

	private static final ThreadLocal<String> CURRENT_CLIENT = new InheritableThreadLocal<>();

	public static String getCurrentClient() {
		return CURRENT_CLIENT.get();
	}

	public static void setCurrentClient(String clientId) {
		CURRENT_CLIENT.set(clientId);
	}

	public static void clear() {
		CURRENT_CLIENT.remove();
	}
}
