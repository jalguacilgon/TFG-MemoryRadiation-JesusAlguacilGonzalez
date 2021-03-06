package form;

public enum SizeSelection {
	BITS(1){
		public String toString() {
			return "Bits";
		}
	},
	KBITS(1024){
		public String toString() {
			return "KBits";
		}
	},
	MBITS(1048576){
		public String toString() {
			return "MBits";
		}
	};
	
	private final int multiplier;
	
	SizeSelection(int n) {
		this.multiplier = n;
	}
	
	public int getMultiplier() {
		return this.multiplier;
	}
}
