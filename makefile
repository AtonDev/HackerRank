.PHONY: default clean

SHELL = bash

CC = c99

default:
	$(CC) -o hello *.c


clean:
	rm -rf *-out