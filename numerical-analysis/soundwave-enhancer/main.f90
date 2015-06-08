program cubicsplines

    use aval_splines

    ! declaracao de variaveis
    integer m,n,i,erro
    !real*8, ALLOCATABLE :: x(:)
    !real*8, ALLOCATABLE :: y(:)
    !real*8, ALLOCATABLE :: z(:)
    !real*8, ALLOCATABLE :: sz(:)
    real*8 :: x(40000)
    real*8 :: y(40000)
    real*8 :: z(40000)
    real*8 :: sz(40000)
    real*8 col1, col2

    ! lendo argumentos de linha de comando
    character (len=256) :: infile
    character (len=256) :: outfile

    n = iargc()

    if (n/=2) then
        write(*,*) 'Numero de argumentos incorretos'
        write(*,*) 'Sintaxe: cubicsplines arquivo_entrada arquivo_saida'
        stop
    endif

    ! abre entrada/saida
    call getarg(1,infile)
    call getarg(2,outfile)

    open (unit=10,file=infile,status='old',action='read')
    open (unit=11,file=outfile,status='replace',action='write')

    read(10,*) n
    !allocate (x(n))
    !allocate (y(n))

    ! le o arquivo de entrada
    do i=1,n
        read(10,100) col1,col2
        x(i) = col1
        y(i) = col2
    enddo

    ! gera os pontos a serem interpolados
    m = aint (n*0.5) +1
    !allocate (z(n))
    !allocate (sz(n))
    
    do i=1,n-1
        z(i) = (x(i+1)+x(i))*0.5
    enddo

    call avalspline(n,x,y,m,z,sz,erro)
    do i=1,n
        write(11,100) z(i),sz(i)
    enddo

    close(10)
    close(11)

    100 format (f16.13,2x,f16.13)

end program cubicsplines
