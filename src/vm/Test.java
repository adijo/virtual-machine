package vm;

import static vm.Bytecode.*;
public class Test {

	static int[] hello = 
	{
		ICONST, 99, GSTORE, 0, GLOAD, 0, PRINT, HALT
	};
	
	static int[] factorial = {
		
		LOAD, -3,
		ICONST, 2,
		ILT,
		BRF, 10,
		ICONST, 1,
		RET,
		
		LOAD, -3,
		LOAD, -3,
		ICONST, 1,
		ISUB,
		CALL, 0, 1,
		IMUL, 
		RET,
		
		ICONST, 5,
		CALL, 0, 1,
		PRINT,
		HALT
		
		
	};
	
	public static void main(String[] args)
	{
		
		VM vm = new VM(factorial, 22, 0);
		vm.trace = true;
		vm.cpu();
	}

}
