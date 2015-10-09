package vm;
import static vm.Bytecode.*;

public class VM 
{
	int[] code;
	int[] stack;
	int[] data;
	
	int ip;
	int sp = -1;
	int fp;
	
	boolean trace = false;
	
	public VM(int[] code, int main, int datasize)
	{
		this.code = code;
		this.ip = main;
		data = new int[datasize];
		stack = new int[100];
	}
	
	public void cpu()
	{
		
		
		while(ip < code.length)
		{
			int opcode = code[ip]; // fetch
			if(trace)
			{
				disassemble(opcode);
			}
			
			
			
			
			ip++;
			switch (opcode)
			{
			
				case HALT:
					return;
					
				case ICONST:
					int v = code[ip];
					sp++;
					ip++;
					stack[sp] = v;
					break;
					
				case PRINT:
					v = stack[sp];
					sp--;
					System.out.println(v);
					break;
			}
		
		}
		
	}

	private void disassemble(int opcode) {
		Instruction instr = Bytecode.instructions[opcode];
		System.err.printf("%04d: %s", ip, instr.name);
		
		if(instr.nOpnds == 1)
		{
			System.err.printf(" %d", code[ip + 1]);
		}
		else if(instr.nOpnds == 2)
		{
			System.err.printf(" %d %d", code[ip + 1], code[ip + 1]);
		}
		System.err.println();
	}
	

}
