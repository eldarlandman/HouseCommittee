package TCPClientobject;

import java.util.ArrayList;

public class Message implements myProtocol{

	private HeaderType header;
	private HeaderContent content;
	private ArrayList<Object> arguments;
	
	public Message(String header,String content,ArrayList<String> arguments){
		if (headerValidation(header) && contentValidation(content)){
			this.header=HeaderType.valueOf(header);
			this.content=HeaderContent.valueOf(content);
			this.arguments=arguments;
		}
		else{
			throw new IllegalArgumentException();
		}
		
		
	}

	private boolean contentValidation(String content2) {
		for (HeaderContent c : HeaderContent.values()) {
	        if (c.name().equals(content2)) {
	            return true;
	        }
	    }

	    return false;
		
	}

	private boolean headerValidation(String header2) {
		 for (HeaderType c : HeaderType.values()) {
		        if (c.name().equals(header2)) {
		            return true;
		        }
		    }

		    return false;
	}

	public ArrayList<Object> getArguments() {
		return arguments;
	}

	public void setArguments(ArrayList<Object> arguments) {
		this.arguments = arguments;
	}

//JUST FOR PRACTICE!
//	public static void main(String argv[]) throws Exception 
//	{
//		String head="GET";
//		HeaderType myheader=(HeaderType)head;
//	}

}
