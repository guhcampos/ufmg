module contador( clock, reset, enable, saida);

input clock, reset, enable;
//Saida com 4 bits
output [3:0] saida;
reg [3:0] saida;

	initial begin
		saida = 0;
	end
	always @ (posedge clock) begin
		//Zera se DONE ou se RESET
		if (saida == 4'b1111 || reset == 1)
			saida = 0;
		else if (enable)
			//So incrementa se ENABLE
			saida = saida + 1;
	end
endmodule
