package tt.test.jmx.bean;

public interface CardServerControllerMBean {

	String[] getKeys();

	void shutdown(String key);

	void shutdownAll();
}
