SRC_ROOT = src
OBJS:=
CFLAGS = ""

SRC = $(SRC_ROOT)
include $(SRC)/gcd.mk
include $(SRC)/pollard_roh.mk

make:
	@ g++ -o bin/pollard_roh.elf $(OBJS)

clean:
	@ rm bin/*
