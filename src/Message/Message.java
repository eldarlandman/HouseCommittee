package Message;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Message implements Serializable{ //TODO maybe abstract class is not necessary

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//fields
	public enum Header {LOGIN,
		GET_TENANTS_PAYMENTS,
		GET_BUILDING_PAYMENTS_BY_APARTMENT,
		GET_BUILDING_PAYMENTS_BY_MONTH,
		UPDATE_PAYMENTS,
		DELETE_PAYMENTS,
		GET_MONTHLY_REVENUE,
		GET_CONTRACTOR,
		INSERT_CONTRACTOR}
	public enum Sender {TENANT,COMMITTEE}
	private ArrayList<String> args;

	//Constructors
	public Message(ArrayList<String> args) {
		this.args=args;
	}
	
	//AbstractMethods
	public abstract void ToString();
	
	//ConcreteMtehods
	public boolean isArgsEmpty(){
		return args.size()==0;
	}

	public ArrayList<String> getArgs(){
		return args;
	}

	


}
