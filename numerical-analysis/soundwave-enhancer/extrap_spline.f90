module extrap_spline
    contains
        subroutine extspline(n,x,y,s2,erro)

            ! entradas e saidas
            integer, intent(in)     :: n        ! numero de pontos dados
            real*8, intent(in)      :: x(n)     ! abcissas
            real*8, intent(in)      :: y(n)     ! ordenadas

            real*8, intent(inout)   :: s2(n)    ! segundas derivadas
            integer, intent(out)    :: erro     ! condicao de erro
    
            ! variaveis internas
            real*8 Ha, Hb, Da, Db
            real*8 c(n), d(n), e(n),t

            ! condicao de existencia

            if (n<4) then
                erro = 1
                write (*,*) 'Erro: numero insuficiente de pontos'
                stop
            endif

    
            ! construcao do sistema tridiagonal nao simetrico
            m = n-2
    
            Ha = x(2)-x(1)
            Da = (y(2)-y(1))/Ha
    
            Hb = x(3)-x(2)
            Db = (y(3)-y(2))/Hb
    
            d(1) = (Ha+Hb)*(Ha+2*Hb)/Hb
            c(2) = (Hb**2-Ha**2)/Hb
            s2(2) = 6*(db-Da)
    
            do i=2,m-1
                Ha = Hb
                Da = Db
                Db = (y(i+2)-y(i+1))/Hb
                d(i) = 2*(Ha+Hb)
                e(i-1) = Ha
                c(i+1) = Hb
                s2(i+1) = 6*(Db-Da)
                Ha = Hb
                Da = Db
            enddo
    
            Ha = Hb
            Da = Db
            Hb = x(n)-x(n-1)
            Db = (y(n)-y(n-1))/Hb
            d(m) = (Ha+Hb)*(Hb+2*Ha)/Ha
            e(m-1) = (Ha**2-Hb**2)/Ha
            s2(m+1) = 6*(Db-Da)
    
            ! eliminacao de gauss
    
            do i=2,m
                t=e(i-1)/d(i-1)
                d(i) = d(i)-t*c(i)
                s2(i+1) = s2(i+1)-t*s2(i)
            enddo
    
            ! substituicoes retroativas
    
            s2(m+1) = s2(m+1)/d(m)
    
            do i=m,2,-1
                s2(i) = (s2(i)-c(i)*s2(i+1))/d(i-1)
            enddo
    
            Ha = x(2)-x(1)
            Hb = x(3) -x(2)
            s2(1) = ((Ha+Hb)*s2(2)-Ha*s2(3))/Hb
            Ha = x(n-1)-x(n-2)
            Hb = x(n)-x(n-1)
            s2(m+2) = ((Ha+Hb)*s2(m+1)-Hb*s2(m))/Ha

        end subroutine extspline
end module extrap_spline
