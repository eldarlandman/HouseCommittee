package TCPClientobject;


public class Tenant extends Person{
	public String apartament;
	public double payment;
	public double sizeApartament;
	
	public String getApartament() {
		return this.apartament;
	}
	public void setApartament(String apartament) {
		this.apartament = apartament;
	}
	public double getPayment() {
		return this.payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public double getSizeApartament() {
		return this.sizeApartament;
	}
	public void setSizeApartament(double sizeApartament) {
		this.sizeApartament = sizeApartament;
	}
	

}
