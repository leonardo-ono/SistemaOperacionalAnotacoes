	bits 16

segment code_a align=16
	valor1 times 64000 db 41h

segment code_b
	valor2 times 64000 db 42h

segment code

..start:
	mov ax, stack
	mov ss, ax
	mov sp, stacktop
	
	mov ax, code_a
	mov es, ax
	mov ah, 0eh
	mov al, [es:0]
	int 10h

	mov ax, code_b
	mov es, ax
	mov ah, 0eh
	mov al, [es:valor2]
	int 10h

	mov ax, code_c
	mov es, ax
	mov ah, 0eh
	mov al, [es:valor3]
	int 10h

	call code_d:print_d
	
	mov ah, 4ch
	int 21h
	
segment code_c
	valor3 times 64000 db 43h

segment code_d

print_d:
		mov ah, 0eh
		mov al, 44h
		int 10h
		retf
	
segment stack stack
	resb 64
	stacktop: