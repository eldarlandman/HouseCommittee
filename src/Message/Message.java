package Message;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Message implements Serializable{ //abstract class because of toString() (not necessary...)

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//fields
	public enum Header {LOGIN,
		LOGOUT,
		GET_TENANTS_PAYMENTS,
		GET_BUILDING_PAYMENTS_BY_APARTMENT,
		GET_BUILDING_PAYMENTS_BY_MONTH,
		UPDATE_PAYMENTS,
		DELETE_PAYMENTS,
		GET_MONTHLY_REVENUE,
		GET_CONTRACTOR,
		INSERT_CONTRACTOR}
	public enum Sender {TENANT,COMMITTEE}
	private ArrayList<String> content;

	//Constructors
	public Message(ArrayList<String> args) {
		this.content=args;
	}
	
	//AbstractMethods
	public abstract void ToString();
	
	//ConcreteMtehods
	public boolean isArgsEmpty(){
		return content.size()==0;
	}

	public ArrayList<String> getArgs(){
		return content;
	}

	


}
