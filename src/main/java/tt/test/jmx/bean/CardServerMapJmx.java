package tt.test.jmx.bean;

import java.util.ArrayList;
import java.util.List;

import tt.test.bean.CardServer;
import tt.test.bean.CardServerMap;

public class CardServerMapJmx implements CardServerMapJmxMXBean {

	CardServerMap cardServerMap;

	public CardServerMapJmx(CardServerMap cardServerMap) {
		super();
		this.cardServerMap = cardServerMap;
	}

	@Override
	public String[] getKeys() {
		List<String> ss = new ArrayList<String>();
		for (String s : this.cardServerMap.keySet())
			ss.add(s);
		String[] sa = new String[ss.size()];
		ss.toArray(sa);
		return sa;
	}

	@Override
	public void shutdown(String key) {
		CardServer s = this.cardServerMap.containsKey(key) ? this.cardServerMap.get(key) : null;
		if (s != null)
			s.shutdown();
	}

	@Override
	public void shutdownAll() {
		System.out.println("Trying to shutdownAll .... ");
		for (CardServer s : this.cardServerMap.values())
			s.interrupt();
	}
}
