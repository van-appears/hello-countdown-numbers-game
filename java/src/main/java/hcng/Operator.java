package hcng;

public enum Operator {
	
	ADD("+"),
	SUBTRACT("-"),
	MULTIPLY("*"),
	DIVIDE("/");
	
	private final String symbol;
	
    private Operator(String symbol) {
        this.symbol = symbol;
    }
    
    String getSymbol() {
    	return symbol;
    }
	
}
