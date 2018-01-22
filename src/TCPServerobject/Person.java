package TCPServerobject;

import java.io.Serializable;

public class Person implements Serializable{
	
		private String firstName;
		private String lastName;
		private String id;
		
		
		public String getName() {
			return firstName;
		}

		public void setName(String name) {
			this.firstName = name;
		}
		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	
}