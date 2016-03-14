package jndi;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.InitialLdapContext;

public class JNDITest {

	public static void main(String[] args) throws Exception {

		Hashtable mp = new Properties();
		mp.put(Context.INITIAL_CONTEXT_FACTORY, "jndi.MyInitialContextFactory");

		DirContext  ctx = new InitialDirContext(mp);
		
		ctx.bind("Name1", "hello Name1");
//		new InitialLdapContext(environment, connCtls);
		
		Object res = ctx.lookup("Name1");

		System.out.println(res);
	}

}
