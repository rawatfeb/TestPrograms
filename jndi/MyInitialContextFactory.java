package jndi;

import java.util.Hashtable;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public class MyInitialContextFactory implements InitialContextFactory {

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
		return new MyCtx(environment);
	}

	static class MyCtx implements Context{
		private Hashtable<?, ?> environment;

		MyCtx(Hashtable<?, ?> environment) {
			this.environment = environment;
		}

		@Override
		public Object addToEnvironment(String propName, Object propVal) throws NamingException {
			System.out.println("From addToEnvironment");
			return null;
		}

		@Override
		public void bind(Name name, Object obj) throws NamingException {
			System.out.println("From bind Name");
		}

		@Override
		public void bind(String name, Object obj) throws NamingException {
			System.out.println("From bind String");
//			environment.put(name, obj);			
		}

		@Override
		public void close() throws NamingException {
			System.out.println("From close");
			
		}

		@Override
		public Name composeName(Name name, Name prefix) throws NamingException {
			System.out.println("From composeName Name");
			return null;
		}

		@Override
		public String composeName(String name, String prefix) throws NamingException {
			System.out.println("From composename String");
			return null;
		}

		@Override
		public Context createSubcontext(Name name) throws NamingException {
			System.out.println("From createSubcontextName");
			return null;
		}

		@Override
		public Context createSubcontext(String name) throws NamingException {
			System.out.println("From createsubcontext String");
			return null;
		}

		@Override
		public void destroySubcontext(Name name) throws NamingException {
			System.out.println("From destroySubcontext Name");
			
		}

		@Override
		public void destroySubcontext(String name) throws NamingException {
			System.out.println("From destroySubContext String");
			
		}

		@Override
		public Hashtable<?, ?> getEnvironment() throws NamingException {
			System.out.println("From getEnvironment");
			return null;
		}

		@Override
		public String getNameInNamespace() throws NamingException {
			System.out.println("From getNameInNamespace");
			return null;
		}

		@Override
		public NameParser getNameParser(Name name) throws NamingException {
			System.out.println("From getNameParser Name");
			return null;
		}

		@Override
		public NameParser getNameParser(String name) throws NamingException {
			System.out.println("From getNameParserString");
			return null;
		}

		@Override
		public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
			System.out.println("From NamingEnumeration Name");
			return null;
		}

		@Override
		public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
			System.out.println("From NamingEnumeration String");
			return null;
		}

		@Override
		public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
			System.out.println("From NamingEnunmeration binding Name");
			return null;
		}

		@Override
		public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
			System.out.println("From NamingEnumeration<Binding> String");
			return null;
		}

		@Override
		public Object lookup(Name name) throws NamingException {
			System.out.println("From lookup Name");
			return null;
		}

		@Override
		public Object lookup(String name) throws NamingException {
			System.out.println("From lookup String");
			return null;
		}

		@Override
		public Object lookupLink(Name name) throws NamingException {
			System.out.println("From lookupLink");
			return null;
		}

		@Override
		public Object lookupLink(String name) throws NamingException {
			System.out.println("From lookupLink String");
			return null;
		}

		@Override
		public void rebind(Name name, Object obj) throws NamingException {
			System.out.println("From rebind Name");
			
		}

		@Override
		public void rebind(String name, Object obj) throws NamingException {
			System.out.println("From rebind String");
			
		}

		@Override
		public Object removeFromEnvironment(String propName) throws NamingException {
			System.out.println("From removeFromEnvironment String");
			return null;
		}

		@Override
		public void rename(Name oldName, Name newName) throws NamingException {
			System.out.println("From rename Name");
			
		}

		@Override
		public void rename(String oldName, String newName) throws NamingException {
			System.out.println("From rename String");
			
		}

		@Override
		public void unbind(Name name) throws NamingException {
			System.out.println("From unbind Name");
			
		}

		@Override
		public void unbind(String name) throws NamingException {
			System.out.println("From unbind String");
			
		}

	}

}
