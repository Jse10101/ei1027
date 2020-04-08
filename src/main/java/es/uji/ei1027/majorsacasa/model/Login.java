package es.uji.ei1027.majorsacasa.model;

public class Login {
		private String user;
		private String password;
		private String role;
		
		public Login() {}
		
		public Login(String user, String password, String role) {
			this.user = user;
			this.password = password;
			this.role = role;
		}

		@Override
		public String toString() {
			return "Login [usuario=" + user + ", password=" + password + ", role=" + role + "]";
		}

		public String getUser() {
			return user;
		}

		public void setUuser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		
}
