package json;

import java.io.IOException;
import java.io.Writer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;		// from C:\Users\U6025719\Downloads\java-json.jar\java-json.jar
import org.json.JSONWriter;

public class JSONTest {
public static void main(String...args) throws Exception{
	
	generateJSONTest();
	//jsonWriterTest();
}

private static void jsonWriterTest() throws JSONException {
	Writer writer=new Writer() {
		
		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void flush() throws IOException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub
			
		}
	};
	JSONWriter jsonWriter = new JSONWriter(writer);
	Object object=new JSONTest();
	jsonWriter.value(object);
	
	System.out.println(jsonWriter.toString());
	
	
}

private static void generateJSONTest() throws JSONException {
JSONObject jsonObject = new JSONObject();	

jsonObject.put("key1", "value1");
jsonObject.append("hello", "ooo");
jsonObject.append("hello", "ooo");

JSONArray jSONArray = new JSONArray();
jSONArray.put("arr");

jsonObject.put("arrakey", jSONArray);

System.out.println(jsonObject);

}
}
