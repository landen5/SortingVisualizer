
public class Step {
	//fields
	public static final int COMPARE = 0;
	public static final int SWAP = 1;
	
	public static final int TOBUFFER = 3;
	public static final int TOBARS = 4;
	
	private int index1;
	private int index2;
	private int type;
	
	//consetructor
	public Step(int m_index1, int m_index2, int m_type) {
		index1 = m_index1;
		index2 = m_index2;
		type = m_type;
	}
	
	public int getType() {
		return type;
	}
	public int getIndex1() {
		return index1;
	}
	public int getIndex2() {
		return index2;
	}
}
