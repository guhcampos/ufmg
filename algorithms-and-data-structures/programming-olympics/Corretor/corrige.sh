

 echo ""
 echo ""
 echo "Olá $USER, bem vindo ao 'Corretor de TPS de AEDS1 do Rodolfo 2005/2 Tabajara' by gucampos"
 echo ""
 echo 'Digite o nome do .class a ser corrigido:'
 read file

i=1

teste=$file

	if [ $teste = 'vovo' ]
	then
		while [ $i -le 10 ]
		do
			echo "Testando bateria numero $i"
			java $file <./$file/cofre.i$i >./$file/out$i
			echo "" >> ./$file.results
			echo "Bateria $i" >> ./$file.results
			diff ./$file/cofre.o$i ./$file/out$i >>./$file.results
			i=$((i+1))
			echo "" >>./$file.results
 		done

	elif [ $teste = 'superm' ]
	then
		while [ $i -le 10 ]
		do
			echo "Testando Bateria numero $i"
			java $file <./$file/super.i$i >./$file/out$i
			echo "" >> ./$file.results
			echo "Bateria $i:" >> ./$file/results
		 	./corrigesuper ./$file/out$i <./$file/super.i$i >> ./$file.results
			i=$((i+1))
			echo "" >>./$file.results
		done
	
	else
		while [ $i -le 10 ]
		do
			echo "Testando bateria numero $i"
			java $file <./$file/'test'$i/in >./$file/out$i
			echo "" >> ./$file.results
			echo "Bateria $i" >> ./$file.results
			diff ./$file/'test'$i/out ./$file/out$i >>./$file.results
			i=$((i+1))
			echo "" >>./$file.results
 		done
	fi
exit

