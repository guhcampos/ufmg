module natural_spline
    contains
        subroutine natspline (n,x,y,s2,erro)
        ! calculo das derivadas para splines naturais
            integer, intent(in) :: n
            real*8,  intent(in)  :: x(n)
            real*8,  intent(in)  :: y(n)
            real*8,  intent(out) :: s2(n)
            integer, intent(out) :: erro

            integer  :: m, i
            real*8   :: Ha, Hb, dA, dB, e(n), d(n), t

            if (n<3) then
                erro=1
                write (*,*) 'ERRO: Numero de pontos < 3'
                return
            endif 

            ! inicializacoes
            m=2; Ha=x(2); dA=(y(2)-y(1))/Ha

            ! construcao do sistema tridiagonal simetrico
            do i=1, m
                Hb=(x(i+2)-x(i+1))
                dB=(y(i+2)-y(i+1))/Hb
                e(i)=Hb
                d(i)=2*(Ha+Hb)
                s2(i+1)=6*(dB-dA)
                Ha=Hb
                dA=dB
            enddo
            ! eliminacao de gauss
            do i=2, m
                t=e(i-1)/d(i-1)
                d(i)=d(i)-t*e(i-1)
                s2(i+1)=s2(i+1)-t*s2(i)
            enddo
            ! substituicoes retroativas
            s2(m+1)=s2(m+1)/d(m)
            do i=m, 2, -1
                s2(i)=(s2(i)-e(i-1)*s2(i+1))/d(i-1)
            enddo
            s2(1)=0
            s2(m+2)=0
        end subroutine natspline
end module natural_spline
