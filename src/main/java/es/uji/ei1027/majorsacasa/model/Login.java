package es.uji.ei1027.majorsacasa.model;

public class Login {
		private String usuario;
		private String pwd;
		private String role;
		
		public Login() {}
		
		public Login(String usuario, String pwd, String role) {
			this.usuario = usuario;
			this.pwd = pwd;
			this.role = role;
		}

		@Override
		public String toString() {
			return "Login [usuario=" + usuario + ", password=" + pwd + ", role=" + role + "]";
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getPwd() {
			return pwd;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		
}
