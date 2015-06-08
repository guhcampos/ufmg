#include "inc.h"

extern unsigned short int regs[REGS];
extern unsigned short int mem[MEM];
extern unsigned short int PC;
extern unsigned short int IR;
extern flag	flags;
extern ctrl	controle;

void executeMemory () {

	preencheControle();
	
	 unsigned short int fmt = (IR & MSKFMT) >> 14;
	 unsigned short int R = 0;



	switch (fmt) {
		case 0:
		/* Formatos IV, V e VI */
			switch (controle.jumpr) {
				case 1:
				/* Jump False */
				if (controle.PCSrc) PC += controle.cons;
				break;
				case 2:
				/* Jump True */
				if (controle.PCSrc) PC += controle.cons;
				break;
				case 3:
				/* Jump */
				PC += controle.cons;
				break;
				case 4:
				/* Jump Register */
				PC = regs[controle.regb];
				break;
				case 5:
				/* Jump And Link */
				R7 = PC;
				PC = regs[controle.regb];
				break;
			}
		break;
		case 1:
		/* Formatos I e VII */
		if(controle.AluOP==STOR) {
			printf("\n STORE %d EM %d \n", controle.regb, controle.rega);
			mem[regs[controle.rega]]=regs[controle.regb];
		}
		else if (controle.AluOP==LOAD) {
			printf("\n LOAD %d EM %d \n", controle.rega, controle.regw);
			regs[controle.regw]=mem[regs[controle.rega]];
		}
		else {
			if (controle.AluOP == (28||29)) controle.rega = controle.regb;
			regs[controle.regw]=aluF(regs[controle.rega], regs[controle.regb]);
		}
		break;
		case 2:
		/* Formato II */
		regs[controle.regw] = controle.cons;	
		break;
		case 3:
		/* Formato III */
		R = (IR & MSKR) >> 10;
		if(R) regs[controle.regw]=(((IR&0xFF)<<8)|(regs[controle.regw]&0x00FF));
		else  regs[controle.regw]=((IR&0xFF)|(regs[controle.regw]&0xFF00));
		break;
	}
}
