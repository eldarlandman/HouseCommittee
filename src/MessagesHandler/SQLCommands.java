package MessagesHandler;



public final class SQLCommands {

	

	private SQLCommands(){};
	
	public static String login(String userName, String password){		
		return "SELECT committees.id from committees WHERE user_name=\""+userName+ "\" AND password=\""+password+"\"";
	}
	public static String loginTenant(String userName, String password){		
		return "SELECT * from tenants WHERE user=\""+userName+ "\" AND password=\""+password+"\"";
	}

	public static String getCommitteeDetails(String userName, String password){
		return "SELECT * from committees WHERE user_name=\""+userName+ "\" AND password=\""+password+"\"";
	}
	
	public static String getTenantDetail(String userName, String password){
		return "SELECT * from tenants WHERE user=\""+userName+ "\" AND password=\""+password+"\"";
	}

	public static String getTenantsOfCommittee(int buildingID){
		return "SELECT * from tenants WHERE building_id="+buildingID;
	}
	
	public static String getTenantsPayments(int buildingID){
		return "SELECT * from tenants INNER JOIN Payments ON building_id="+buildingID+" AND Tenants.id=Payments.tenant_id";
	}
	public static String getContractor(int contractor_id,String name,int phone,String profession){
		return "SELECT * from contractors WHERE id=\""+ contractor_id +"\" AND name=\""+ name +"\" "
				+ "AND phone=\""+ phone +"\" AND profession=\""+ profession +"\"";
	}


	public static String getTenantByapartment(int apartmentNumber,int buildingNumber){
		return "SELECT tenants.id from tenants WHERE tenants.apartment_number="+apartmentNumber+" AND building_id="+buildingNumber;
	}

	public static String getTenantDetails(String userName, String password){
		return "SELECT * from Tenants WHERE user_name=\""+userName+ "\" AND password=\""+password+"\"";
	}

	public static String getTenantPayments(int tenantID){
		return "SELECT paid_month, paid_amount  from tenants_payment WHERE tenant_id="+tenantID;
	}
	

	public static String setBuildingID(int houseCommittee, int newBuildingNumber,String address, int capacity) {
		return "INSERT INTO housecommittee.buildings (`id`, `house_committee_id`, `building_address`,`building_capacity`) VALUES ("+newBuildingNumber+","+houseCommittee+",\""+address+"\","+capacity+")";
		
	}
	
	public static String updateTenantPayment(int tenant_id, int payment, int month){
		//return "INSERT INTO housecommittee.tenants_payment (`tenant_id`, `paid_month`, `paid_amount`) VALUES ("+tenant_id+","+month+","+payment+")";
		
		String sql="UPDATE housecommittee.tenants_payment SET `paid_amount`="+payment+" WHERE tenant_id="+tenant_id+" AND paid_month="+month;
		return sql;
	}

//TODO: think about update concept: full update? once per time? Maybe when server disconnects?
//	public static String updateTenantPayments(String tenantID){
//
//	}
	//	public static String getCommitteeDetails(String userName, String password){
	//		return "SELECT * from committees WHERE user_name=\""+userName+ "\" AND password=\""+password+"\"";
	//	}




}
