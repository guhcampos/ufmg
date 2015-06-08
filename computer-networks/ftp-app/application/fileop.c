#include <sys/statvfs.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdio.h> 
#include <string.h>
#include "fileop.h"
#include <stdlib.h>

/*****************************************************************************/
int fileExist(char *fileName)
{
  struct stat infobuf;
  if (stat(fileName,&infobuf)) return 0;
  else return 1;
}
/*****************************************************************************/
int fileSize(char *fileName)
{
	struct stat infobuf;
	if ( stat( fileName , &infobuf) == -1 ) {
		perror("Erro ao calcular o tamanho do arquivo.\n");
    		return 0;
	}
	else return infobuf.st_size;
}
/*****************************************************************************/
int freeSpace(long long fileSize)
{
/*	struct statvfs dir;
	statvfs("/", &dir);
	printf("%d\n",dir.f_bfree);
	if ((dir.f_bfree*4096) > (fileSize)){
		return 1;
	}	
	else{
		return 0;
	}*/

	return 1;
}
