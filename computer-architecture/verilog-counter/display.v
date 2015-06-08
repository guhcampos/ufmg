`include "contador.v"
`include "conversor.v"

module display;

	reg clock, enable, reset;
	wire s1,s2,s3,s4,s5,s6,s7;
	wire [3:0] saida;

	//Instancia os modulos
	//Observar que a saida de CONT esta ligada nas entradas de CONV
	contador CONT (clock, reset, enable, saida);
	conversor CONV (
		clock,
		saida[3],
		saida[2],
		saida[1],
		saida[0],
		s1, s2, s3, s4, s5, s6, s7
	);

	initial begin
		$monitor ("Clock: %b, Time: %g, Contador: %b, Conversor: %b%b%b%b%b%b%b",
		clock, $time, saida, s1,s2,s3,s4,s5,s6,s7 );

		clock = 1;
		enable = 1;
		reset = 0;
		//Testes de simulacao
		#50 enable = 0;
		#25 enable = 1;
		#25 reset = 1;
		#1 reset = 0;
		#180 $finish;
	end

	always begin
		//Geracao de clock
		#5 clock = ~clock;
	end 
endmodule
