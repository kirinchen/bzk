package net.bzk.infrastructure;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bzk.infrastructure.ex.BzkRuntimeException;

public class CommUtils {

	public static final Base64 base64 = new Base64();
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	public static <T> boolean  hasChild(Class<? extends T> bc, Set<Class<? extends T>> bcs) {
		return bcs.stream().anyMatch(c ->  bc!=c &&  bc.isAssignableFrom(c));
	}
	
	public static <T> T orElse(T t, T nt) {
		return t != null ? t : nt;
	}

	public static <T> T orOneElse(List<T> t, T nt) {
		return t != null && t.size() > 0 ? t.get(0) : nt;
	}

	public static <T> T orElse(Supplier<T> t, T nt) {
		try {
			return orElse(t.get(), nt);
		} catch (Exception e) {
			return nt;
		}
	}

	public static void pl(Object o) {
		String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

		System.out.println(String.format("%s.%s (%s) : %s", className, methodName, lineNumber, o));
	}

	public static <T> T toByJson(Object src, Class<T> clz) {
		try {
			String json = JSON_MAPPER.writeValueAsString(src);
			return JSON_MAPPER.readValue(json, clz);
		} catch (Exception e) {
			throw new BzkRuntimeException("toByJson src:" + src + " clz:" + clz, e);
		}
	}

	public static String toJson(Object o) {
		try {
			return JSON_MAPPER.writeValueAsString(o);
		} catch (Exception e) {
			throw new BzkRuntimeException("toJson O:" + o, e);
		}
	}
	
	public static <T> T loadByJson(String json,Class<T> clz) {
		try {
			return JSON_MAPPER.readValue(json, clz);
		} catch (Exception e) {
			throw new BzkRuntimeException(e);
		}
	}

	public static String serializeToString(Serializable object) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(object);
			oos.close();
			return new String(base64.encode(baos.toByteArray()));
		}
	}

	public static String sha1(Serializable o, int max) {
		try {
			String sos = serializeToString(o);
			String sha1 = sha1(sos);
			int sl = sha1.length() < max ? sha1.length() : max;
			return sha1.substring(0, sl - 1);
		} catch (IOException e) {
			throw new BzkRuntimeException(e);
		}
	}

	public static String sha1(String input) {
		return DigestUtils.sha1Hex(input);
	}
	
	public static String loadBy(Resource r) {
		try {
			try(InputStream is = r.getInputStream()){
				StringWriter writer = new StringWriter();
				IOUtils.copy(r.getInputStream(), writer,  StandardCharsets.UTF_8);
				return  writer.toString();
			}
		} catch (Exception e) {
			throw new BzkRuntimeException(e);
		}
	}
	
	public static boolean isWindows() {
	    return System.getProperty("os.name").toLowerCase().contains("win");
	}

}
