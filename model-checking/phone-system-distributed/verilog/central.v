`include "phone_line.v"


module central2;

    reg reset, clock;
    reg send_p0, send_p1;
    reg recv_p0, recv_p1;
    reg status_p0, status_p1;

    wire sp0, sp1;
    wire[3:0] np0, np1;

    reg[3:0] id_p0, id_p1;
    reg[3:0] number_p0, number_p1;

    phone_line p0 (reset, clock, id_p0, send_p0, recv_p0, sp0, np0);

    initial begin

        //$monitor("Clock: %b", clock);

        clock = 0;
        reset = 0;

        #4 reset = 1;
        #5 reset = 0;
        #10 $finish;

    end

    // clock generator
    always begin
        #2 clock = ~clock;
    end
endmodule