package vm;

import static vm.Bytecode.*;
public class Test {

	static int[] hello = 
	{
		ICONST, 99, PRINT, HALT
	};
	
	public static void main(String[] args)
	{
		
		VM vm = new VM(hello, 0, 0);
		vm.trace = true;
		vm.cpu();
	}

}
