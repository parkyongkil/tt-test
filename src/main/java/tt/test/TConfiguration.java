package tt.test;

import javax.management.ObjectName;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tt.test.bean.CardServer;
import tt.test.bean.CardServerMap;
import tt.test.jmx.JmxU;
import tt.test.jmx.bean.CardServerMapJmx;
import tt.test.jmx.bean.CardServerMapJmxMXBean;

@Configuration
public class TConfiguration {

	@Bean
	public String testString() {
		return "TEST_STRING";
	}

	@Bean
	public CardServerMap cardServerMap() throws Exception {

		// System.setProperty("java.rmi.server.hostname", "192.168.200.188");
		// -Djava.rmi.server.hostname=127.0.0.1
		String name = CardServerMapJmx.class.getSimpleName();
		ObjectName oname = JmxU.toObjectName(name);
		if (JmxU.isRegistered(oname)) {
			System.out.println("CardServerMap already has been registered.");
			CardServerMapJmxMXBean mxb = JmxU.getMXBean(oname, CardServerMapJmxMXBean.class);
			String[] sa = mxb.getKeys();
			System.out.println(String.format("Old CardServerMap = (%d){%s}", sa.length, StringUtils.join(sa, ", ")));
			for (String s : sa) {
				System.out.println(String.format("Trying to shutdown (%s) ... ", s));
				mxb.shutdown(s);
			}
			JmxU.unregisterMBean(oname);
		} else {
			System.out.println("CardServerMap is creating.");
		}
		CardServerMap m = new CardServerMap();
		m.put("1000", new CardServer(1000));
		CardServerMapJmx xb = new CardServerMapJmx(m);
		JmxU.registerMBean(xb, oname);

		if (JmxU.isRegistered(oname)) {
			CardServerMapJmxMXBean mxb = JmxU.getMXBean(oname, CardServerMapJmxMXBean.class);
			String[] sa = mxb.getKeys();
			System.out.println(String.format("New CardServerMap = (%d){%s}", sa.length, StringUtils.join(sa, ", ")));
		} else {
			System.err.println("CardServerMap has been unregistered.");
		}
		return m;
	}
}
