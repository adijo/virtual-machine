package vm;
import static vm.Bytecode.*;

import java.util.ArrayList;
import java.util.List;

public class VM 
{
	int[] code;
	int[] stack;
	int[] globals;
	private static final int TRUE = 1;
	private static final int FALSE = 0;
	int ip;
	int sp = -1;
	int fp;
	
	boolean trace = false;
	
	public VM(int[] code, int main, int datasize)
	{
		this.code = code;
		this.ip = main;
		globals = new int[datasize];
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
					
				case GLOAD:
					int addr = code[ip];
					ip++;
					v = globals[addr];
					sp++;
					stack[sp] = v;
					break;
				
				case GSTORE:
					v = stack[sp];
					sp--;
					addr = code[ip];
					ip++;
					globals[addr] = v;
					break;

				case BR:
					ip = code[ip++];
					break;
					
				case BRT:
					addr = code[ip++];
					if(stack[sp--] == TRUE) ip = addr;
					break;
					
				case BRF:
					addr = code[ip++];
					if(stack[sp--] == FALSE) ip = addr;
					break;
					
				case IADD:
					int b = stack[sp--];
					int a = stack[sp--];
					stack[++sp] = a + b;
					break;

				case IMUL:
					b = stack[sp--];
					a = stack[sp--];
					stack[++sp] = a * b;
					break;
				
				case CALL:
					addr = code[ip++];
					int nargs = code[ip++];
					stack[++sp] = nargs;
					stack[++sp] = fp;
					stack[++sp] = ip;
					fp = sp;
					ip = addr;
					break;
					
				case RET:
					int rvalue = stack[sp--];
					sp = fp;
					ip = stack[sp--];
					fp = stack[sp--];
					nargs = stack[sp--];
					sp -= nargs;
					stack[++sp] = rvalue;
					break;
					
				case ILT :
					b = stack[sp--];
					a = stack[sp--];
					stack[++sp] = (a < b) ? TRUE : FALSE;
					break;
					
				case ISUB:
					b = stack[sp--];
					a = stack[sp--];
					stack[++sp] = a - b;
					break;
				case LOAD:
					int offset = code[ip++];
					stack[++sp] = stack[fp + offset];
					break;
				default:
					throw new Error("invalid opcode: "+opcode+" at ip="+(ip-1));
					
			
			}
		
		}
		
	}

	private void disassemble(int opcode) 
	{
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
		
		List<Integer> stck = new ArrayList<Integer>();
		for(int i = 0; i <= sp; i++)
		{
			int v = stack[i];
			stck.add(v);
		}
		System.err.printf("\t\t" +  stck);
		System.err.println();
	}
	

}
