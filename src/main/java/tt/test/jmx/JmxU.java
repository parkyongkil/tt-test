package tt.test.jmx;

import java.lang.management.ManagementFactory;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxU {

	static MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	public static void registerMBean(Object o, ObjectName oname) throws Exception {
		mbs.registerMBean(o, oname);
	}

	public static boolean isRegistered(ObjectName oname) {
		return mbs.isRegistered(oname);
	}

	public static void unregisterMBean(ObjectName oname) throws Exception {
		mbs.unregisterMBean(oname);
	}

	public static <X> X getMBean(ObjectName oname, Class<X> c) throws Exception {
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
		return JMX.newMBeanProxy(mbsc, oname, c);
	}

	public static <X> X getMXBean(ObjectName oname, Class<X> c) throws Exception {
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
		JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
		MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
		return JMX.newMXBeanProxy(mbsc, oname, c);
	}

	public static ObjectName toObjectName(String name) throws Exception {
		return new ObjectName(String.format("card:type=%s", name));
	}
}
