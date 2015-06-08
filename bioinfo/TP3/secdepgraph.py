#!/usr/bin/env python

import os
import sys

def main():

    with open(sys.argv[1], "rb") as grafo:
        for linha in grafo.readlines():
            print(linha)



if __name__ == "__main__":
    main()