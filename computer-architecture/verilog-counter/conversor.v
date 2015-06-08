module conversor ( clock, A, B, C, D, s1,s2,s3,s4,s5,s6,s7);

	//Verificar q sao 4 entradas, cada uma um bit da saida do contador

	input clock;
	input A, B, C, D;
	output s1, s2, s3, s4, s5, s6, s7;

	wire A, B, C, D;
	reg s1,s2,s3,s4,s5,s6,s7;

	always @ ( posedge clock ) begin

		//Saidas mapeadas usando funcoes logicas minimizadas

		assign s1 = (A&!B&!C) | (!A&B&D) | (A&!D) | (!A&C) | (B&C); 
		assign s2 = (!B&!C&D) | (A&!B&!D) | (!B&C&!D) | (!A&B&!C&!D) | (A&!C&D) | (!A&C&D); 
		assign s3 = (!B&D) | (A&!B) | (!A&B) | (!C&D);
		assign s4 = (A&!C) | (B&!C&D) | (C&!D) | (!B&C); 
		assign s5 = (C&!D) | (A&B) | (A&!D) | (A&C);
		assign s6 = (A&C&D) | (A&!C&!D) | (B&!D) | (B&!C);
		assign s7 = (B&D) | (A&!B) | (!A&B) | (C);
	end

endmodule
