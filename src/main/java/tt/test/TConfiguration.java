package tt.test;

import java.util.Set;

import javax.management.ObjectName;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tt.test.bean.CardServer;
import tt.test.bean.CardServerHandler;
import tt.test.bean.CardServerHandlerMap;
import tt.test.bean.CardServerMapMBean;
import tt.test.jmx.JmxU;
import tt.test.jmx.bean.CardServerController;
import tt.test.jmx.bean.CardServerControllerMBean;
import tt.test.jmx.bean.CardServerMapJmx;
import tt.test.jmx.bean.CardServerMapJmxMXBean;

@Configuration
public class TConfiguration {

	@Bean
	public String testString() {
		return "TEST_STRING";
	}

	@Bean
	public CardServerHandlerMap cardServerHandlerMap() throws Exception {

		String name = CardServerMapMBean.class.getSimpleName();
		ObjectName oname = JmxU.toObjectName(name);
		if (JmxU.isRegistered(oname)) {
			System.out.println("CardServerMap already has been registered.");
			CardServerMapMBean mxb = JmxU.getMBean(oname, CardServerMapMBean.class);
			Set<String> ks = mxb.keySet();
			System.out.println(String.format("Old CardServerMap = (%d){%s}", ks.size(), StringUtils.join(ks, ", ")));
			for (String k : ks) {
				System.out.println(String.format("Trying to shutdown (%s) ... ", k));
				CardServerHandler s = mxb.get(k);
				s.shutdown();
			}
			JmxU.unregisterMBean(oname);
		} else {
			System.out.println("CardServerMap is creating.");
		}
		CardServerHandlerMap m = new CardServerHandlerMap();
		m.put("1000", new CardServer(1000));
		JmxU.registerMBean(m, oname);

		if (JmxU.isRegistered(oname)) {
			CardServerMapMBean mxb = JmxU.getMBean(oname, CardServerMapMBean.class);
			Set<String> ks = mxb.keySet();
			System.out.println(String.format("New CardServerMap = (%d){%s}", ks.size(), StringUtils.join(ks, ", ")));
		} else {
			System.err.println("CardServerMap has been unregistered.");
		}
		return m;
	}

	// @Bean
	public CardServerHandlerMap cardServerHandlerMapByMBean() throws Exception {

		String name = CardServerControllerMBean.class.getSimpleName();
		ObjectName oname = JmxU.toObjectName(name);
		if (JmxU.isRegistered(oname)) {
			System.out.println("CardServerMap already has been registered.");
			CardServerControllerMBean mxb = JmxU.getMBean(oname, CardServerControllerMBean.class);
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
		CardServerHandlerMap m = new CardServerHandlerMap();
		m.put("1000", new CardServer(1000));
		CardServerController xb = new CardServerController(m);
		JmxU.registerMBean(xb, oname);

		if (JmxU.isRegistered(oname)) {
			CardServerControllerMBean mxb = JmxU.getMBean(oname, CardServerControllerMBean.class);
			String[] sa = mxb.getKeys();
			System.out.println(String.format("New CardServerMap = (%d){%s}", sa.length, StringUtils.join(sa, ", ")));
		} else {
			System.err.println("CardServerMap has been unregistered.");
		}
		return m;
	}

	// @Bean
	public CardServerHandlerMap cardServerHandlerMapByMXBean() throws Exception {

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
		CardServerHandlerMap m = new CardServerHandlerMap();
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
