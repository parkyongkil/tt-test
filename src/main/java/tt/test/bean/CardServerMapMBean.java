package tt.test.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface CardServerMapMBean extends Serializable {

	CardServerHandler get(Object key);

	CardServerHandler put(String key, CardServerHandler value);

	Set<String> keySet();

	Collection<CardServerHandler> values();

}
