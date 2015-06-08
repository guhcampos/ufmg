/*
Modulo Linha Telefonica
*/


module phone_line(reset, clock, id, send, receive, status, number);

    inout[3:0] number;
    input[3:0] id;
    input clock, reset;
    input send, receive;
    output status;

    reg WAITING, DIALING, CALLING, TALKING, RINGING;
    reg status;
    reg timeout;

    initial begin
         $monitor("ID: %d\n CLOCK %b RESET %b SEND %b RECV %b\n W %b | D %b | C %b | T %b | R %b\n STATUS %b",
             id, clock, reset, send, receive, WAITING, DIALING, CALLING, TALKING, RINGING, status);
    end

    always @(posedge clock) begin

        if (reset) begin
            WAITING <= 1;
            DIALING <= 0;
            CALLING <= 0;
            TALKING <= 0;
            RINGING <= 0;
            timeout <= 0;
        end

        // ESPERANDO: se recebe ligacao toca, se enviando disca
        if (WAITING) begin
            if(send) begin
                WAITING <= 0;
                DIALING <= 1;
                CALLING <= 0;
                TALKING <= 0;
                RINGING <= 0;
            end else if (receive) begin
                WAITING <= 0;
                DIALING <= 0;
                CALLING <= 0;
                TALKING <= 0;
                RINGING <= 1;
            end
        end
        // DISCANDO: tenta completar a ligacao
        if (DIALING) begin
            WAITING <= 0;
            DIALING <= 0;
            CALLING <= 1;
            TALKING <= 0;
            RINGING <= 0;
        end
        // CHAMANDO EXTERNAMENTE
        if(CALLING) begin
            // tocou ate desligar, volta pro estado inicial
            if(timeout == 5) begin
                WAITING <= 1;
                DIALING <= 0;
                CALLING <= 0;
                TALKING <= 0;
                RINGING <= 0;
                timeout <= 0;
            end

            timeout <= timeout +1;
        end
        // CONVERSANDO pode conversar indefinidamente se tiver sinal
        if(TALKING) begin
            if(!send and !receive) begin
                WAITING <= 1;
                DIALING <= 0;
                CALLING <= 0;
                TALKING <= 0;
                RINGING <= 0;
            end
        end
        // CHAMANDO INTERNAMENTE
        if(RINGING) begin
            WAITING <= 0;

        end

        // Status eh livre se esta em espera e nao em nenhum dos demais estados
        assign status = !WAITING & DIALING & CALLING & TALKING & RINGING;

    end
endmodule