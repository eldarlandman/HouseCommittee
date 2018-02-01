package MessagesHandler;



public class SQLCommands {

	public static String login(String userName, String password){
		return "SELECT committees.ID from committees WHERE user_name=\""+userName+ "\" AND password=\""+password+"\"";
	}

	public static String getCommitteeDetails(String userName, String password){
		return "SELECT * from committees WHERE user_name=\""+userName+ "\" AND password=\""+password+"\"";
	}

	public static String getTenantsOfCommittee(int buildingID){
		return "SELECT * from Tenants WHERE building_id="+buildingID;
	}
	
	public static String getTenantsPayments(int buildingID){
		return "SELECT * from Tenants INNER JOIN Payments ON building_id="+buildingID+" AND Tenants.id=Payments.tenant_id";
	}


	public static String getTenantDetails(String userName, String password){
		return "SELECT * from Tenants WHERE user_name=\""+userName+ "\" AND password=\""+password+"\"";
	}

	public static String getTenantPayments(String tenantID){
		return "SELECT paid_amount, paid_month from payments WHERE tenants_id=\""+tenantID+"\"";
	}

//TODO: think about update concept: full update? once per time? when server disconnects?
//	public static String updateTenantPayments(String tenantID){
//
//	}
	//	public static String getCommitteeDetails(String userName, String password){
	//		return "SELECT * from committees WHERE user_name=\""+userName+ "\" AND password=\""+password+"\"";
	//	}




}
