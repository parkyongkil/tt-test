package tt.test;

import javax.management.ObjectName;

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
			System.out.println("Aleady registered");
			CardServerMapJmxMXBean mxb = JmxU.getMXBean(oname, CardServerMapJmxMXBean.class);
			mxb.shutdownAll();
			JmxU.unregisterMBean(oname);
		}
		CardServerMap m = new CardServerMap();
		m.put("1000", new CardServer(1000));
		CardServerMapJmx xb = new CardServerMapJmx(m);
		JmxU.registerMBean(xb, oname);

		if (JmxU.isRegistered(oname)) {
			CardServerMapJmxMXBean mxb = JmxU.getMXBean(oname, CardServerMapJmxMXBean.class);
			String[] sa = mxb.getKeys();
			int n = sa == null ? 0 : sa.length;
			System.out.println("SIXZ = " + n);
			if (n > 0) {
				for (String s : sa)
					System.out.println(String.format("[%s]", s));
			}
		} else {
			System.err.println("Unregistered.");
		}
		return m;
	}
}
