module aval_splines

    use extrap_spline

    contains
        subroutine avalspline(n,x,y,m,z,sz,erro)
            
            ! entradas e saidas
            integer, intent(in)     :: n        ! numero de pontos dados
            integer, intent(in)     :: m        ! numero de pontos a interpolar
            real*8, intent(in)      :: x(n)     ! abcissas dos pontos dados
            real*8, intent(in)      :: y(n)     ! ordenadas dos pontos dados
            real*8, intent(in)      :: z(n)     ! abcissas dos pontos a interpolar

            real*8, intent(out)     :: sz(n)    ! ordenadas dos pontos interpolados
            integer, intent(out)    :: erro     ! condicao de erro

            ! variaveis internas
            integer ind,inf,sup,j
            real*8 h,a,b,c,d
            real*8 s2(n)

            ! calculo das derivadas segundas
            call extspline(n,x,y,s2,erro)

            ! condicao de existencia

            if (erro/=0) then
                write (*,*) 'Ocorreu um erro fatal, confira as entradas'
                stop
            endif

            ! avaliacao dos splines
            do j=1,n
                if(z(j)>=x(1) .and. z(j)<=x(n)) then
                    ! pesquisa binaria no intervalo
                    inf=1
                    sup=n
                    do
                        if((sup-inf)<=1) exit
                        ind = aint((inf+sup)*0.5)
                        if (x(ind)>z(j)) then
                            sup=ind
                        else
                            inf=ind
                        endif
                    enddo
                    
                    ! processo de horner

                    h = x(sup)-x(inf)
                    a = (s2(sup)-s2(inf))/(6*h)
                    b = s2(inf)*0.5
                    c = (y(sup)-y(inf))/h - (s2(sup)+2*s2(inf))*h/6
                    d = y(inf)
                    h = z(j)-x(inf)
                    sz(j) = ((a*h+b)*h+c)*h+d
                else
                    sz(j) = 0
                endif
            enddo
        end subroutine avalspline
end module aval_splines
