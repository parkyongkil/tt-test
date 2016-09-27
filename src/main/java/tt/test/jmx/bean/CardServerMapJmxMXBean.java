package tt.test.jmx.bean;

public interface CardServerMapJmxMXBean {

	String[] getKeys();

	void shutdown(String key);

	void shutdownAll();
}
