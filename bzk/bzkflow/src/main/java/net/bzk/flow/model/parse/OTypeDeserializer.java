package net.bzk.flow.model.parse;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import net.bzk.flow.model.BzkObj;
import net.bzk.infrastructure.convert.OType;
import net.bzk.infrastructure.ex.BzkRuntimeException;

@SuppressWarnings("serial")
public class OTypeDeserializer<T extends OType> extends StdDeserializer<T> {
	private ObjectMapper mapper;

	public OTypeDeserializer(ObjectMapper m) {
		super(BzkObj.class);
		mapper = m;
	}

	@Override
	public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		try {
			TreeNode tn = p.readValueAsTree();
			System.out.println("deserialize:" + tn.toString());
			TreeNode clzn = tn.get("clazz");
			String className = clzn.toString().replace("\"", "");
			Class tc = Class.forName(className);
			return (T) mapper.readValue(tn.toString(), tc);
		} catch (ClassNotFoundException e) {
			throw new BzkRuntimeException();
		}
	}

//	public static boolean hasChild(Class<? extends BzkObj> bc, Set<Class<? extends BzkObj>> bcs) {
//		return bcs.stream().anyMatch(c ->  bc!=c &&  bc.isAssignableFrom(c));
//	}

}
