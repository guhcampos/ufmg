#!/bin/bash
DATADIR='fasta_dados'
python src/guprotaligner.py -w $DATADIR/2YPIA.fasta -m $DATADIR/dTIM.fasta -f $DATADIR/family.fasta -s $DATADIR/pam250.txt