HOST=`hostname`
if [ $HOST == "thor" ]
then
	rsync -arvuz . via-lactea.drc.cecom.ufmg.br:~/graduacao/redes/straits/.
	rsync -arvuz . login.dcc.ufmg.br:~/graduacao/redes/straits/.
elif [ $HOST == "via-lactea" ]
then
	rsync -arvuz . gucampos.no-ip.org:~/ufmg/redes/straits/.
fi
