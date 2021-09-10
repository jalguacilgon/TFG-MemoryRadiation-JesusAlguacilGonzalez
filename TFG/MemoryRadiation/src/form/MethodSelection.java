package form;

public enum MethodSelection {
	MD{
		public String toString() {
			return "Manhattan Distance (MD)";
		}
	},
	IND{
		public String toString() {
			return "Infinity Norm Distance (IND)";
		}
	}
}
