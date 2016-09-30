package tt.test.jmx.bean;

import java.util.ArrayList;
import java.util.List;

import tt.test.bean.CardServerHandler;
import tt.test.bean.CardServerHandlerMap;

public class CardServerController implements CardServerMapJmxMXBean {

	CardServerHandlerMap cardServerMap;

	public CardServerController(CardServerHandlerMap cardServerMap) {
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
		CardServerHandler s = this.cardServerMap.containsKey(key) ? this.cardServerMap.get(key) : null;
		if (s != null)
			s.shutdown();
	}

	@Override
	public void shutdownAll() {
		System.out.println("Trying to shutdownAll .... ");
		for (CardServerHandler s : this.cardServerMap.values())
			s.shutdown();
	}
}
